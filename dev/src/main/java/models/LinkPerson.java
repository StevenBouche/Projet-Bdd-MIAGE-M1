package models;

import com.marklogic.client.pojo.annotation.Id;

public class LinkPerson {

    private String id;
    private String idPersonPrimary;
    private String idPersonSecondary;
    private long date;
    private String dateStr;

    public LinkPerson(){

    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

}
