package source.models;

import source.interfaces.Printable;

import java.util.*;

public class Player implements Printable {
    private final String name;
    private final int age;
    private final ArrayList<LotteryTicket> tickets;
    private Set<String> winningTicketsId;

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

    public boolean checkWinningCombination(int[] partialWinningNumbers) {
        Set<String> id = new HashSet<>();
        boolean found = false;
        for(LotteryTicket ticket : this.tickets) {
            if (ticket.containsSequence(partialWinningNumbers)) {
                id.add(ticket.getUID());
                found = true;
            }
        }

        this.winningTicketsId = id;
        return found;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isParticipating() {
        return this.tickets.size() != 0;
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
            stringBuilder.append("Ticket ").append(count++).append(" <");
            stringBuilder.append(lotteryTicket.print()).append("\n");
        }
        return stringBuilder.toString();
    }

    public String printWinnerTickets() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" (").append(age).append(")\n");
        int count = 1;
        for(LotteryTicket lotteryTicket : tickets) {
            if (!this.winningTicketsId.contains(lotteryTicket.getUID())) {
                continue;
            }

            stringBuilder.append("Ticket ").append(count++).append(" <");
            stringBuilder.append(lotteryTicket.print()).append("\n");
        }
        return stringBuilder.toString();
    }
}
