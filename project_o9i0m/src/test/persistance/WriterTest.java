package persistance;

import model.Pagoda;
import model.Statue;
import org.junit.jupiter.api.Test;
import persistence.Reader;
import persistence.Writer;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest extends StatueTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Pagoda pagoda = new Pagoda("Shaolin");
            Writer writer = new Writer("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Pagoda pagoda = new Pagoda("Shaolin");
            Writer writer = new Writer("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(pagoda);
            writer.close();

            Reader reader = new Reader("./data/testWriterEmptyWorkroom.json");
            pagoda = reader.read();
            assertEquals("Shaolin", pagoda.getName());
            assertEquals(0, pagoda.getNumStatues());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Pagoda pagoda = new Pagoda("Shaolin");
            pagoda.addStatue(new Statue("Guanyin"));
            pagoda.addStatue(new Statue("Buddha"));
            Writer writer = new Writer("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(pagoda);
            writer.close();

            Reader reader = new Reader("./data/testWriterGeneralWorkroom.json");
            pagoda = reader.read();
            assertEquals("Shaolin", pagoda.getName());
            List<Statue> statues = pagoda.getStatues();
            assertEquals(2, statues.size());
            checkStatue("Guanyin", statues.get(0));
            checkStatue("Buddha", statues.get(1));
        } catch (IOException e)  {
            fail("Exception should not have been thrown");
        }
    }
}

