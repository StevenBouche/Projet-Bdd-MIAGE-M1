package models;

public class Feedback {

    private String idPerson;
    private String asinProduct;
    private String content;

    public String getAsinProduct() {
        return asinProduct;
    }

    public void setAsinProduct(String asinProduct) {
        this.asinProduct = asinProduct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }
}
