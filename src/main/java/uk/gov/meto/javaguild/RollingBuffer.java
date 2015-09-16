package uk.gov.meto.javaguild;

import java.util.ArrayDeque;

public class RollingBuffer {

    private final ArrayDeque<Integer> buffer = new ArrayDeque<>(5);
    private final int bufferSize = 5;

    public synchronized void add(int value) {
        if (buffer.size() == bufferSize) {
            buffer.removeFirst();
        }
        buffer.addLast(value);
    }

    public synchronized double getAverage() {
        if (buffer.size() < bufferSize) {
            return 0;
        } else {
            return buffer.stream().mapToInt(Integer::valueOf).average().orElseGet(() -> 0);
        }
    }
}
