package persistance;

import model.Statue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatueTest {
    private Statue gy;
    private Statue buddha;

    @BeforeEach
    public void setUp() {
        gy = new Statue("Guan Yin");
        buddha = new Statue("Gautama Buddha");
    }

    @Test
    public void constructorTest() {
        assertEquals("Guan Yin", gy.getName());
        assertEquals(0, gy.getAmountFunded());
        assertEquals("Gautama Buddha", buddha.getName());
        assertEquals(0, buddha.getAmountFunded());
    }

    @Test
    public void donateTest() {
        assertEquals(5000, gy.donate(5000));
        assertEquals(11000,gy.donate(6000));
        assertEquals(6000,buddha.donate(6000));
        assertEquals(12000,buddha.donate(6000));
    }

    void checkStatue(String name, Statue statue) {
        assertEquals(name, statue.getName());
    }
}
