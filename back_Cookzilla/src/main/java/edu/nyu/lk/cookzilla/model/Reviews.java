package edu.nyu.lk.cookzilla.model;

public class Reviews {
    private Integer reciewid;

    private Integer userid;

    private String title;

    private String content;

    private String rating;

    private Integer recipeid;

    public Integer getReciewid() {
        return reciewid;
    }

    public void setReciewid(Integer reciewid) {
        this.reciewid = reciewid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating == null ? null : rating.trim();
    }

    public Integer getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(Integer recipeid) {
        this.recipeid = recipeid;
    }
}