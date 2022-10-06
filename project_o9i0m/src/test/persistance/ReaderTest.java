package persistance;

import model.Pagoda;
import model.Statue;
import org.junit.jupiter.api.Test;
import persistence.Reader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest extends StatueTest{

    @Test
    public void testReaderNonExistentFile() {
        Reader rd = new Reader("./data/noFile.json");
        try {
            Pagoda pagoda = rd.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPagoda() {
        Reader reader = new Reader("./data/testReaderEmptyPagoda.json");
        try {
            Pagoda pd = reader.read();
            assertEquals("Shaolin", pd.getName());
            assertEquals(0, pd.getNumStatues());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPagoda() {
        Reader reader = new Reader("./data/testReaderGeneralWorkRoom.json");
        try {
            Pagoda pd = reader.read();
            assertEquals("Shaolin", pd.getName());
            List<Statue> statues = pd.getStatues();
            assertEquals(2, statues.size());
            checkStatue("Guanyin", statues.get(0));
            checkStatue("Buddha", statues.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
