package net.microwonk.generics;

// imports für nicht implementierte methoden
import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import lombok.SneakyThrows;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MyLinkedList<E> implements List<E>, Iterable<E> {

    private ElementWrapper<E> head;
    private int size = 0;

    @Override
    public boolean add(final E toAdd) {
        ElementWrapper<E> newE = new ElementWrapper<>(toAdd);
        if (head == null) {
            head = newE;
        } else {
            last().nextElement = newE;
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        ElementWrapper<E> newE = new ElementWrapper<>(element);

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

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
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

    @Override
    public boolean remove(final Object element) {
        if (head == null) return false;

        if (head.data.equals(element)) {
            head = head.nextElement;
            size--;
            return true;
        }

        ElementWrapper<E> current = head;
        ElementWrapper<E> previous = current;

        while (current != null && !current.data.equals(element)) {
            previous = current;
            current = current.nextElement;
        }

        if (current == null) return false;

        previous.nextElement = current.nextElement;

        size--;
        return true;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        ElementWrapper<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.nextElement;
        }

        return current.data;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        ElementWrapper<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.nextElement;
        }

        E oldValue = current.data;
        current.data = element;
        return oldValue;
    }

    @Override
    public boolean contains(Object o) {
        for (E element : this) {
            if (Objects.equals(element, o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        MyLinkedList<E> newList = new MyLinkedList<>();
        newList.addAll(c);

        if (index == 0) {
            newList.last().nextElement = head;
            head = newList.head;
        } else {
            ElementWrapper<E> current = head;
            ElementWrapper<E> previous = null;
            for (int i = 0; i < index; i++) {
                previous = current;
                current = current.nextElement;
            }

            previous.nextElement = newList.head;
            newList.last().nextElement = current;
        }

        size += newList.size;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (c.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }

    // List iterator nicht implementiert

    @SneakyThrows
    @Override
    public ListIterator<E> listIterator() {
        throw new NotImplementedException("");
    }

    @SneakyThrows
    @Override
    public ListIterator<E> listIterator(int index) {
        throw new NotImplementedException("");
    }

    // --------------------------------

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("FromIndex: " + fromIndex + ", ToIndex: " + toIndex + ", Size: " + size);
        }

        MyLinkedList<E> subList = new MyLinkedList<>();
        ElementWrapper<E> current = head;

        for (int i = 0; i < fromIndex; i++) {
            current = current.nextElement;
        }

        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(current.data);
            current = current.nextElement;
        }

        return subList;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (E element : this) {
            if (Objects.equals(element, o)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        int index = 0;
        for (E element : this) {
            if (Objects.equals(element, o)) {
                lastIndex = index;
            }
            index++;
        }
        return lastIndex;
    }

    @Override
    public void clear() {
        head = null;
    }

    @Override
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

    public E peek() {
        return last().data;
    }

    public E pop() {
        return remove(size - 1);
    }

    public E push(E element) {
        add(element);
        return element;
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new IteratorImpl();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (E element: this) {
            array[index++] = element;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        } else if (a.length > size) {
            a[size] = null;
        }
        int index = 0;
        for (E element : this) {
            a[index++] = (T) element;
        }
        return a;
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

    private static <T> MyLinkedList<T> merge(MyLinkedList<T> left, MyLinkedList<T> right) {
        left.last().nextElement = right.head;
        left.size += right.size;
        return left;
    }

    @Override
    public String toString() {
        return "[" + head.toString() + ']';
    }

    private class IteratorImpl implements java.util.Iterator<E> {
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