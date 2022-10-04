package me.uquark.mcmagic.voice.recognize.ps4j;

public class PS4J {
    static {
        System.loadLibrary("ps4j");
    }

    public static native void init(String model, String dict, String kws);
    public static native int startUtterance();
    public static native int endUtterance();
    public static native int addWord(String word, String phones, boolean update);
    public static native int process(byte[] data, long samples, boolean fullUtterance);
    public static native boolean getInSpeech();
    public static native String getHypothesis();
    public static native int getScore();
    public static native void destroy();
}
