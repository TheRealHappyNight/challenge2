package source.models;

import source.interfaces.Printable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player implements Printable {
    private final String name;
    private final int age;
    private final ArrayList<LotteryTicket> tickets;
    private final Set<String> winningTicketsId;

    public Player(String name, int age, int noTickets) {
        this.name = name;
        this.age = age;
        this.tickets = new ArrayList<>(noTickets);
        this.winningTicketsId = new HashSet<>();
    }

    public void setPlayerTickets(ArrayList<LotteryTicket> tickets) {
        if (null == tickets) {
            return;
        }

        for(LotteryTicket ticket : tickets) {
            this.tickets.add(ticket.clone());
        }
    }

    public boolean checkWinningCombination(int[] winningNumbers) {
        LotteryTicket lotteryTicket = new LotteryTicket("copy", winningNumbers.length);
        lotteryTicket.setLuckyNumbers(winningNumbers);
        for(LotteryTicket ticket : tickets) {
            if(ticket.equals(lotteryTicket)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<LotteryTicket> getTickets() {
        return this.tickets;
    }

    public Set<String> getWinningTicketsId() {
        return this.winningTicketsId;
    }

    @Override
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" (").append(age).append(")\n");
        int count = 1;
        for(LotteryTicket lotteryTicket : tickets) {
            stringBuilder.append("Ticket ").append(count++).append(" <").append(lotteryTicket.getUID());
            stringBuilder.append(lotteryTicket.print()).append("\n");
        }
        return stringBuilder.toString();
    }
}
