package PubSub;

import java.lang.reflect.Method;
import java.util.Objects;

@FunctionalInterface
public interface Subscriber<T> {
    void receiveMessage(T message);
}
