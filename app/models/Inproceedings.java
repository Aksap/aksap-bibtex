package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;

import play.data.validation.Validation;

@Entity
public class Inproceedings extends Model{

    @Required
    @Id
    public String id;
    @Required
    public String author;
    @Required
    public String title;
    @Required
    public String booktitle;
    @Required
    //@Max(2013)
    public int year;

    public String getId(){
       return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}