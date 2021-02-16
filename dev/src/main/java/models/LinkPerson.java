package models;

import java.util.Date;

public class LinkPerson {

    private String idPersonPrimary;
    private String idPersonSecondary;
    private String date;
    
    public String getIdPersonPrimary() {
        return idPersonPrimary;
    }

    public void setIdPersonPrimary(String idPersonPrimary) {
        this.idPersonPrimary = idPersonPrimary;
    }

    public String getIdPersonSecondary() {
        return idPersonSecondary;
    }

    public void setIdPersonSecondary(String idPersonSecondary) {
        this.idPersonSecondary = idPersonSecondary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
