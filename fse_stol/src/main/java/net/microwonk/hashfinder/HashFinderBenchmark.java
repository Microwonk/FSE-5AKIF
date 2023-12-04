package net.microwonk.hashfinder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class HashFinderBenchmark {

    public static void main(String[] args) {
        int numIterations = 100;
        int stringSize = 20;
        int difficulty = 6;

        try (FileWriter writer = new FileWriter("benchmark_results.txt")) {
            long cumulative = 0;
            for (int i = 0; i < numIterations; i++) {
                String randomString = generateRandomString(stringSize);
                long timeTaken = runHashFinder(randomString, difficulty, writer);
                cumulative += timeTaken;
                writer.write("Iteration " + (i + 1) + ": " + timeTaken + " ms\n");
            }
            writer.write("All measured " + (cumulative / numIterations) + " ms on average\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomString(int size) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder(size);
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }

        return randomString.toString();
    }

    private static long runHashFinder(String message, int difficulty, FileWriter writer) {
        long startTime = System.currentTimeMillis();
        new HashFinderExtreme().run(message, difficulty, writer);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
