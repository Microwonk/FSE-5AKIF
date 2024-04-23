package at.kolleg.erplite.ordermanagement.domain.valueobjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValueObjectTests {

    @Test
    public void testCreation() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Email("asdf"));
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, () -> new Email("asdf"));
        Assertions.assertEquals("Email not valid!", throwable.getMessage());
    }

}
