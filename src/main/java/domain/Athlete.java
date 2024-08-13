package domain;

import java.util.concurrent.ArrayBlockingQueue;

import main.RunnyRelay400x4;

import static main.RunnyRelay400x4.ATHLETES;
import static main.RunnyRelay400x4.DISTANCE;

public class Athlete implements Runnable {

    private final ArrayBlockingQueue<Integer> batonQueue;
    private final int athleteNumber;

    public Athlete(ArrayBlockingQueue<Integer> batonQueue, int athleteNumber) {
        this.batonQueue = batonQueue;
        this.athleteNumber = athleteNumber;
    }

    @Override
    public void run() {
        try {
            // Wait to receive the baton
            int baton = batonQueue.take();
            if (baton == athleteNumber) {
                System.out.println("Athlete " + athleteNumber + " has the baton.");

                // Simulate running
                int distanceCovered = 0;
                while (distanceCovered < DISTANCE / ATHLETES) {
                    Thread.sleep(1000);
                    distanceCovered += 100; // Increment distance covered
                    System.out.println("Athlete " + athleteNumber + " covered " + distanceCovered + " meters.");
                }

                // Pass the baton to the next athlete
                if (athleteNumber < ATHLETES) {
                    batonQueue.put(athleteNumber + 1);
                    System.out.println("Athlete " + athleteNumber + " passed the baton.");
                } else {
                    System.out.println("---- ---- Athlete " + athleteNumber + " finished the race. ---- ----");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Athlete " + athleteNumber + " was interrupted.");
        }
    }
}