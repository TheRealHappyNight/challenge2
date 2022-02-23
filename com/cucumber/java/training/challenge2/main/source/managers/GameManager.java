package source.managers;

import source.interfaces.Processable;
import source.models.Player;
import source.models.PlayerCollection;
import source.settings.LotterySettings;
import source.singleton.GameSettings;
import source.statistics.Processors;
import source.threads.PlayerBuilder;
import source.threads.generators.RandomGeneratorSupplier;
import source.threads.generators.TicketUIDSupplier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GameManager {
    private final PlayerCollection players;
    private final RandomGeneratorSupplier randomGeneratorSupplier;
    private final TicketUIDSupplier ticketUIDSupplier;

    public GameManager(RandomGeneratorSupplier randomGeneratorSupplier, TicketUIDSupplier ticketUIDSupplier) {
        this.players = new PlayerCollection();
        this.randomGeneratorSupplier = randomGeneratorSupplier;
        this.ticketUIDSupplier = ticketUIDSupplier;
    }

    public void start() {
        try {
            readPlayer(GameSettings.getInstance().playersFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        generateStatistics();
    }

    private void readPlayer(String fileName) throws IOException {
        LinkedList<CompletableFuture<Void>> threadList = new LinkedList<>();
        LotterySettings lotterySettings = GameSettings.getInstance().lotterySettings;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while (null != (line = bufferedReader.readLine()) &&
                    lotterySettings.getNoCurrentPlayers() < lotterySettings.getMaxNoPlayers()) {
                String name = line.trim();
                int age = Integer.parseInt(bufferedReader.readLine());
                int noTickets = Integer.parseInt(bufferedReader.readLine());
                ArrayList<Integer> noLuckyNumbers = new ArrayList<>(noTickets);
                for (int index = 0; index < noTickets; ++index) {
                    noLuckyNumbers.add(Integer.parseInt(bufferedReader.readLine()));
                }

                Player player = new Player(name, age, noTickets);
                PlayerBuilder playerBuilder = new PlayerBuilder(player, noLuckyNumbers, randomGeneratorSupplier,
                        ticketUIDSupplier, this);
                threadList.add(CompletableFuture.runAsync(playerBuilder));
            }
        }

        finishedGenerating(threadList);

        ticketUIDSupplier.stop();
        randomGeneratorSupplier.stop();
        players.printToFile("report.txt");
    }

    private void finishedGenerating(LinkedList<CompletableFuture<Void>> threadList) {
        for (var thread : threadList) {
            if (!thread.isDone()) {
                try {
                    thread.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void generateStatistics() {
        try {
            processStatistic(Processors::getPlayersThatAreParticipatingAndArePreviousWinners,
                    "FirstStream.txt");

            processStatistic(Processors::getPlayersThatUsedLastTimeWinningCombination,
                    "SecondStream.txt");

            processStatistic(Processors::getPlayerWithMostTickets,
                    "ThirdStream.txt");

            processStatistic(Processors::getPlayersWithTicketsThatHave13,
                    "FourthStream.txt");

            processStatistic(Processors::getPlayersWith4ofTheMostUsedNumbers,
                    "FifthStream.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processStatistic(Processable processable, String fileName) throws IOException {
        PlayerCollection statistic = new PlayerCollection(players.process(processable));
        statistic.printToFile(fileName);
    }

    public void addPlayer(Player player) {
        players.addPlayer(player);
    }
}
