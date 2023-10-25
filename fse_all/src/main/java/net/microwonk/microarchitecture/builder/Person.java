package net.microwonk.microarchitecture.builder;

import net.microwonk.microarchitecture.WR;

import java.util.*;


public class Person {

    private int age;
    private String name;
    private String lastName;
    private Gender gender;
    private final Set<Person> friends;

    private Person() {
        this.friends = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                '}';
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public Set<Person> getFriends() {
        return friends;
    }

    public enum Gender {
        M,
        F,
        D;
    }

    public final static Map<String, Person> people = new HashMap<>();

    // Das Builder-Muster ist bei dieser Klasse sehr hilfreich, da man einige Datenfelder (und die people-Liste) populaten muss.
    public static class Builder {
        private Person person;

        public Builder() {
            this.person = new Person();
        }

        public Builder reset() {
            // YAGNI: Das Zurücksetzen eines Person-Objekts ist möglicherweise nicht notwendig und könnte entfernt werden, wenn nicht benötigt.
            this.person = new Person();
            return this;
        }

        public Builder withAge(int age) {
            person.age = age;
            return this;
        }

        public  Builder withName(String name) {
            person.name = name;
            return this;
        }

        public Builder withLastName(String lastName) {
            person.lastName = lastName;
            return this;
        }

        public Builder withGender(Gender gender) {
            person.gender = gender;
            return this;
        }

        public Builder addFriend(String firstName, String lastName) {
            var friend = people.get((firstName + lastName));
            if (friend == null) return this;
            this.person.friends.add(friend);
            friend.friends.add(person);
            return this;
        }

        public Builder addFriendGroupFrom(String firstName, String lastName) {
            var friend = people.get(firstName + lastName);
            if (friend == null) return this;
            // DRY: Der Code zum Hinzufügen von Freunden und Aktualisieren der Freunde von beiden Personen ist hier wiederholt.
            // Dies könnte in eine separate Methode ausgelagert werden, um die Redundanz zu reduzieren.
            friend.friends.forEach(f -> addFriend(f.name, f.lastName));
            return addFriend(lastName, firstName);
        }

        public Person build() {
            people.put(person.name + person.lastName, person);
            return person;
        }
    }
}