package me.uquark.mcmagic.voice.recognize;

import java.io.ByteArrayOutputStream;

public class PipeStream extends ByteArrayOutputStream {
    private volatile int offset;

    public synchronized int read(byte[] buffer) {
        int available = buf.length-offset;
        if (available <= 0)
            return 0;
        int len = Math.min(available, buffer.length);
        offset += len;
        System.arraycopy(buf, offset-len, buffer, 0, len);
        return len;
    }
}
