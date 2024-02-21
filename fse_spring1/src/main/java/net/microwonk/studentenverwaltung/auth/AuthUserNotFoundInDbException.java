package net.microwonk.studentenverwaltung.auth;

public class AuthUserNotFoundInDbException extends RuntimeException{
    public AuthUserNotFoundInDbException()
    {
        super("User of Token not found in DB!");
    }
}
