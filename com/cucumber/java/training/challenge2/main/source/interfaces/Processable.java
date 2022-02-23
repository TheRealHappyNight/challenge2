package source.interfaces;

import source.models.Player;

import java.util.ArrayList;

public interface Processable {
    Player[] process(ArrayList<Player> players);
}
