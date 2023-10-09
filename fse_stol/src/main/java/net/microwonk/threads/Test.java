package net.microwonk.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<5;i++){
            es.execute(() -> System.out.println("Hello World!"));
        }
        es.shutdown();
        boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
    }
}