package source.models;

import source.interfaces.Processable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PlayerCollection {
    private final List<Player> players = Collections.synchronizedList(new ArrayList<>());

    public PlayerCollection() { }

    public PlayerCollection(Player[] players) {
        if (null == players) {
            return;
        }
        this.players.addAll(Arrays.asList(players));
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public Player getPlayer(String name) {
        return this.players.parallelStream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public void removePlayer(String name) {
        this.players.removeIf(p -> p.getName().equalsIgnoreCase(name));
    }

    public int getNoPlayers() {
        return players.size();
    }

    public Player[] process(Processable processor) {
        if (null == processor) {
            throw new IllegalArgumentException();
        }

        return processor.process(new ArrayList<>(this.players));
    }

    public void printToFile(String fileName) throws IOException {
        if (null != fileName && !" ".equals(fileName)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
                for (Player player : this.players) {
                    bufferedWriter.write(player.print());
                }
            }
        }
    }

    public ArrayList<Player> getAllPlayers() {
        return new ArrayList<>(this.players);
    }
}
