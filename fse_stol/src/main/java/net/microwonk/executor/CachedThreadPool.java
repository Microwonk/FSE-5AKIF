package net.microwonk.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        
        int counter = 0;

        while (true) {
            System.out.printf("%d Threads laufen gerade %n", Thread.activeCount());
            if (counter < 10) {
                exec.execute(new Job(5, "Job-" + (counter + 1)));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Job implements Runnable {
    int runs;
    String name;

    public Job(int runs, String name) {
        this.runs = runs;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.printf("* Starte thread %s %n", name);

            IntStream.range(0, runs).forEach((i) -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        System.out.printf("* Endet thread %s %n", name);
    }
}
