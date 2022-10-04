package me.uquark.mcmagic.voice.recognize;

import me.uquark.mcmagic.Util;
import me.uquark.mcmagic.voice.recognize.ps4j.PS4J;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import javax.sound.sampled.TargetDataLine;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Timer;
import java.util.TimerTask;

public class Recognizer {
    private static final int BUFFER_SIZE = 4096;
    private static final long HYPOTHESIS_INTERVAL = 200;
    private static final long RECOGNITION_INTERVAL = 50;
    private static final long END_UTTERANCE_DELAY = 400;
    private static Recognizer INSTANCE;
    public static Recognizer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Recognizer();
        return INSTANCE;
    }

    private static void unpackDist(Path dist) throws IOException {
        InputStream distStream = Recognizer.class.getResourceAsStream("/assets/mcmagic/ps4j.tar");
        if (distStream == null) {
            Util.LOGGER.severe("PS4J dist not found");
            return;
        }
        TarArchiveInputStream distTar = new TarArchiveInputStream(distStream);
        ArchiveEntry entry = distTar.getNextEntry();
        while (entry != null) {
            Path newFile = Path.of(dist.toString(), entry.getName());
            if (entry.isDirectory()) {
                if (!Files.exists(newFile))
                    Files.createDirectories(newFile);
            } else {
                Files.copy(distTar, newFile, StandardCopyOption.REPLACE_EXISTING);
            }
            entry = distTar.getNextEntry();
        }
        distTar.close();
    }

    public static void init() throws IOException {
        Path dist = Path.of(FabricLoader.getInstance().getGameDir().toAbsolutePath().toString(), ".ps4j");
        unpackDist(dist);
        Path model = Path.of(dist.toString(), "model");
        Path dict = Path.of(dist.toString(), "model", "spells.dict");
        Path kws = Path.of(dist.toString(), "model", "spells.kws");
        PS4J.init(model.toString(), dict.toString(), kws.toString());
    }

    private String lastHypothesis = "";
    private UtteranceThread utteranceThread;

    private static class UtteranceThread extends Thread {
        private TargetDataLine line;
        private volatile boolean running;

        public synchronized void start(TargetDataLine line) {
            this.line = line;
            running = true;
            super.start();
        }

        @Override
        public void run() {
            int sampleSize = line.getFormat().getSampleSizeInBits() / 8;
            byte[] buffer = new byte[BUFFER_SIZE];

            line.start();
            int res = PS4J.startUtterance();
            assert res == 0;

            while (running) {
                int read = line.read(buffer, 0, BUFFER_SIZE);
                if (read > 0) {
                    res = PS4J.process(buffer, read / sampleSize, false);
                    assert res == 0;
                }
            }

            line.stop();
            res = PS4J.endUtterance();
            assert res == 0;
        }

        @Override
        public void interrupt() {
            running = false;
            super.interrupt();
        }
    }

    private Recognizer() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String hypothesis = PS4J.getHypothesis();
                if (hypothesis != null && !hypothesis.equals(lastHypothesis)) {
                    lastHypothesis = hypothesis;
                    System.out.println(hypothesis);
                    System.out.flush();
                }
            }
        }, HYPOTHESIS_INTERVAL / 2, HYPOTHESIS_INTERVAL);
    }

    public void start(TargetDataLine line) {
        if (utteranceThread != null)
            return;
        lastHypothesis = "";
        utteranceThread = new UtteranceThread();
        utteranceThread.start(line);
    }

    public void end() {
        utteranceThread.interrupt();
        utteranceThread = null;
    }
}