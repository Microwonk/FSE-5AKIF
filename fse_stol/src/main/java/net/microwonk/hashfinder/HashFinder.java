package net.microwonk.hashfinder;


import java.security.MessageDigest;
import java.util.Arrays;
import java.util.stream.IntStream;

public class HashFinder {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors() - 2;
    private static final int DIFFICULTY = 6;
    private static final String MESSAGE = "Hallo";

    // termination condition
    private static volatile boolean found = false;

    public static void main(String[] args) {
        IntStream.range(0, NUM_THREADS).forEach(i -> new Thread(() -> calculateNonce(i)).start());
    }

    private static void calculateNonce(int threadId) {

        long startTime = System.currentTimeMillis();
        int steps = 0;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageBytes = MESSAGE.getBytes();
            int nonce = 0;

            byte[] leadingZeros = new byte[DIFFICULTY / 2]; // Two hexadecimal characters per byte
            Arrays.fill(leadingZeros, (byte) 0);

            while (!found) {
                byte[] nonceBytes = Long.toString(nonce).getBytes();
                byte[] dataToHash = concatByteArrays(messageBytes, nonceBytes);
                byte[] hash = md.digest(dataToHash);

                if (startsWithZeroBytes(hash, leadingZeros)) {
                    // synchronized
                    synchronized (HashFinder.class) {
                        found = true;
                    }
                    System.out.println("Thread " + threadId + " found a valid nonce: " + nonce);
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;
                    System.out.println("It Took " + elapsedTime + "ms, with " + steps + " steps");

                    break;
                }

                nonce++;
                steps++;
            }
        } catch (Exception e) {
            System.err.println("Calculating the nonce has failed: " + e);
        }
    }

    private static byte[] concatByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static boolean startsWithZeroBytes(byte[] data, byte[] zeros) {
        int length = zeros.length;
        for (int i = 0; i < length; i++) {
            if (data[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
