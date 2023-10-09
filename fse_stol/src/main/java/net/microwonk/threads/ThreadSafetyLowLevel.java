package net.microwonk.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ThreadSafetyLowLevel {

    public static void main(String[] args) {
        List<Thread> threadList = new ArrayList<>();

        ReentrantLock lock = new ReentrantLock();

        var job = new LowLevelManagedCountingJob(lock);

        int nThreads = 10;
        IntStream.range(0, nThreads).forEach((i) -> {
            Thread t = new Thread(job);
            threadList.add(t);
            t.start();
        });

        try {
            for (Thread thread: threadList) {
                thread.join();
            }
        } catch (InterruptedException e ) {
            System.out.println("Oh nein");
        }
        System.out.println("Counting done: " + LockStorage.counter);
    }
}

class LowLevelManagedCountingJob implements Runnable {

    private final ReentrantLock lock;

    public LowLevelManagedCountingJob(ReentrantLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("Counting job starting");

        int n = 1000;
        IntStream.range(0, n).forEach((i) -> {
            lock.lock();
            LockStorage.counter++;
            lock.unlock();
        });
        System.out.println("Counting job stopping");
    }
}

class LockStorage {
    public static long counter = 0;
}