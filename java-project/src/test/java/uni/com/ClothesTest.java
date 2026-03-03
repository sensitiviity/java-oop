package uni.com;

import uni.com.Clothes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Clothes class.
 * Tests validation logic of constructor and setters.
 */
public class ClothesTest {
    /**
     * Verifies that setter throws exception when invalid price is provided.
     */
    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes c = new Clothes("T-shirt", "white", "M",  22.50, "Nike", "cotton");
        assertThrows(IllegalArgumentException.class, () -> {
            c.setPrice(-10);
        });
    }

    /**
     * Verifies that constructor throws exception when invalid parameters are passed.
     */
    @Test
    void shouldThrowExceptionWhenInvalidConstructorData(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("", "white", "M",  0, "Nike", "cotton");
        });
    }
}