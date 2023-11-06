package net.microwonk.hashfinder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Hashfinder {
    private static final int NUM_THREADS = 4; // Number of threads for mining
    private static final String PREFIX = "000000"; // The desired difficulty prefix
    private static final String MESSAGE = "Hello, World!"; // Replace with your data

    public static void main(String[] args) {
        AtomicBoolean found = new AtomicBoolean(false);

        for (int i = 0; i < NUM_THREADS; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> mineNonce(finalI, found));
            thread.start();
        }
    }

    private static void mineNonce(int threadId, AtomicBoolean found) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageBytes = MESSAGE.getBytes();
            int nonce = 0;

            while (!found.get()) {
                byte[] nonceBytes = intToBytes(nonce);
                byte[] dataToHash = concatByteArrays(nonceBytes, messageBytes);

                byte[] hash = md.digest(dataToHash);

                // Convert the hash to a hexadecimal string
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    String hex = String.format("%02x", b);
                    hexString.append(hex);
                }

                if (hexString.toString().startsWith(PREFIX)) {
                    System.out.println("Thread " + threadId + " found a valid nonce: " + nonce);
                    found.set(true);
                    break;
                }

                nonce++;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static byte[] intToBytes(int value) {
        return new byte[] {
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }

    private static byte[] concatByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
