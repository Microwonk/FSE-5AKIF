package net.microwonk.hashfinder;

import lombok.Cleanup;
import lombok.extern.java.Log;
import lombok.val;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Log
public class HashFinderExtreme {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int NUM_THREADS_SUBTRACTION = 2;
    private static final long WORKLOAD_PER_THREAD = Long.MAX_VALUE / (NUM_THREADS - NUM_THREADS_SUBTRACTION);
    private int DIFFICULTY = 6;
    private String MESSAGE = "Hello World!";
    private String HASHED_NONCE_MESSAGE;
    private String NONCE_MESSAGE;
    private long FINAL_NONCE;

    private final AtomicLong validNonce = new AtomicLong(-1);

    public void run(String message, int difficulty, FileWriter writer) {
        try {
            String hashedNonceMessage;
            String nonceMessage;
            long finalNonce;

            Thread[] threads = new Thread[NUM_THREADS - NUM_THREADS_SUBTRACTION];

            DIFFICULTY = difficulty;
            MESSAGE = message;

            long startTime = System.currentTimeMillis();

            IntStream.range(0, NUM_THREADS - NUM_THREADS_SUBTRACTION).forEach(i -> {
                threads[i] = new Thread(() -> calculateNonce(i, WORKLOAD_PER_THREAD * i));
                threads[i].start();
            });

            Arrays.stream(threads).forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    log.severe("Failed to join working Threads: " + e);
                }
            });

            long endTime = System.currentTimeMillis();

            // metrics
            writer.write("Result Found in: " + (endTime - startTime) + " ms, or " + (double) (endTime - startTime) / 1000.0 + " s.\n");
            writer.write("-".repeat(40) + "\n");
            writer.write("The Input: \"" + message + "\"\n");
            writer.write("Valid nonce found: " + FINAL_NONCE + "\n");
            writer.write("Un-hashed concatenation: " + NONCE_MESSAGE + "\n");
            writer.write("The resulting Hash: " + HASHED_NONCE_MESSAGE + "\n");

        } catch (IOException e) {
            log.severe("Failed to write benchmark results: " + e);
        }
    }

    public static void main(String... args) throws IOException {

        String message = "";
        int diff = 0;

        if (args.length > 0) {
            message = args[0];
        }

        if (args.length > 1) {
            try {
                diff = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid difficulty input. Using the default value.");
            }
        }

        if (args.length == 0) {
            @Cleanup
            val scanner = new Scanner(System.in);

            System.out.print("Message that may be found a nonce for: ");
            val msg = scanner.nextLine();
            message = msg.isEmpty() ? message : msg;

            System.out.print("Difficulty: ");
            try {
                val difficulty = scanner.nextLine();
                diff = Integer.parseInt(difficulty);
            } catch (Exception ignored) {}
        }

        @Cleanup
        FileWriter fw = new FileWriter("benchmark_results.txt");
        new HashFinderExtreme().run(message, diff, fw);
    }

    private void calculateNonce(final int threadId, final long startRange) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageBytes = MESSAGE.getBytes();
            long nonce = startRange;

            while (validNonce.get() == -1) {
                System.out.println("[Thread " + threadId + "] started searching from " + startRange + " to " + startRange + WORKLOAD_PER_THREAD);

                for (; nonce < startRange + WORKLOAD_PER_THREAD; nonce++) {
                    byte[] nonceBytes = Long.toString(nonce).getBytes();
                    byte[] dataToHash = concatByteArrays(messageBytes, nonceBytes);
                    byte[] hash = md.digest(dataToHash);

                    if (startsWithZeroBytes(hash, DIFFICULTY)) {
                        handleValidNonce(threadId, nonce, dataToHash, hash);
                    }
                    if (validNonce.get() != -1) {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            log.severe("[Thread " + threadId + "] failed: " + e);
        }
    }

    private void handleValidNonce(int threadId, long nonce, byte[] dataToHash, byte[] hash) {
        synchronized (HashFinderExtreme.class) {
            if (validNonce.get() == -1) {
                validNonce.set(nonce);
                FINAL_NONCE = validNonce.get();
            }
        }

        log.info("[Thread " + threadId + "] found a valid nonce: " + FINAL_NONCE);

        NONCE_MESSAGE = byteArrayToString(dataToHash, false);
        HASHED_NONCE_MESSAGE = byteArrayToString(hash, true);
    }

    private static String byteArrayToString(byte[] bytes, boolean format) {
        StringBuilder hexString = new StringBuilder();
        for (val b : bytes) {
            val s = format ? String.format("%02X", b) : String.valueOf((char)b);
            hexString.append(s);
        }
        return hexString.toString();
    }

    private static byte[] concatByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static boolean startsWithZeroBytes(byte[] data, int numZeros) {
        for (int i = 0; i < numZeros / 2; i++) {
            if (data[i] != 0) {
                return false;
            }
        }
        return numZeros % 2 == 0 || (data[numZeros / 2] & 0xF0) == 0;
    }
}
