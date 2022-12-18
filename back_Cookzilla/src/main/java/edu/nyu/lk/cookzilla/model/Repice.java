package edu.nyu.lk.cookzilla.model;

public class Repice {
    private Integer recipeid;

    private String title;

    private String serving;

    private String image;

    public Integer getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(Integer recipeid) {
        this.recipeid = recipeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving == null ? null : serving.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }
}