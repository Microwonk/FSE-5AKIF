package net.microwonk.test;

import lombok.*;

public class Test {
    public static void main(String[] args) {
        val finalVar = 19;
        System.out.println(finalVar);

        val person = new Person(finalVar, "Nicolas", "Frey");

        var sumElse = person.withAge(finalVar + 7);
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @With
    @Builder
    public static class Person {
        @Setter private int age;
        @Setter private String name;
        @Setter private String lastName;
    }
}
