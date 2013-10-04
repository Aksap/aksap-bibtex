import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.running;
import static play.test.Helpers.start;
import static play.test.Helpers.testServer;

public class AddingInproceedingsTest {
    WebDriver driver;
    int port;
    String url;
    WebElement e;

    @Before
    public void setUp() {
        driver = new HtmlUnitDriver();
        port = 3030;
        url = "http://localhost:" + port;
    }

    @Test
    public void canAddInproceedings() throws Exception {
        running(testServer(port), new Runnable() {
            public void run() {
                driver.get(url + "/proceedings/create");

                String id = "asdf666";
                driver.findElement(By.name("id")).sendKeys(id);
                driver.findElement(By.name("author")).sendKeys("Matti Meikäläinen");
                driver.findElement(By.name("title")).sendKeys("A Methodology for the Synthesis of Expert Systems");
                driver.findElement(By.name("booktitle")).sendKeys("Proceedings of INFOCOM");
                driver.findElement(By.name("year")).sendKeys("1998");

                driver.findElement(By.cssSelector("[type=submit]")).click();

                driver.get(url + "/proceedings/show/" + id);
                String source = driver.getPageSource();

                assertTrue("Id missing", source.contains(id));
                assertTrue("Author missing", source.contains("Matti Meikäläinen"));
                assertTrue("Title missing", source.contains("A Methodology for the Synthesis of Expert Systems"));
                assertTrue("Booktitle missing", source.contains("Proceedings of INFOCOM"));
                assertTrue("Year missing", source.contains("1998"));
            }
        });
    }
}
