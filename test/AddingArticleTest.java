import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Created with IntelliJ IDEA.
 * User: Jonatan
 * Date: 10.10.2013
 * Time: 13:56
 * To change this template use File | Settings | File Templates.
 */
public class AddingArticleTest {
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
    public void canAddArticle() throws Exception {
        running(testServer(port), new Runnable() {
            public void run() {
                driver.get(url + "/proceedings/create");

                String id = "asdf668";
                driver.findElement(By.xpath("//input[@t_name='Article-Id']")).sendKeys(id);
                driver.findElement(By.xpath("//input[@t_name='Article-Author']")).sendKeys("Matti Meikäläinen");
                driver.findElement(By.xpath("//input[@t_name='Article-Title']")).sendKeys("Tukiainen onkin reptiliaani");
                driver.findElement(By.xpath("//input[@t_name='Article-Journal']")).sendKeys("7-päivää");
                driver.findElement(By.xpath("//input[@t_name='Article-Year']")).sendKeys("2013");

                driver.findElement(By.id("add_article")).click();


                driver.get(url + "/articles/show/"+id);
                String source = driver.getPageSource();
                System.out.println(source);
                assertTrue("Id missing", source.contains(id));
                assertTrue("Author missing", source.contains("Matti Meikäläinen"));
                assertTrue("Title missing", source.contains("Tukiainen onkin reptiliaani"));
                assertTrue("Journal missing", source.contains("7-päivää"));
                assertTrue("Year missing", source.contains("2013"));
            }
        });
    }
}
