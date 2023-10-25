package net.microwonk.microarchitecture.observer;

public class Chatter implements Observer {

    private final String name;

    public Chatter(String name) {
        this.name = name;
    }

    @Override
    public void update(Object message) {
        System.out.println(name + " hat die Nachricht: '" + message + "' erhalten.");
    }

    public void sendChatFrom(ChatSubject sendTo, String message) {
        sendTo.sendChat(new ChatSubject.Chat(name, message));
    }
}
