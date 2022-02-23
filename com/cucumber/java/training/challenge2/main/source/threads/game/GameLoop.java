package source.threads.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class GameLoop implements Runnable{

    private final int initialSeed;
    BlockingQueue<Integer> winnerNumbers;

    public GameLoop(int initialSeed) {
        this.winnerNumbers = new LinkedBlockingDeque<>();
        this.initialSeed = initialSeed;
    }

    @Override
    public void run() {
        Random random = new Random(initialSeed);
        int generatedNumbers = 0;
        while(generatedNumbers < 6) {
            int extractedNumber = random.nextInt(49) + 1;
            if (!winnerNumbers.contains(extractedNumber)) {
                winnerNumbers.add(extractedNumber);
                ++generatedNumbers;
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getWinnerNumber() throws InterruptedException {
        return this.winnerNumbers.take();
    }
}
