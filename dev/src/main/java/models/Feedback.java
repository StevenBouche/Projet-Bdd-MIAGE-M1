package models;

public class Feedback {

    private String idPerson;
    private String asinProduct;
    private float note;
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

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
