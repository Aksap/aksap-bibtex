package controllers;

import models.Inproceedings;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import static play.data.Form.form;

public class Application extends Controller {

    public static Result index() {
        return ok(
                index.render("Your new application is ready.")
        );
    }

    public static Result createProceedings() {
        return ok(
                create.render(form(Inproceedings.class))
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
