package PubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        var sub1 = new TestSubscriber();
        var sub2 = new TestInlineSubscriber();

        var pub = new TestPublisher();

        pub.subscribe(sub1);
        pub.subscribe(sub2.subToInt);
        pub.subscribe(sub2.subToStr);

        pub.publish(1);
        pub.publish(4);
        pub.publish("String");
    }

    public static class TestSubscriber implements Subscriber<Integer>{
        @Override
        public void receiveMessage(Integer message) {
            System.out.println(this.hashCode() + " " + message);
        }
    }

    public static class TestInlineSubscriber {
        protected final Subscriber<Integer> subToInt = new Subscriber<Integer>() {
            @Override
            public void receiveMessage(Integer message) {
                System.out.println(this.hashCode() + " " + message);
            }
        };

        protected final Subscriber<String> subToStr = new Subscriber<String>() {
            @Override
            public void receiveMessage(String message) {
                System.out.println(this.hashCode() + " " + message);
            }
        };
    }

    public static class TestPublisher extends Publisher { }
}
