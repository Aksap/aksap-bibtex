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

public class AddingBookTest {
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
    public void canAddBook() throws Exception {
        running(testServer(port), new Runnable() {
            public void run() {
                driver.get(url + "/proceedings/create");

                String id = "asdf667";
                driver.findElement(By.xpath("//input[@t_name='Book-Id']")).sendKeys(id);
                driver.findElement(By.xpath("//input[@t_name='Book-Author']")).sendKeys("Matti Meikäläinen");
                driver.findElement(By.xpath("//input[@t_name='Book-Title']")).sendKeys("Raamattu");
                driver.findElement(By.xpath("//input[@t_name='Book-Publisher']")).sendKeys("Otava");
                driver.findElement(By.xpath("//input[@t_name='Book-Year']")).sendKeys("100");

                driver.findElement(By.id("add_book")).click();


                driver.get(url + "/books/show/"+id);
                String source = driver.getPageSource();
                System.out.println(source);
                assertTrue("Id missing", source.contains(id));
                assertTrue("Author missing", source.contains("Matti Meikäläinen"));
                assertTrue("Title missing", source.contains("Raamattu"));
                assertTrue("Publisher missing", source.contains("Otava"));
                assertTrue("Year missing", source.contains("100"));
            }
        });
    }
}
