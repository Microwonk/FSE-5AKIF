package net.microwonk.generics;

import lombok.val;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();

        list.add("hallo");
        list.add("hallo1");
        list.add("hallo2");
        list.add("hallo3");
        list.add(0, "tschüss");
        list.add(3, "laiahoasdhaksjrh");
        System.out.println(list.peek());
        System.out.println(list.pop());
        System.out.println(list.push("Hallo Welt!"));
        list.remove(3);
        list.remove("hallo");

        val otherList = list.stream().filter(s -> s.contains("hallo")).collect(MyLinkedList.collector());
        System.out.println(otherList.size());
        System.out.println(list.size());

        list.forEach(System.out::println);
        System.out.printf("%n");
        otherList.forEach(System.out::println);

        for (String s: otherList) {
            // do something
        }

        System.out.printf("%s, %s", list.size(), otherList.size());
    }

}
