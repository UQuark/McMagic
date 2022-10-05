package me.uquark.mcmagic.voice.record;

import javax.sound.sampled.*;

public class Recorder extends Thread {
    public final AudioFormat format;
    private TargetDataLine line;

    public Recorder() {
        format = new AudioFormat(16000, 16, 1, true, false);
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

    public void end() {
        if (line != null) {
            line.close();
            line = null;
        }
    }
}
