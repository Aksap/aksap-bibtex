package controllers;


import models.Article;
import models.Book;
import models.Inproceedings;
import models.Misc;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import static play.data.Form.form;
import static play.libs.Json.toJson;
import views.html.*;

@org.springframework.stereotype.Controller
public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready.")
        );
    }

    public static Result createProceedings() {
        return ok(create.render(form(Inproceedings.class))
        );
    }

    //Kaamea tapa parseta, sry, pitää vaihtaa for each field -tyyliseksi.

    public static Result getBibTex(){
        String result = "";
        List<Article> articles = new Model.Finder(String.class, Article.class).all();
        List<Inproceedings> inproceedingses = new Model.Finder(String.class, Inproceedings.class).all();
        List<Book> books = new Model.Finder(String.class, Book.class).all();

        for(Article article : articles){
            result += "@article{"+article.getId()+",\r\n" +
                    "author = {"+article.getAuthor()+"},\r\n"+
                    "title = {"+article.getTitle()+"},\r\n" +
                    "year = {"+article.getYear() +"},\r\n"+
                    "journal = {"+article.getJournal()+"},\r\n}\r\n\r\n";
        }
        for(Inproceedings inproceedings : inproceedingses){
            result += "@inproceedings{"+inproceedings.getId()+",\r\n" +
                    "author = {"+inproceedings.getAuthor()+"},\r\n"+
                    "title = {"+inproceedings.getTitle()+"},\r\n" +
                    "booktitle = {"+inproceedings.getBooktitle()+"},\r\n"+
                    "year = {"+inproceedings.getYear() +"},\r\n}\r\n\r\n";
        }

        for(Book book : books){
            result += "@book{"+book.getId()+",\r\n" +
                    "author = {"+book.getAuthor()+"},\r\n"+
                    "title = {"+book.getTitle()+"},\r\n" +
                    "year = {"+book.getYear() +"},\r\n"+
                    "publisher = {"+book.getPublisher()+"},\r\n}\r\n\r\n";
        }
        return ok(result);
    }

    public static Result getArticleTex(String id){
        Article article = null;
        List<Article> articles = new Model.Finder(String.class, Article.class).all();
        for(Article a : articles){
            if(a.getId().equals(id)) article = a;
        }
        String result = "@article{"+article.getId()+",\r\n" +
                "author = {"+article.getAuthor()+"},\r\n"+
                "title = {"+article.getTitle()+"},\r\n" +
                "year = {"+article.getYear() +"},\r\n"+
                "journal = {"+article.getJournal()+"},\r\n}";
        return ok(result);
    }

    public static Result getProceedingsTex(String id){
        Inproceedings inproceedings = null;
        List<Inproceedings> inproceedingses = new Model.Finder(String.class, Inproceedings.class).all();
        for(Inproceedings i : inproceedingses){
            if(i.getId().equals(id)) inproceedings = i;
        }
        String result = "@inproceedings{"+inproceedings.getId()+",\r\n" +
                "author = {"+inproceedings.getAuthor()+"},\r\n"+
                "title = {"+inproceedings.getTitle()+"},\r\n" +
                "booktitle = {"+inproceedings.getBooktitle()+"},\r\n"+
                "year = {"+inproceedings.getYear() +"},\r\n}";

        return ok(result);
    }

    public static Result getBookTex(String id){
        Book book = null;
      List<Book> books = new Model.Finder(String.class, Book.class).all();
        for(Book b : books){
            if(b.getId().equals(id)) book = b;
        }
       String result = "@book{"+book.getId()+",\r\n" +
               "author = {"+book.getAuthor()+"},\r\n"+
               "title = {"+book.getTitle()+"},\r\n" +
               "year = {"+book.getYear() +"},\r\n"+
               "publisher = {"+book.getPublisher()+"},\r\n}";
        return ok(result);
    }

    public static Result addProceedings(){
        create.render(form(Inproceedings.class));

        //Luodaan POST requestin rungosta Inproceedings-luokan toteuttava olio
        Inproceedings inproceeding = Form.form(Inproceedings.class).bindFromRequest().get();

        //Tallennetaan tietokantaan
        inproceeding.save();

        //Ohjataan takaisin lomakkeeseen
        return redirect(controllers.routes.Application.createProceedings());
    }

    public static Result addMisc(){
        Misc m = Form.form(Misc.class).bindFromRequest().get();
        m.save();
        return redirect(controllers.routes.Application.createProceedings());
    }

    public static Result addBook(){
        Book b = Form.form(Book.class).bindFromRequest().get();
        b.save();
        return redirect(controllers.routes.Application.createProceedings());
    }

    public static Result addArticle(){
        Article a = Form.form(Article.class).bindFromRequest().get();
        a.save();
        return redirect(controllers.routes.Application.createProceedings());
    }

    //Palauttaa URIssa annetun id:n perusteella oikean proceedingsin
    public static Result showProceeding(String id){
        List<Inproceedings> inproceedings = new Model.Finder(String.class, Inproceedings.class).all();
        for(Inproceedings i : inproceedings){
            if(i.getId().equals(id)) return ok(toJson(i));
        }
        return ok(toJson(null));
    }


    public static Result showArticle(String id){
        List<Article> articles = new Model.Finder(String.class, Article.class).all();
        for(Article a : articles){
            if(a.getId().equals(id)) return ok(toJson(a));
        }
        return ok(toJson(null));
    }

    public static Result showBook(String id){
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        for(Book b : books){
            if(b.getId().equals(id)) return ok(toJson(b));
        }
        return ok(toJson(null));
    }



    public static Result getProceedings(){

        //Otetaan kaikki id:stä (muotoa Integer.class) riippuvat Inproceedings luokan jäsenet listaan
        List<Inproceedings> inproceedings = new Model.Finder(String.class, Inproceedings.class).all();
        return ok(
                //Palautetaan JSON-datana
                toJson(inproceedings)
        );
    }

    public static Result getArticles(){

        //Otetaan kaikki id:stä (muotoa Integer.class) riippuvat Inproceedings luokan jäsenet listaan
        List<Inproceedings> articles = new Model.Finder(String.class, Article.class).all();
        return ok(
                //Palautetaan JSON-datana
                toJson(articles)
        );
    }

    public static Result getBooks(){

        //Otetaan kaikki id:stä (muotoa Integer.class) riippuvat Inproceedings luokan jäsenet listaan
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        return ok(
                //Palautetaan JSON-datana
                toJson(books)
        );
    }

    public static Result printProceedings() {
        Form<Inproceedings> form = form(Inproceedings.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(create.render(form));
        } else {
            Inproceedings data = form.get();
            return ok(inproceedings.render(data.getAuthor(), data.getBooktitle(), data.getTitle(), data.getYear()));
        }
    }
}

