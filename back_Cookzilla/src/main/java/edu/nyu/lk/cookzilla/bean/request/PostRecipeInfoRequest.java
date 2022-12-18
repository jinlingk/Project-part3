package edu.nyu.lk.cookzilla.bean.request;

public class PostRecipeInfoRequest {
    private String title;

    private String serveing;

    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServeing() {
        return serveing;
    }

    public void setServeing(String serveing) {
        this.serveing = serveing;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
