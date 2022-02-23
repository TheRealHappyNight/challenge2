package source.threads.generators;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class TicketUIDSupplier implements Runnable{
    private boolean hasFinishedGenerating;
    private final String initialSerial;
    private final BlockingDeque<String> uid;
    private int initialSeed;
    private boolean work;

    public TicketUIDSupplier(int initialSeed, String initialSerial) {
        this.initialSeed = initialSeed;
        this.initialSerial = initialSerial;
        uid = new LinkedBlockingDeque<>(100);
        work = true;
    }

    public boolean hasFinished() {
        return this.hasFinishedGenerating;
    }

    @Override
    public void run() {
        while(work) {
            if (this.uid.size() < 95) {
                String uid = initialSerial + (initialSeed++);
                this.uid.add(uid);
            }
        }

        this.hasFinishedGenerating = true;
    }

    public String getUID() {
        try {
            return this.uid.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void stop() {
        work = false;
    }

    @Override
    public String toString() {
        return "TicketUIDSupplier{" +
                "hasFinishedGenerating=" + hasFinishedGenerating +
                ", initialSeed=" + initialSeed +
                ", initialSerial='" + initialSerial + '\'' +
                ", uid=" + uid +
                ", work=" + work +
                '}';
    }
}
