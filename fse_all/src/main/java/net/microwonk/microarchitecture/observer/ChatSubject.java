package net.microwonk.microarchitecture.observer;

import java.util.ArrayList;
import java.util.List;

// macht hier auf jeden Fall sinn, da ein Chat nichts bringt, wenn keiner zuhören kann
public class ChatSubject implements Subject {

    private final List<Observer> observers;

    public ChatSubject() {
        this.observers = new ArrayList<>();
    }

    public void sendChat(Chat chat) {
        notifyObservers(chat);
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Object message) {
        observers.forEach(o -> o.update(message));
    }

    public record Chat(String person, String message) {
        @Override
        public String toString() {
            return person + ": " + message;
        }
    }
}
