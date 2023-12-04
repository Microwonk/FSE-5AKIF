package net.microwonk.generics;

import lombok.val;

public class Main {

    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();

        list.add("hallo");
        list.add("hallo1");
        list.add("hallo2");
        list.add("hallo3");
        list.insert("tschüss", 0);
        list.insert("larh", 5);
        list.remove(10);
        list.remove("hallo");

        val otherList = list.stream().filter(s -> s.contains("hallo")).collect(MyLinkedList.collector());
        System.out.println(otherList.size());
        System.out.println(list.size());

        MyLinkedList.merge(list, otherList).forEach(System.out::println);
    }

}
