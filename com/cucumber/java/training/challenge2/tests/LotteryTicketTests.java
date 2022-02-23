import source.exceptions.LotteryTicketException;
import source.models.LotteryTicket;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.*;

public class LotteryTicketTests {

    @Test
    public void testCreateValidLotteryTicket() {
        try {
            LotteryTicket lotteryTicket = new LotteryTicket("TX85", 8);
            lotteryTicket.setLuckyNumbers(new int[]{1,2,3,4,5,6,7,8});
            if (!lotteryTicket.isValidSequence())
                fail("Lottery ticket is not valid");
        } catch (LotteryTicketException e) {
            e.printStackTrace();
            fail("");
        }
    }

    @Test
    public void testGetLotteryNumberCorrect() {
        try {
            LotteryTicket lotteryTicket = new LotteryTicket("TX85", 8);
            lotteryTicket.setLuckyNumbers(new int[]{1,2,3,4,5,6,7,8});
            if (!lotteryTicket.isValidSequence())
                fail("Lottery ticket is not valid");

            assertThat(lotteryTicket.getNumber(0)).isEqualTo(1);

            assertThatThrownBy(() -> lotteryTicket.getNumber(100))
                    .isInstanceOf(IllegalArgumentException.class);
        } catch (LotteryTicketException e) {
            e.printStackTrace();
            fail("");
        }
    }

    @Test
    public void testIsValidSequenceFail() {
        try {
            LotteryTicket lotteryTicket = new LotteryTicket("TX85", 8);
            lotteryTicket.setLuckyNumbers(new int[]{50,2,3,4,5,6,7,8});
            assertThat(lotteryTicket.isValidSequence()).isTrue();
        } catch (LotteryTicketException e) {
            e.printStackTrace();
            fail("");
        }
    }

    @Test
    public void testGetNoNumbers() {
        LotteryTicket lotteryTicket = new LotteryTicket("TX85", 8);
        assertThat(lotteryTicket.getNoNumbers()).isEqualTo(8);
    }

    @Test
    public void testEqualsAndClone() {
        try {
            LotteryTicket lotteryTicket = new LotteryTicket("TX85", 8);
            lotteryTicket.setLuckyNumbers(new int[]{1,2,3,4,5,6,7,8});
            if (!lotteryTicket.isValidSequence())
                fail("Lottery ticket is not valid");

            LotteryTicket lotteryTicket1 = lotteryTicket.clone();

            assertThat(lotteryTicket.equals(lotteryTicket1)).isTrue();
        } catch (LotteryTicketException e) {
            e.printStackTrace();
            fail("");
        }
    }

    @Test
    public void testUidIsFinalAndPrivate() {
        try {
            Field uid = LotteryTicket.class.getDeclaredField("UID");
            assertThat(uid.getModifiers()).isEqualTo(18);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail("");
        }
    }

    @Test
    public void testIfThereIsAtLeastOnePublicConstructor() {
        Constructor<?>[] constructors = LotteryTicket.class.getConstructors();
        for(Constructor<?> constructor : constructors) {
            if (constructor.getModifiers() == Modifier.PUBLIC) {
                return;
            }
        }

        fail("No public constructor");
    }

    @Test
    public void testIfConstructorHasRightArguments() {
        Constructor<?>[] constructors = LotteryTicket.class.getConstructors();
        for(Constructor<?> constructor : constructors) {
            if (constructor.getModifiers() == Modifier.PUBLIC) {
                Class<?>[] parameters = constructor.getParameterTypes();
                if(parameters.length == 2) {
                    assertThat(parameters[0].getSimpleName()).isEqualToIgnoringCase("string");
                    assertThat(parameters[1].getSimpleName()).isEqualToIgnoringCase("int");
                    return;
                }
            }
        }

        fail("No public constructor");
    }

    @Test
    public void testPrint() {
        try {
            LotteryTicket lotteryTicket = new LotteryTicket("TX85", 8);
            lotteryTicket.setLuckyNumbers(new int[]{1,2,3,4,5,6,7,8});
            if (!lotteryTicket.isValidSequence())
                fail("Lottery ticket is not valid");
            lotteryTicket.print();
        } catch (LotteryTicketException e) {
            e.printStackTrace();
            fail("");
        }
    }

}
