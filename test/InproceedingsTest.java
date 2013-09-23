import models.Inproceedings;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class InproceedingsTest {

    @Test
    public void testInproceedings1() throws Exception {

        Inproceedings inproceedings = new Inproceedings("Arto Vihavainen", "Kukkien kastelu ja web-kehitys", "Vastaus kaikkiin maailman kysymyksiin", 2011);

        assertEquals("Arto Vihavainen", inproceedings.getAuthor());
        assertEquals("Kukkien kastelu ja web-kehitys", inproceedings.getTitle());
        assertEquals("Vastaus kaikkiin maailman kysymyksiin", inproceedings.getBooktitle());
        assertEquals(2011, inproceedings.getYear());

    }
}