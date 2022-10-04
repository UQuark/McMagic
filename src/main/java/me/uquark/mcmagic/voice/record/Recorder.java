package me.uquark.mcmagic.voice.record;

import me.uquark.mcmagic.voice.recognize.PipeStream;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class Recorder extends Thread {
    private static Recorder INSTANCE;
    public static Recorder getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Recorder();
        return INSTANCE;
    }

    public final AudioFormat format;
    public final PipeStream out;
    private TargetDataLine line;

    public Recorder() {
        format = new AudioFormat(16000, 16, 1, true, false);
        out = new PipeStream();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            return;
        }
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public TargetDataLine getLine() {
        return line;
    }
}
