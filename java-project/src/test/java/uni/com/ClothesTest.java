package uni.com;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Clothes class validation.
 */
public class ClothesTest {
    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes c = new Clothes("T-shirt", "white", "M",  22.50, "Nike", "cotton");
        assertThrows(IllegalArgumentException.class, () -> {
            c.setPrice(-10);
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("", "white", "M",  0, "Nike", "cotton");
        });
    }
}
