package source.managers;

import com.sun.source.doctree.BlockTagTree;
import source.settings.LotterySettings;

public class GameManager {

    private static GameManager instance;
    public LotterySettings lotterySettings;

    private GameManager() {

    }

    public static GameManager getInstance() {
        if (null == instance) {
            instance = new GameManager();
        }

        return instance;
    }

    public void init(String fileName) {
        lotterySettings = new LotterySettings(fileName);
    }
}
