// uni/com/StoreTest.java (оновлений)
package uni.com;

import uni.com.exceptions.InvalidFieldValueException;
import uni.com.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreTest {

    private Store store;
    private TShirts tshirt1;
    private TShirts tshirt2;

    @BeforeEach
    public void setUp() {
        store = new Store();
        tshirt1 = new TShirts("T1", "white", Size.M, 100.0, "Brand1", "cotton", false, false);
        tshirt2 = new TShirts("T2", "black", Size.L, 150.0, "Brand2", "cotton", true, true);

        store.addNewClothes(tshirt1, 5);
        store.addNewClothes(tshirt2, 3);
    }

    @Test
    public void shouldThrowObjectNotFoundExceptionWhenUpdatingNonExistingObject() {
        TShirts nonExisting = new TShirts("NonExisting", "red", Size.S, 50.0, "BrandX", "poly", false, false);

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            store.update(nonExisting, nonExisting);   // такого об’єкта немає в inventory
        });

        assertEquals("Object not found for update", thrown.getMessage());
    }

    @Test
    public void shouldThrowInvalidFieldValueExceptionWhenUpdatingWithNull() {
        InvalidFieldValueException thrown = assertThrows(InvalidFieldValueException.class, () -> {
            store.update(tshirt1, null);   // updated = null
        });

        assertEquals("Objects cannot be null", thrown.getMessage());
    }

    @Test
    public void shouldThrowObjectNotFoundExceptionWhenDeletingNonExistingObject() {
        TShirts nonExisting = new TShirts("NonExisting", "red", Size.S, 50.0, "BrandX", "poly", false, false);

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            store.delete(nonExisting);   // об’єкт не додавався до store
        });

        assertEquals("Object not found for deletion", thrown.getMessage());
    }

    @Test
    public void shouldThrowInvalidFieldValueExceptionWhenDeletingNull() {
        InvalidFieldValueException thrown = assertThrows(InvalidFieldValueException.class, () -> {
            store.delete(null);
        });

        assertEquals("Object cannot be null", thrown.getMessage());
    }
}