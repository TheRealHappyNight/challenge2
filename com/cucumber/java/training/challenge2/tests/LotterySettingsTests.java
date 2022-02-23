import org.junit.Test;
import source.exceptions.NoGameSettingsException;
import source.exceptions.NoAvailablePlayersException;
import source.settings.LotterySettings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.*;

public class LotterySettingsTests {

    @Test
    public void testCreateLotterySettingsCorrect() {
        LotterySettings lotterySettings = new LotterySettings("game_settings.txt");
    }

    @Test
    public void testClone() {
        LotterySettings lotterySettings = new LotterySettings("game_settings.txt");
        lotterySettings.incrementNoCurrentPlayers();
        LotterySettings lotterySettings1 = lotterySettings.clone();

        assertThat(lotterySettings.getNoCurrentPlayers())
                .isEqualTo(lotterySettings1.getNoCurrentPlayers());
        assertThat(lotterySettings.getNoLuckyCombinations())
                .isEqualTo(lotterySettings1.getNoLuckyCombinations());
        assertThat(lotterySettings.getNoPreviousWinners())
                .isEqualTo(lotterySettings1.getNoPreviousWinners());
    }

    @Test
    public void testIfThereIsAtLeastOnePublicConstructor() {
        Constructor<?>[] constructors = LotterySettings.class.getConstructors();
        for(Constructor<?> constructor : constructors) {
            if (constructor.getModifiers() == Modifier.PUBLIC) {
                return;
            }
        }

        fail("No public constructor");
    }

    @Test
    public void testIfConstructorHasRightArguments() {
        Constructor<?>[] constructors = LotterySettings.class.getConstructors();
        for(Constructor<?> constructor : constructors) {
            if (constructor.getModifiers() == Modifier.PUBLIC) {
                Class<?>[] parameters = constructor.getParameterTypes();
                if(parameters.length == 1) {
                    if(parameters[0].getSimpleName().equalsIgnoreCase("string")) {
                        return;
                    }
                }
            }
        }

        fail("No public constructor");
    }
}
