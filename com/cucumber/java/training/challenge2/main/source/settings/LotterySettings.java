package source.settings;

import source.models.LotteryTicket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class LotterySettings implements Cloneable{
    private static int MAX_NO_PLAYERS;
    private AtomicInteger noCurrentPlayers;
    ArrayList<String> previousWinners;
    ArrayList<LotteryTicket> previousLuckyNumbers;

    public LotterySettings(String fileName) {

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            MAX_NO_PLAYERS = Integer.parseInt(bufferedReader.readLine().trim());

            String[] previousWinners = bufferedReader.readLine().trim().split(",");
            this.previousWinners = new ArrayList<>(previousWinners.length);
            this.previousWinners.addAll(Arrays.asList(previousWinners));

            String line;
            int count = 0;
            this.previousLuckyNumbers = new ArrayList<>(20);
            while(null != (line = bufferedReader.readLine())) {
                line = line.trim();
                String[] winnerNumbers = line.split(",");
                int[] luckyNumbers = new int[winnerNumbers.length];
                for (int index = 0; index < winnerNumbers.length; ++index) {
                    luckyNumbers[index] = Integer.parseInt(winnerNumbers[index]);
                }

                LotteryTicket lotteryTicket = new LotteryTicket("Winner" + (count++), winnerNumbers.length);
                lotteryTicket.setLuckyNumbers(luckyNumbers);
                if (lotteryTicket.isLuckyNumbersValid()) {
                    this.previousLuckyNumbers.add(lotteryTicket);
                }
            }

            this.noCurrentPlayers = new AtomicInteger();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LotterySettings(LotterySettings lotterySettings) {
        this.noCurrentPlayers = lotterySettings.noCurrentPlayers;
        this.previousWinners = new ArrayList<>(lotterySettings.previousWinners);
        this.previousLuckyNumbers = new ArrayList<>(lotterySettings.previousLuckyNumbers.size());

        for(LotteryTicket ticket : lotterySettings.previousLuckyNumbers) {
            this.previousLuckyNumbers.add(ticket.clone());
        }
    }

    public int getNoCurrentPlayers() {
        return this.noCurrentPlayers.get();
    }

    public int getNoPreviousWinners() {
        return this.previousWinners.size();
    }

    public String getWinner(int index) {
        if (index > this.previousWinners.size() || index < 0) {
            throw new IllegalArgumentException();
        }

        return this.previousWinners.get(index);
    }

    public int getNoLuckyCombinations() {
        return this.previousLuckyNumbers.size();
    }

    public LotteryTicket getLuckyCombinations(int index) {
        if (index > this.previousLuckyNumbers.size() || index < 0) {
            throw new IllegalArgumentException();
        }

        return this.previousLuckyNumbers.get(index);
    }

    public void incrementNoCurrentPlayers() {
        noCurrentPlayers.incrementAndGet();
    }

    public int getMaxNoPlayers() {
        return MAX_NO_PLAYERS;
    }

    @Override
    public String toString() {
        return "LotterySettings{" +
                "noCurrentPlayers=" + noCurrentPlayers +
                ", previousWinners=" + previousWinners +
                ", previousLuckyNumbers=" + previousLuckyNumbers +
                '}';
    }

    @Override
    public LotterySettings clone() {
        return new LotterySettings(this);
    }
}
