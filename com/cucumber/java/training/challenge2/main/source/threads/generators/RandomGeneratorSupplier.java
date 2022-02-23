package source.threads.generators;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class RandomGeneratorSupplier implements Runnable{

    private boolean hasFinishedGeneratingData;
    private final int initialSeed;
    private boolean work;
    private final BlockingDeque<Integer> generatedNumbers;

    public RandomGeneratorSupplier(int initialSeed) {
        this.work = true;
        this.initialSeed = initialSeed;
        this.generatedNumbers = new LinkedBlockingDeque<>();
    }

    @Override
    public void run() {
        Random random = new Random(initialSeed);

        while (work) {
            if (generatedNumbers.size() < 95) {
                generatedNumbers.add(random.nextInt(49) + 1);
            }
        }

        this.hasFinishedGeneratingData = true;
    }

    public boolean hasFinished() {
        return this.hasFinishedGeneratingData;
    }

    public void stop() {
        work = false;
    }

    public int[] getLuckyNumbers(Integer length) {
        ArrayList<Integer> result = new ArrayList<>();
        while (result.size() < length) {
            try {
                result.add(generatedNumbers.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int[] copy = new int[result.size()];
        for(int index = 0; index < result.size(); ++index) {
            copy[index] = result.get(index);
        }
        return copy;
    }

    @Override
    public String toString() {
        return "RandomGeneratorSupplier{" +
                "hasFinishedGeneratingData=" + hasFinishedGeneratingData +
                ", initialSeed=" + initialSeed +
                ", work=" + work +
                ", generatedArrays=" + generatedNumbers +
                '}';
    }
}
