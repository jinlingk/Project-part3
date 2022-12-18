package edu.nyu.lk.cookzilla.model;

public class Images {
    private Integer imagesid;

    private String avatar;

    public Integer getImagesid() {
        return imagesid;
    }

    public void setImagesid(Integer imagesid) {
        this.imagesid = imagesid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }
}