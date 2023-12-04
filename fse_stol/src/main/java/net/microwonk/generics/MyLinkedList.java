package net.microwonk.generics;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MyLinkedList<E> implements Iterable<E> {

    private ElementWrapper<E> head;
    private int size;

    public void add(final E toAdd) {
        ElementWrapper<E> newE = new ElementWrapper<>(toAdd);
        if (head == null) {
            head = newE;
        } else {
            last().nextElement = newE;
        }
        size++;
    }

    public void insert(final E toInsert, int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        ElementWrapper<E> newE = new ElementWrapper<>(toInsert);

        if (index == 0) {
            newE.nextElement = head;
            head = newE;
        } else {
            ElementWrapper<E> current = head;
            ElementWrapper<E> previous = null;
            int currentIndex = 0;

            while (current != null && currentIndex < index) {
                previous = current;
                current = current.nextElement;
                currentIndex++;
            }

            if (currentIndex == index) {
                previous.nextElement = newE;
                newE.nextElement = current;
            } else {
                last().nextElement = newE;
            }
        }
        size++;
    }

    public int size() {
        return size;
    }

    private ElementWrapper<E> last() {
        ElementWrapper<E> lastE = head;
        if (lastE != null) {
            while (lastE.nextElement != null) {
                lastE = lastE.nextElement;
            }
        }
        return lastE;
    }

    @SuppressWarnings("unused")
    public E remove(final E element) {
        if (head == null) return null;

        if (head.data.equals(element)) {
            E removedData = head.data;
            head = head.nextElement;
            return removedData;
        }

        ElementWrapper<E> current = head;
        ElementWrapper<E> previous = current;

        while (current != null && !current.data.equals(element)) {
            previous = current;
            current = current.nextElement;
        }

        if (current == null) return null;

        E removedData = current.data;
        previous.nextElement = current.nextElement;

        size--;
        return removedData;
    }

    @SuppressWarnings("unused")
    public E remove(final int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        if (head == null) return null;

        if (index == 0) {
            E removedData = head.data;
            head = head.nextElement;
            return removedData;
        }

        ElementWrapper<E> current = head;
        ElementWrapper<E> previous = null;
        int currentIndex = 0;

        while (current != null && currentIndex < index) {
            previous = current;
            current = current.nextElement;
            currentIndex++;
        }

        if (current == null) return null;

        E removedData = current.data;
        previous.nextElement = current.nextElement;

        size--;
        return removedData;
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new Iterator();
    }

    public Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        for (E e : this) {
            action.accept(e);
        }
    }

    public static <E> Collector<E, ?, MyLinkedList<E>> collector() {
        return Collector.of(
                MyLinkedList::new,
                MyLinkedList::add,
                MyLinkedList::merge
        );
    }

    public static <T> MyLinkedList<T> merge(MyLinkedList<T> left, MyLinkedList<T> right) {
        left.last().nextElement = right.head;
        left.size += right.size;
        return left;
    }

    @Override
    public String toString() {
        return "[" + head.toString() + ']';
    }

    private class Iterator implements java.util.Iterator<E> {
        private ElementWrapper<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            E data = current.data;
            current = current.nextElement;
            return data;
        }
    }

    private static class ElementWrapper<T> {
        T data;
        ElementWrapper<T> nextElement;

        public ElementWrapper(final T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString() + ", " + (nextElement != null ? nextElement.toString() : "");
        }
    }
}