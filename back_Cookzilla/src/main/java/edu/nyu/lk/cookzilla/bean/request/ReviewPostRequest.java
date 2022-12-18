package edu.nyu.lk.cookzilla.bean.request;

public class ReviewPostRequest {
    private Integer reciewid;

    private Integer userid;

    private String title;

    private String content;

    private Integer rating;

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
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(Integer recipeid) {
        this.recipeid = recipeid;
    }
}
