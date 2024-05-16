package ru.nsu.romanov.checker.client.bytes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Read from bytes with fixed delay.
 */
public class ReaderWithTimer {

    /**
     * Read int with delay.
     *
     * @param reader stream.
     * @param delay delay.
     * @return int.
     * @throws IOException can throw if delay is timeout.
     */
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
