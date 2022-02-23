package source;

import source.exceptions.NoGameSettingsException;
import source.managers.GameManager;
import source.singleton.GameSettings;
import source.threads.generators.RandomGeneratorSupplier;
import source.threads.generators.TicketUIDSupplier;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws NoGameSettingsException {
        if (args.length < 2) {
            throw new NoGameSettingsException();
        }

        GameSettings.getInstance().init(args[0], args[1]);

        int initialSeed = 23453456;
        String uidPrefix = "RX";
        TicketUIDSupplier ticketUIDSupplier = new TicketUIDSupplier(initialSeed, uidPrefix);
        CompletableFuture.runAsync(ticketUIDSupplier);

        initialSeed = 3246526;
        RandomGeneratorSupplier randomGeneratorSupplier = new RandomGeneratorSupplier(initialSeed);
        CompletableFuture.runAsync(randomGeneratorSupplier);

        GameManager gameManager = new GameManager(randomGeneratorSupplier, ticketUIDSupplier);
        gameManager.start();
    }
}
