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

public class InproceedingsTest {
    WebDriver driver;
    int port;
    String url;
    WebElement e;

    //VALUES
    String AUTHOR   = "Yrjö Meikäläinen";
    String ID       = "ID42";
    String TITLE    = "A Methodology for the Synthesis of Expert Systems";
    String BOOKTITLE  = "Proceedings of INFOCOM";
    String YEAR     = "2013";

    String FORMAL_BIBTEX_PRESENTATION = c("" +
            "@inproceedings{"+ID+",\r\n" +
            "author = {"+AUTHOR+"},\r\n" +
            "title = {"+TITLE+"},\r\n" +
            "booktitle = {"+BOOKTITLE+"},\r\n" +
            "year = {"+YEAR+"},\r\n" +
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
    public void canAddInproceedings() throws Exception {
        running(testServer(port), new Runnable() {
            public void run() {

                //given "a form for adding inproceedings"
                driver.get(url + "/proceedings/create");
                //

                //when "valid values are submitted"
                driver.findElement(By.name("id")).sendKeys(ID);
                driver.findElement(By.name("author")).sendKeys(AUTHOR);
                driver.findElement(By.name("title")).sendKeys(TITLE);
                driver.findElement(By.name("booktitle")).sendKeys(BOOKTITLE);
                driver.findElement(By.name("year")).sendKeys(YEAR);

                driver.findElement(By.cssSelector("[type=submit]")).click();
                //

                //then "an inproceedings should be created with correct values"
                driver.get(url + "/proceedings/show/" + ID);
                String source = driver.getPageSource();

                assertTrue("Id missing", source.contains(ID));
                assertTrue("Author missing", source.contains(AUTHOR));
                assertTrue("Title missing", source.contains(TITLE));
                assertTrue("Booktitle missing", source.contains(BOOKTITLE));
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
                driver.findElement(By.name("id")).sendKeys(ID);
                driver.findElement(By.name("author")).sendKeys(AUTHOR);
                driver.findElement(By.name("title")).sendKeys(TITLE);
                driver.findElement(By.name("booktitle")).sendKeys(BOOKTITLE);
                driver.findElement(By.name("year")).sendKeys(YEAR);

                driver.findElement(By.cssSelector("[type=submit]")).click();
                //

                //then "a valid bibtex-presentation should be created and available"
                driver.get(url + "/inproceedings/bib/" + ID + ".bib");
                String source = driver.getPageSource();
                System.out.println(FORMAL_BIBTEX_PRESENTATION);
                System.out.println(source);

                assertTrue("Id missing", source.contains(c(ID)));
                assertTrue("Author missing", source.contains(c(AUTHOR)));
                assertTrue("Title missing", source.contains(c(TITLE)));
                assertTrue("Publisher missing", source.contains(c(BOOKTITLE)));
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

                //given "an added inproceedings with delete option"
                driver.get(url + "/proceedings/create");

                driver.findElement(By.name("id")).sendKeys(ID);
                driver.findElement(By.name("author")).sendKeys(AUTHOR);
                driver.findElement(By.name("title")).sendKeys(TITLE);
                driver.findElement(By.name("booktitle")).sendKeys(BOOKTITLE);
                driver.findElement(By.name("year")).sendKeys(YEAR);

                driver.findElement(By.cssSelector("[type=submit]")).click();
                //

                //when "delete functionality is used"
                driver.get(url + "/proceedings/delete/"+ID);
                //

                //then "reference should no longer exist"
                driver.get(url + "/proceedings/show/"+ID);
                String source = driver.getPageSource();
                assertTrue("Inproceedings prompted for delete still exists", !source.contains(ID));
                //
            }
        });
    }
}
