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

public class BookTest {
    WebDriver driver;
    int port;
    String url;
    WebElement e;

    //VALUES
    String AUTHOR   = "Yrjö Meikäläinen";
    String ID       = "ID666";
    String TITLE    = "Raamattu";
    String PUBLISHER = "Otava";
    String YEAR     = "100";

    String FORMAL_BIBTEX_PRESENTATION = c("" +
            "@book{"+ID+",\r\n" +
            "author = {"+AUTHOR+"},\r\n" +
            "title = {"+TITLE+"},\r\n" +
            "year = {"+YEAR+"},\r\n" +
            "publisher = {"+PUBLISHER+"},\r\n" +
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
    public void canAddBook() throws Exception {
        running(testServer(port), new Runnable() {
            public void run() {

                //given "a form for adding books"
                driver.get(url + "/proceedings/create");
                //

                //when "valid values are submitted"
                driver.findElement(By.xpath("//input[@t_name='Book-Id']")).sendKeys(ID);
                driver.findElement(By.xpath("//input[@t_name='Book-Author']")).sendKeys(AUTHOR);
                driver.findElement(By.xpath("//input[@t_name='Book-Title']")).sendKeys(TITLE);
                driver.findElement(By.xpath("//input[@t_name='Book-Publisher']")).sendKeys(PUBLISHER);
                driver.findElement(By.xpath("//input[@t_name='Book-Year']")).sendKeys(YEAR);

                driver.findElement(By.id("add_book")).click();
                //

                //then "a book should be created with correct values"
                driver.get(url + "/books/show/"+ID);
                String source = driver.getPageSource();
                System.out.println(source);
                assertTrue("Id missing", source.contains(ID));
                assertTrue("Author missing", source.contains(AUTHOR));
                assertTrue("Title missing", source.contains(TITLE));
                assertTrue("Publisher missing", source.contains(PUBLISHER));
                assertTrue("Year missing", source.contains(YEAR));
                //
            }
        });
    }

    @Test
    public void canGetBookBibtex() throws Exception {
        running(testServer(port), new Runnable() {
            public void run() {

                //given "a form for adding books"
                driver.get(url + "/proceedings/create");
                //

                //when "valid values are submitted"
                driver.findElement(By.xpath("//input[@t_name='Book-Id']")).sendKeys(ID);
                driver.findElement(By.xpath("//input[@t_name='Book-Author']")).sendKeys(AUTHOR);
                driver.findElement(By.xpath("//input[@t_name='Book-Title']")).sendKeys(TITLE);
                driver.findElement(By.xpath("//input[@t_name='Book-Publisher']")).sendKeys(PUBLISHER);
                driver.findElement(By.xpath("//input[@t_name='Book-Year']")).sendKeys(YEAR);

                driver.findElement(By.id("add_book")).click();
                //

                //then "a valid bibtex-presentation should be created and available"
                driver.get(url + "/books/bib/" + ID + ".bib");
                String source = driver.getPageSource();
                System.out.println(FORMAL_BIBTEX_PRESENTATION);
                System.out.println(source);

                assertTrue("Id missing", source.contains(c(ID)));
                assertTrue("Author missing", source.contains(c(AUTHOR)));
                assertTrue("Title missing", source.contains(c(TITLE)));
                assertTrue("Publisher missing", source.contains(c(PUBLISHER)));
                assertTrue("Year missing", source.contains(c(YEAR)));
                assertTrue("Scands are not rendered correctly", source.contains("\\\"{a}")
                        && source.contains("\\\"{o}"));
                assertTrue("Bibtex is informal", source.contains(FORMAL_BIBTEX_PRESENTATION));
                //
            }
        });

    }

    @Test
    public void canDeleteBook() throws Exception {
        running(testServer(port), new Runnable() {
            public void run() {

                //given "an added book with delete option"
                driver.get(url + "/proceedings/create");
                driver.findElement(By.xpath("//input[@t_name='Book-Id']")).sendKeys(ID);
                driver.findElement(By.xpath("//input[@t_name='Book-Author']")).sendKeys(AUTHOR);
                driver.findElement(By.xpath("//input[@t_name='Book-Title']")).sendKeys(TITLE);
                driver.findElement(By.xpath("//input[@t_name='Book-Publisher']")).sendKeys(PUBLISHER);
                driver.findElement(By.xpath("//input[@t_name='Book-Year']")).sendKeys(YEAR);

                driver.findElement(By.id("add_book")).click();
                //

                //when "delete functionality is used"
                driver.get(url + "/books/delete/"+ID);
                //

                //then "reference should no longer exist"
                driver.get(url + "/books/show/"+ID);
                String source = driver.getPageSource();
                assertTrue("Book prompted for delete still exists", !source.contains(ID));
                //
            }
        });
    }
}
