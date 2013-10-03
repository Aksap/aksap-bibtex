import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumTest {
    WebDriver driver;
    String port;
    String url;
    WebElement e;

    @Before
    public void setUp() {
        driver = new HtmlUnitDriver();
        port = "9090";
        url = "http://localhost:"+port;
        driver.get(url);
    }

    @Test
    public void create(){
        System.out.println(driver.getPageSource());
    }
}
