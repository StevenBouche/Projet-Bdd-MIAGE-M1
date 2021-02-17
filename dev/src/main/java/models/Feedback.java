package models;

public class Feedback {

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
}
