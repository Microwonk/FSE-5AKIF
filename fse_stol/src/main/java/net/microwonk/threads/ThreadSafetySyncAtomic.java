package net.microwonk.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class ThreadSafetySyncAtomic {
    public static void main(String[] args) {

        atomic();
        sync();

        System.out.println("Counting done: " + Storage.counter);
        System.out.println("Counting done: " + AtomicStorage.counter);
    }

    static void sync() {
        List<Thread> threadList = new ArrayList<>();

        // muss hier erstellt werden. wenn es in dem 'new Thread(new SyncCountingJob())' geschrieben wird, gibt es wieder race conditions
        // (synchronized gilt pro Object, nucht pro Klasse)
        // geht auch so:
        // synchronized(this) {
        //    ... implementation
        // }
        var job = new SyncCountingJob();

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

    }

    static void atomic() {
        List<Thread> threadList = new ArrayList<>();

        int nThreads = 10;
        IntStream.range(0, nThreads).forEach((i) -> {
            Thread t = new Thread(new AtomicCountingJob());
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
    }
}

class AtomicStorage {
    public static AtomicLong counter = new AtomicLong(0);
}

class Storage {
    public static long counter = 0;
}

class AtomicCountingJob implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("Counting job starting");

        int n = 1000;
        IntStream.range(0, n).forEach((i) -> AtomicStorage.counter.incrementAndGet());

        System.out.println("Counting job stopping");
    }
}

class SyncCountingJob implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("Counting job starting");

        int n = 1000;
        IntStream.range(0, n).forEach((i) -> s());
        System.out.println("Counting job stopping");
    }

    private synchronized void s() {
        Storage.counter++;
    }
}
