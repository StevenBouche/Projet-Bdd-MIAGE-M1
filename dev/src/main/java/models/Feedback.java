package models;

import com.marklogic.client.pojo.annotation.Id;

public class Feedback {


    private String id;
    private String idPerson;
    private String asinProduct;
    private String note;
    private String comment;

    public Feedback(){

    }

    public String getAsinProduct() {
        return asinProduct;
    }

    public void setAsinProduct(String asinProduct) {
        this.asinProduct = asinProduct;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) { this.note = note; }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
