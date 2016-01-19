package com.android.media.service.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class CopyingInputStream extends BufferedInputStream {

    private ByteArrayOutputStream mOutputStream;

    public CopyingInputStream(InputStream inputStream) {
        super(inputStream);
        mOutputStream = new ByteArrayOutputStream();
    }

    public int read(byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        int read = super.read(buffer, byteOffset, byteCount);

        for (int i = 0; i < read; i++) {
            mOutputStream.write(buffer[byteOffset + i]);
        }
        return read;
    }

    public byte[] getCopiedBytes() {
        return mOutputStream.toByteArray();
    }
}
