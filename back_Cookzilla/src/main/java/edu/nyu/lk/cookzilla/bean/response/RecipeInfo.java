package edu.nyu.lk.cookzilla.bean.response;

public class RecipeInfo {

    private Integer id;

    private String title;

    private Float serving;

    private String images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getServing() {
        return serving;
    }

    public void setServing(Float serving) {
        this.serving = serving;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
