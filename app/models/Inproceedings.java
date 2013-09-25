package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inproceedings extends Model{

    @Id
    public int id;
    @Required
    public String author;
    @Required
    public String title;
    @Required
    public String booktitle;
    @Required
    public int year;

//    public Inproceedings(String author, String title, String booktitle, int year) {
//        this.author = author;
//        this.title = title;
//        this.booktitle = booktitle;
//        this.year = year;
//    }

    public int getId(){
       return id;
    }

    public void setId(int id){
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