package gh2;

import deque.ArrayDeque;
import deque.Deque;

public class GuitarString {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
     private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // Create a buffer with capacity = SR / frequency.
        // need to cast the result of this division operation into an int.
        buffer = new ArrayDeque<>();
        int capacity = (int)Math.round(SR / frequency);
        for(int i = 0; i< capacity; i++) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for(int i = 0; i < buffer.size(); i++) {
            double r = Math.random() - 0.5;
            buffer.removeFirst();
            buffer.addLast(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // Dequeue the front sample and enqueue a new sample that is
        // the average of the two multiplied by the DECAY factor.
        double front = buffer.removeFirst();
        double average = (front + buffer.get(0)) / 2 * DECAY;
        buffer.addLast(average);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
