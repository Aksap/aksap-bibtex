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
public class ArticleTest {
    WebDriver driver;
    int port;
    String url;
    WebElement e;

    //VALUES
    String AUTHOR   = "Yrjö Meikäläinen";
    String ID       = "ID1337";
    String TITLE    = "Tukiainen onkin reptiliaani";
    String JOURNAL  = "7-päivää";
    String YEAR     = "2013";
    
    String FORMAL_BIBTEX_PRESENTATION = c("" +
            "@article{"+ID+",\r\n" +
            "author = {"+AUTHOR+"},\r\n" +
            "title = {"+TITLE+"},\r\n" +
            "year = {"+YEAR+"},\r\n" +
            "journal = {"+JOURNAL+"},\r\n" +
            "}");

    //Convert scands
    public String c(String s){
        return s.replace("ä", "\\\"{a}").replace("ö", "\\\"{o}");
    }


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

                //given "a form for adding articles"
                driver.get(url + "/proceedings/create");
                //

                //when "valid values are submitted"
                driver.findElement(By.xpath("//input[@t_name='Article-Id']")).sendKeys(ID);
                driver.findElement(By.xpath("//input[@t_name='Article-Author']")).sendKeys(AUTHOR);
                driver.findElement(By.xpath("//input[@t_name='Article-Title']")).sendKeys(TITLE);
                driver.findElement(By.xpath("//input[@t_name='Article-Journal']")).sendKeys(JOURNAL);
                driver.findElement(By.xpath("//input[@t_name='Article-Year']")).sendKeys(YEAR);

                driver.findElement(By.id("add_article")).click();
                //

                //then "an article should be created with correct values"
                driver.get(url + "/articles/show/"+ID);
                String source = driver.getPageSource();

                assertTrue("Id missing", source.contains(ID));
                assertTrue("Author missing", source.contains(AUTHOR));
                assertTrue("Title missing", source.contains(TITLE));
                assertTrue("Journal missing", source.contains(JOURNAL));
                assertTrue("Year missing", source.contains(YEAR));
                //
            }
        });
    }

    @Test
    public void canGetArticleBibtex() throws Exception {
        running(testServer(port), new Runnable() {
            public void run() {

                //given "a form for adding articles"
                driver.get(url + "/proceedings/create");
                //

                //when "valid values are submitted"
                driver.findElement(By.xpath("//input[@t_name='Article-Id']")).sendKeys(ID);
                driver.findElement(By.xpath("//input[@t_name='Article-Author']")).sendKeys(AUTHOR);
                driver.findElement(By.xpath("//input[@t_name='Article-Title']")).sendKeys(TITLE);
                driver.findElement(By.xpath("//input[@t_name='Article-Journal']")).sendKeys(JOURNAL);
                driver.findElement(By.xpath("//input[@t_name='Article-Year']")).sendKeys(YEAR);

                driver.findElement(By.id("add_article")).click();
                //

                //then "a valid bibtex-presentation should be created and available"
                driver.get(url + "/articles/bib/"+ID+".bib");

                String source = driver.getPageSource();
                assertTrue("Id missing", source.contains(c(ID)));
                assertTrue("Author missing", source.contains(c(AUTHOR)));
                assertTrue("Title missing", source.contains(c(TITLE)));
                assertTrue("Journal missing", source.contains(c(JOURNAL)));
                assertTrue("Year missing", source.contains(c(YEAR)));
                assertTrue("Scands are not rendered correctly", source.contains("\\\"{a}")
                                                             && source.contains("\\\"{o}"));
                assertTrue("Bibtex is informal", source.contains(FORMAL_BIBTEX_PRESENTATION));
                //
            }
        });
    }

    @Test
    public void canDeleteArticle() throws Exception {
        running(testServer(port), new Runnable() {
            public void run() {

                //given "an added article with delete option"
                driver.get(url + "/proceedings/create");
                driver.findElement(By.xpath("//input[@t_name='Article-Id']")).sendKeys(ID);
                driver.findElement(By.xpath("//input[@t_name='Article-Author']")).sendKeys(AUTHOR);
                driver.findElement(By.xpath("//input[@t_name='Article-Title']")).sendKeys(TITLE);
                driver.findElement(By.xpath("//input[@t_name='Article-Journal']")).sendKeys(JOURNAL);
                driver.findElement(By.xpath("//input[@t_name='Article-Year']")).sendKeys(YEAR);
                driver.findElement(By.id("add_article")).click();
                //

                //when "delete functionality is used"
                driver.get(url + "/articles/delete/"+ID);
                //

                //then "reference should no longer exist"
                driver.get(url + "/articles/show/"+ID);
                String source = driver.getPageSource();
                assertTrue("Article prompted for delete still exists", !source.contains(ID));
                //
            }
        });
    }
}
