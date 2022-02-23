package source.threads;

import source.managers.GameManager;
import source.models.LotteryTicket;
import source.models.Player;
import source.singleton.GameSettings;
import source.threads.generators.RandomGeneratorSupplier;
import source.threads.generators.TicketUIDSupplier;

import java.util.ArrayList;
import java.util.List;

public class PlayerBuilder implements Runnable{

    private final Player player;
    private final List<Integer> noLuckyNumbers;
    private final RandomGeneratorSupplier randomGeneratorSupplier;
    private final TicketUIDSupplier ticketUIDSupplier;
    private final GameManager gameManager;

    public PlayerBuilder(Player player, ArrayList<Integer> noLuckyNumbers,
                         RandomGeneratorSupplier randomGeneratorSupplier, TicketUIDSupplier ticketUIDSupplier,
                         GameManager gameManager) {
        this.player = player;
        this.noLuckyNumbers = new ArrayList<>(noLuckyNumbers);
        this.randomGeneratorSupplier = randomGeneratorSupplier;
        this.ticketUIDSupplier = ticketUIDSupplier;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        ArrayList<LotteryTicket> lotteryTickets = new ArrayList<>();
        int index = 0;
        while(index < this.noLuckyNumbers.size()) {
            LotteryTicket lotteryTicket = new LotteryTicket(ticketUIDSupplier.getUID(), this.noLuckyNumbers.get(index));
            lotteryTicket.setLuckyNumbers(randomGeneratorSupplier.getLuckyNumbers(this.noLuckyNumbers.get(index)));
            if (lotteryTicket.isLuckyNumbersValid()) {
                lotteryTickets.add(lotteryTicket);
                ++index;
            }
        }

        player.setPlayerTickets(lotteryTickets);
        this.gameManager.addPlayer(player);
        GameSettings.getInstance().lotterySettings.incrementNoCurrentPlayers();
    }
}
