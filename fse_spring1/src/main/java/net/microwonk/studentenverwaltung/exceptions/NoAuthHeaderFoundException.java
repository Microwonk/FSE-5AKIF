package net.microwonk.studentenverwaltung.exceptions;

public class NoAuthHeaderFoundException extends RuntimeException{
    public NoAuthHeaderFoundException()
    {
        super("No Auth-Header with Bearer-Token found!");
    }
}
