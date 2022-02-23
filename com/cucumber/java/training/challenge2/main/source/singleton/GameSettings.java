package source.singleton;

import source.settings.LotterySettings;

public class GameSettings {

    private static GameSettings instance;
    public LotterySettings lotterySettings;
    public String playersFileName;

    private GameSettings() { }

    public static GameSettings getInstance() {
        if (null == instance) {
            instance = new GameSettings();
        }

        return instance;
    }

    public void init(String fileName, String playersFileName) {
        lotterySettings = new LotterySettings(fileName);
        this.playersFileName = playersFileName;
    }
}
