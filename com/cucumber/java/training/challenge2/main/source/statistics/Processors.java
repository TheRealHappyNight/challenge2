package source.statistics;

import source.models.LotteryTicket;
import source.models.Player;
import source.settings.LotterySettings;
import source.singleton.GameSettings;

import java.util.ArrayList;

public class Processors {
    public static Player[] getPlayersThatAreParticipatingAndArePreviousWinners(ArrayList<Player> list) {
        LotterySettings lotterySettings = GameSettings.getInstance().lotterySettings;
        return list.parallelStream()
                .filter(player -> player.isParticipating() && lotterySettings.isPreviousWinner(player.getName()))
                .toArray(Player[]::new);
    }

    public static Player[] getPlayersThatUsedLastTimeWinningCombination(ArrayList<Player> list) {
        LotterySettings lotterySettings = GameSettings.getInstance().lotterySettings;
        return list.parallelStream()
                .filter(player -> player.getTickets().stream().anyMatch(lotterySettings::previousUsedCombination))
                .toArray(Player[]::new);
    }

    public static Player[] getPlayerWithMostTickets(ArrayList<Player> list) {
        return null;
    }

    public static Player[] getPlayersWithTicketsThatHave13(ArrayList<Player> list) {
        return list.parallelStream()
                .filter(p -> p.getTickets().stream().anyMatch(t -> t.contains(13)))
                .toArray(Player[]::new);
    }

    public static Player[] getPlayersWith4ofTheMostUsedNumbers(ArrayList<Player> list) {
        return null;
    }
}
