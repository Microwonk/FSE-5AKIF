package net.microwonk.hashfinder;

import lombok.Cleanup;
import lombok.extern.java.Log;
import lombok.val;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Log
public class HashFinderExtreme {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int NUM_THREADS_SUBTRACTION = 2;
    private static int DIFFICULTY = 6;
    private static String MESSAGE = "Hello World!";
    private static String HASHED_NONCE_MESSAGE;
    private static String NONCE_MESSAGE;
    private static long RANGE_SIZE;
    private static long FINAL_NONCE;

    private static void calculateOptimalRange() {
        long baseRangeSize = 1_000_000;
        int expectedTimeSeconds = 10;
        val adjustedRangeSize  = baseRangeSize * (NUM_THREADS * 4L) / DIFFICULTY;
        val maxExpectedTime = expectedTimeSeconds * 1_000_000; // Convert seconds to microseconds
        RANGE_SIZE = Math.min(adjustedRangeSize, maxExpectedTime);
        System.out.println("Using a RANGE_SIZE of " + RANGE_SIZE);
    }

    private static final AtomicLong validNonce = new AtomicLong(-1);
    private static final AtomicLong nonceRangeStart = new AtomicLong(0);

    public static void main(String... args) {

        if (args.length > 0) {
            MESSAGE = args[0];
        }

        if (args.length > 1) {
            try {
                DIFFICULTY = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid difficulty input. Using the default value.");
            }
        }

        if (args.length == 0) {
            @Cleanup
            val scanner = new Scanner(System.in);

            System.out.print("Message that may be found a nonce for: ");
            val msg = scanner.nextLine();
            MESSAGE = msg.isEmpty() ? MESSAGE : msg;

            System.out.print("Difficulty: ");
            try {
                val diff = scanner.nextLine();
                DIFFICULTY = Integer.parseInt(diff);
            } catch (Exception ignored) {}
        }

        calculateOptimalRange();

        Thread[] threads = new Thread[NUM_THREADS - NUM_THREADS_SUBTRACTION]; // nicht alle erhaeltlichen Threads benutzen

        // metrics
        long startTime = System.currentTimeMillis();

        IntStream.range(0, NUM_THREADS - NUM_THREADS_SUBTRACTION).forEach(i -> {
            threads[i] = new Thread(() -> calculateNonce(i));
            threads[i].start();
        });

        Arrays.stream(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                log.severe("Failed to join working Threads: " + e);
            }
        });

        // metrics
        long endTime = System.currentTimeMillis();

        System.out.println("\nResult Found in: " + (endTime - startTime) + " ms, or " + (double)(endTime - startTime) / 1000.0 + " s.");
        System.out.println("-".repeat(40));
        System.out.println("The Input: \"" + MESSAGE + '"');
        System.out.println("Valid nonce found: " + FINAL_NONCE);
        System.out.println("Un-hashed concatenation: " + NONCE_MESSAGE);
        System.out.println("The resulting Hash: " + HASHED_NONCE_MESSAGE);
    }

    private static void calculateNonce(int threadId) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageBytes = MESSAGE.getBytes();

            while (validNonce.get() == -1) {
                long nonce = nonceRangeStart.get();
                nonceRangeStart.getAndAdd(RANGE_SIZE);
                long end = nonce + RANGE_SIZE;

                long steps = 0;

                for (; nonce < end; nonce++) {
                    steps++;
                    byte[] nonceBytes = Long.toString(nonce).getBytes();
                    byte[] dataToHash = concatByteArrays(messageBytes, nonceBytes); // Concatenate nonce and message
                    byte[] hash = md.digest(dataToHash);

                    if (startsWithZeroBytes(hash, DIFFICULTY)) {
                        synchronized (HashFinderExtreme.class) {
                            if (validNonce.get() == -1) {
                                validNonce.set(nonce);
                                FINAL_NONCE = validNonce.get();
                            }
                        }
                        log.info("[Thread " + threadId + "] found a valid nonce: " + FINAL_NONCE + " in " + steps + " steps.");

                        NONCE_MESSAGE = byteArrayToString(dataToHash, false); // Create un-hashed concatenation
                        HASHED_NONCE_MESSAGE = byteArrayToString(hash, true);
                        return;
                    }

                    if (validNonce.get() != -1) {
                        break;
                    }
                }

                if (validNonce.get() == -1) {
                    System.out.println("[Thread " + threadId + "] finished searching from " + nonce + " to " + (nonce + RANGE_SIZE));
                }
            }
        } catch (Exception e) {
            log.severe("[Thread " + threadId + "] failed: " + e);
        }
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
