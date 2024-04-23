package at.kolleg.erplite.ordermanagement.domain.valueobjects;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ValueObjectMarker
public record Email(String email) {
    public Email {
        if (!isValid(email)) throw new IllegalArgumentException("Email not valid!");
    }

    public static boolean isValid(String email) {
        if (email != null) {
            Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            return matcher.find();
        } else {
            return false;
        }
    }
}
