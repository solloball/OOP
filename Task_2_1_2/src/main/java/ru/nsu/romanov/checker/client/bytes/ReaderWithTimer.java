package ru.nsu.romanov.checker.client.bytes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ReaderWithTimer {

    public int readInt(InputStream reader, long delay) throws IOException {
        long startTime = System.currentTimeMillis();
        final int sizeInt = 4;
        byte[] bytes = new byte[sizeInt];
        while (reader.available() < sizeInt) {
            if (System.currentTimeMillis() - startTime > delay) {
                throw new RuntimeException("Failed to wait data");
            }
        }
        if (reader.read(bytes, 0, sizeInt) != sizeInt) {
            throw new RuntimeException("Failed to read data");
        }
        return ByteBuffer.wrap(bytes).getInt();
    }
}
