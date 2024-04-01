package ru.nsu.romanov.pizzeria.state;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Response for reading and writing state.
 */
public interface StateManger {
    /**
     * Read state from stream.
     *
     * @param inputStreamReader stream from which read.
     */
    void readState(InputStreamReader inputStreamReader) throws IOException;

    /**
     * Write state into stream.
     *
     * @param outputStreamWriter stream in which write.
     */
    void writeState(OutputStreamWriter outputStreamWriter);
}
