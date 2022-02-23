package source;

import source.exceptions.NoGameSettingsException;
import source.managers.GameManager;
import source.settings.LotterySettings;

public class Main {
    public static void main(String[] args) throws NoGameSettingsException {
        if (args.length < 2) {
            throw new NoGameSettingsException();
        }

        GameManager.getInstance().init(args[0]);


    }
}
