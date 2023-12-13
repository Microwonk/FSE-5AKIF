package net.microwonk.aufg_jdbc.dao_land.domain;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }
}
