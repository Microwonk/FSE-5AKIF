package net.microwonk;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.MountableFile;

public class Main {
    public static void main(String... args) {
        var rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management-alpine")
                .withExposedPorts(5672, 15672)
                .withReuse(true); // Enable reuse for RabbitMQ container
        rabbitMQContainer.start();

        var mariadbContainer = new GenericContainer<>("mariadb:10.3")
                .withEnv("MYSQL_ROOT_PASSWORD", "notSecureChangeMe")
                .withEnv("MYSQL_ROOT_HOST", "%")
                .withExposedPorts(3306)
                .withCopyFileToContainer(MountableFile.forClasspathResource("init/"), "/docker-entrypoint-initdb.d")
                .withReuse(true); // Enable reuse for MariaDB container
        mariadbContainer.start();

        var adminerContainer = new GenericContainer<>("adminer:4.8.0")
                .withExposedPorts(8080)
                .withReuse(true); // Enable reuse for Adminer container
        adminerContainer.start();

        var phpMyAdminContainer = new GenericContainer<>("phpmyadmin")
                .withExposedPorts(80)
                .withEnv("PMA_ARBITRARY", "1")
                .withReuse(true); // Enable reuse for PhpMyAdmin container
        phpMyAdminContainer.start();

        // Stop containers when the JVM exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            rabbitMQContainer.stop();
            mariadbContainer.stop();
            adminerContainer.stop();
            phpMyAdminContainer.stop();
        }));

        // Loop indefinitely to keep containers running
        try {
            while (true) {
                Thread.sleep(1000); // Sleep for 1 second
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
