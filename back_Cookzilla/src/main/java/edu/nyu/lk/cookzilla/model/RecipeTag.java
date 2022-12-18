package edu.nyu.lk.cookzilla.model;

public class RecipeTag {
    private Integer recipetagid;

    private Integer tagid;

    private Integer recipeid;

    public Integer getRecipetagid() {
        return recipetagid;
    }

    public void setRecipetagid(Integer recipetagid) {
        this.recipetagid = recipetagid;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public Integer getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(Integer recipeid) {
        this.recipeid = recipeid;
    }
}