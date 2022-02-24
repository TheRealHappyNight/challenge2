package source.threads.game;

import source.models.LotteryTicket;
import source.models.Player;
import source.models.PlayerCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class WinnerFinder implements Runnable {

    private final GameLoop gameLoop;
    private Collection<Player> participatingPlayers;
    private final int[] winningCombination;
    private int foundNumbers;


    public WinnerFinder(GameLoop gameLoop, PlayerCollection players) {
        this.gameLoop = gameLoop;
        this.winningCombination = new int[6];
        this.participatingPlayers = players.getAllPlayers();
    }

    @Override
    public void run() {
        int remainingTickets;
        while(this.foundNumbers < 6) {
            int extractedNumber = 0;
            try {
                extractedNumber = gameLoop.getWinnerNumber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.winningCombination[foundNumbers++] = extractedNumber;

            int[] partialCombination = new int[foundNumbers];
            partialCombination = Arrays.stream(partialCombination).sorted().toArray();
            System.arraycopy(this.winningCombination, 0, partialCombination, 0, foundNumbers);
            final int[] finalPartialCombination = partialCombination;
            this.participatingPlayers = this.participatingPlayers.parallelStream()
                    .filter(p -> p.checkWinningCombination(finalPartialCombination))
                    .collect(Collectors.toList());

            remainingTickets =  participatingPlayers.parallelStream().mapToInt(p -> p.getWinningTicketsId().size())
                    .sum();
            System.out.println(remainingTickets + " lucky combinations");
        }


        LotteryTicket lotteryTicket = new LotteryTicket("WinningTicket", 6);
        lotteryTicket.setLuckyNumbers(this.winningCombination);
        System.out.println(lotteryTicket);
        if (this.participatingPlayers.size() == 0) {
            System.out.println("No winners");
        }
        else {
            for (Player winner : this.participatingPlayers) {
                System.out.println(winner.printWinnerTickets());
            }
        }
    }
}
