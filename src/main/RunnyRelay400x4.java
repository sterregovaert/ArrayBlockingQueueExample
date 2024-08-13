package main;

import java.util.concurrent.*;

import domain.Athlete;

public class RunnyRelay400x4 {

    public static final int DISTANCE = 1600;
    public static final int ATHLETES = 4;

    public static void main(String[] args) {

        ArrayBlockingQueue<Integer> batonQueue = new ArrayBlockingQueue<>(1);

        // ThreadPool
        ExecutorService executorService = Executors.newFixedThreadPool(ATHLETES);

        for (int i = 0; i < ATHLETES; i++) {
            executorService.execute(new Athlete(batonQueue, i + 1));
        }

        // Start the race by giving the baton to the first athlete
        try {
            System.out.println("---- ---- Starting the race by giving the baton to Athlete 1. ---- ----");
            batonQueue.put(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread was interrupted.");
        }

        // Shutdown the executor service
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Executor service was interrupted.");
        }
    }
}