package net.microwonk.microarchitecture.observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogObserver implements Observer {

    private final BufferedWriter writer;

    public LogObserver(String fileName) {
        try {
            this.writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Writer konnte nicht initialisiert werden");
        }
    }

    @Override
    public void update(Object message) {
        try {
            writer.write(message.toString() + '\n');
            writer.flush();
        } catch (IOException e) {
            System.err.println("Nachricht: " + message + " konnte nicht geschickt werden");
        }
    }
}
