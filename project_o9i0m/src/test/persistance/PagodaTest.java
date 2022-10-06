package persistance;

import model.Pagoda;
import model.Statue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagodaTest {
    private Pagoda shaolin;
    private Statue guanyin;
    private Statue buddha;


    @BeforeEach
    void setUp() {
        shaolin = new Pagoda("Shaolin");
        guanyin = new Statue("Guan Yin");
        buddha = new Statue("Gautama Buddha");

    }

    @Test
    void constructorTest() {
        assertEquals("Shaolin",shaolin.getName());
    }

    @Test
    void getStatuesTest() {
        assertEquals(0, shaolin.getStatues().size());
        shaolin.addStatue(guanyin);
        assertEquals(1, shaolin.getStatues().size());
        assertEquals(guanyin, shaolin.getStatues().get(0));
        shaolin.addStatue(buddha);
        assertEquals(2, shaolin.getStatues().size());
        assertEquals(buddha, shaolin.getStatues().get(1));
    }

    @Test
    void checkMoneyTest() {
        shaolin.addStatue(guanyin);
        shaolin.addStatue(buddha);
        guanyin.donate(500);
        assertEquals("Guan Yin statue has been funded 500$",shaolin.checkMoney(500));
        assertEquals("No statue has been funded 600$", shaolin.checkMoney(600));
    }

    @Test
    void getListings() {
        shaolin.addStatue(guanyin);
        guanyin.donate(500);
        shaolin.addStatue(buddha);
        buddha.donate(100);
        assertEquals("Statue #0: Guan Yin" +
                "\n" + "You have donated: $ 500 to Guan Yin statue" + "\n" + "\n" +
                "Statue #1: Gautama Buddha" +
                "\n" + "You have donated: $ 100 to Gautama Buddha statue" + "\n" + "\n",shaolin.getListings());

    }

}
