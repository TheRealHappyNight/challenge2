package source.models;

import source.interfaces.Processable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PlayerCollection {
    List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private Processable processor;

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player getPlayer(String name) {
        return players.parallelStream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public void removePlayer(String name) {
        players.removeIf(p -> p.getName().equalsIgnoreCase(name));
    }

    public int getNoPlayers() {
        return players.size();
    }

    public Player[] process(Processable processor) {
        if (null == processor) {
            throw new IllegalArgumentException();
        }

        return this.processor.process(new ArrayList<>(this.players));
    }

    public void printToFile(String fileName) throws IOException {
        if (null != fileName && !" ".equals(fileName)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
                for (Player player : players) {
                    bufferedWriter.write(player.print());
                }
            }
        }
    }
}
