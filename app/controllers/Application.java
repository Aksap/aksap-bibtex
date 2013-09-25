package controllers;

import models.Inproceedings;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import static play.data.Form.form;
import static play.libs.Json.toJson;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready.")
        );
    }

    public static Result createProceedings() {
        return ok(create.render(form(Inproceedings.class))
        );
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


    //Palauttaa URIssa annetun id:n perusteella oikean proceedingsin
    public static Result showProceeding(int id){
        List<Inproceedings> inproceedings = new Model.Finder(Integer.class, Inproceedings.class).all();
        for(Inproceedings i : inproceedings){
            if(i.getId() == id) return ok(toJson(i));
        }
        return ok(toJson(null));
    }


    public static Result getProceedings(){

        //Otetaan kaikki id:stä (muotoa Integer.class) riippuvat Inproceedings luokan jäsenet listaan
        List<Inproceedings> inproceedings = new Model.Finder(Integer.class, Inproceedings.class).all();
        return ok(
                //Palautetaan JSON-datana
                toJson(inproceedings)
        );
    }

    public static Result printProceedings() {
        Form<Inproceedings> form = form(Inproceedings.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(create.render(form));
        } else {
            Inproceedings data = form.get();
            return ok(
                    inproceedings.render(data.getAuthor(), data.getBooktitle(), data.getTitle(), data.getYear())
            );
        }
    }
}

