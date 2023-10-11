package net.microwonk.microarchitecture;

import net.microwonk.microarchitecture.adapter.UmrechnungsAdapter;
import net.microwonk.microarchitecture.builder.Person;
import net.microwonk.microarchitecture.chainOfResonsibility.DOLLAR2EUR;
import net.microwonk.microarchitecture.chainOfResonsibility.EUR2DOLLAR;
import net.microwonk.microarchitecture.chainOfResonsibility.EUR2YEN;
import net.microwonk.microarchitecture.chainOfResonsibility.WRHandler;
import net.microwonk.microarchitecture.decorator.FixGebuhrenDecorator;
import net.microwonk.microarchitecture.decorator.ProzentGebuhrenDecorator;
import net.microwonk.microarchitecture.observer.ChatSubject;
import net.microwonk.microarchitecture.observer.LogObserver;

import java.util.Arrays;

import static net.microwonk.microarchitecture.builder.Person.people;

public class Main {
    public static void main(String[] args) {

        //testChainOfResponsibility();
        System.out.println("\n");

        //testDecorator();
        System.out.println("\n");

        //testBuilder();
        System.out.println("\n");

        //testAdapter();
        System.out.println("\n");

        testObserver();
    }

    private static void testObserver() {
        ChatSubject cs = new ChatSubject();
        LogObserver lo = new LogObserver("test.txt");
        cs.addObserver(lo);

        cs.sendChat(new ChatSubject.Chat("Nicolas", "Hallo ich bin Nicolas!"));
    }

    private static void testAdapter() {
        var adapter = new UmrechnungsAdapter(new EUR2YEN());
        double[] betraegeInEUR = { 100.0, 200.0, 300.0, 450.0 };

        double gesamtBetragInZielWährung = adapter.sammelumrechnen(betraegeInEUR, "EUR2YEN");

        System.out.println("Gesamtbetrag in Zielwährung: " + gesamtBetragInZielWährung);
    }

    private static void testChainOfResponsibility() {
        var chain = new WRHandler();
        chain.addHandler(new EUR2DOLLAR());
        chain.addHandler(new EUR2YEN());
        chain.addHandler(new DOLLAR2EUR());

        final int betrag = 100;

        // Chain Of Responsibilty test und Template Method
        System.out.println(chain.umrechnen("EUR2DOLLAR", betrag));
        System.out.println(chain.umrechnen("EUR2YEN", betrag));
        System.out.println(chain.umrechnen("DOLLAR2EUR",  betrag));
    }

    private static void testDecorator() {
        final int betrag = 1000;
        // Decorator test
        System.out.println(new FixGebuhrenDecorator(new EUR2YEN()).umrechnen("EUR2YEN", betrag));
        System.out.println(new ProzentGebuhrenDecorator(new EUR2YEN()).umrechnen("EUR2YEN", betrag));
    }

    private static void testBuilder() {
        // Builder test
        var pb = new Person.Builder();
        var p1 = pb.withAge(17)
                .withName("Josef")
                .withGender(Person.Gender.M)
                .withLastName("Marse")
                .build();
        pb.reset();
        var p2 = pb.withAge(19)
                .withName("Josephine")
                .withGender(Person.Gender.F)
                .withLastName("Marse")
                .addFriend("Josef", "Marse")
                .build();
        pb.reset();

        var p3 = pb.withAge(29)
                .withName("Johann")
                .withGender(Person.Gender.M)
                .withLastName("A")
                .addFriend("Josef", "Marse")
                .addFriend("Josephine", "Marse")
                .build();
        pb.reset();

        var p4 = pb.withAge(15)
                .withName("Marlene")
                .withGender(Person.Gender.M)
                .withLastName("B")
                .addFriendGroupFrom("Johann", "A")
                .build();

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        System.out.println(Arrays.toString(p1.getFriends().toArray()));
    }
}
