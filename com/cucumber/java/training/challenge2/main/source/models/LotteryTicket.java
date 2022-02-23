package source.models;

import source.exceptions.LotteryTicketException;
import source.interfaces.Printable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LotteryTicket implements Cloneable, Printable, Comparable<LotteryTicket> {
    private final String UID;
    private final int noNumbers;
    private final int[] luckyNumbers;

    public LotteryTicket(String UID, int noNumbers) {
        this.UID = UID;
        this.noNumbers = noNumbers;
        this.luckyNumbers = new int[this.noNumbers];
    }

    private LotteryTicket(LotteryTicket lotteryTicket) {
        this.UID = lotteryTicket.UID;
        this.noNumbers = lotteryTicket.noNumbers;
        this.luckyNumbers = new int[this.noNumbers];
        setLuckyNumbers(lotteryTicket.luckyNumbers);
    }

    public void setLuckyNumbers(int[] luckyNumbers) {
        luckyNumbers = Arrays.stream(luckyNumbers).sorted().toArray();
        System.arraycopy(luckyNumbers, 0, this.luckyNumbers, 0, luckyNumbers.length);
    }

    /**
     * To use after setting LuckyNumber
     * @return Whether the combination is valid
     */
    public boolean isLuckyNumbersValid() {
        if (null == this.luckyNumbers || this.luckyNumbers.length != this.noNumbers) {
            return false;
        }

        return Arrays.stream(luckyNumbers).distinct().filter((number) -> number > 0 && number < 50)
                .count() == this.noNumbers;
    }

    public String getUID() {
        return this.UID;
    }

    public int getNoNumbers() {
        return this.noNumbers;
    }

    public int getNumber(int index) {
        if (index > noNumbers || index < 0) {
            throw new IllegalArgumentException();
        }

        return this.luckyNumbers[index];
    }

    public boolean isValidSequence() throws LotteryTicketException {
        if (this.noNumbers < 6 || this.noNumbers > 10) {
            throw new LotteryTicketException();
        }

        return true;
    }

    public boolean contains(int num) {
        return Arrays.stream(this.luckyNumbers).anyMatch(val -> val == num);
    }

    public boolean containsSequence(int[] list) {
        for(int num : list) {
            if (!contains(num)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public LotteryTicket clone() {
        return new LotteryTicket(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryTicket that = (LotteryTicket) o;

        if (this.noNumbers != that.noNumbers) {
            return false;
        }

        for (int index = 0; index < that.noNumbers; ++index) {
            if (this.luckyNumbers[index] != that.luckyNumbers[index]) {
                return false;
            }
        }

        return UID.equals(that.UID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UID);
    }

    @Override
    public int compareTo(LotteryTicket that) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.UID, that.UID);
    }

    @Override
    public String toString() {
        return print();
    }

    @Override
    public String print() {
        StringBuilder output = new StringBuilder(this.UID + "[");
        for(int index = 0; index < this.luckyNumbers.length - 1; ++index) {
            output.append(this.luckyNumbers[index]).append(",");
        }
        output.append(this.luckyNumbers[this.luckyNumbers.length - 1]).append("]>");
        return output.toString();
    }
}
