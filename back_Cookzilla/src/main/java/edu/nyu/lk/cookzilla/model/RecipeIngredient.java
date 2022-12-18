package edu.nyu.lk.cookzilla.model;

public class RecipeIngredient {
    private Integer recipeingredientid;

    private Integer recipeid;

    private Integer ingredientid;

    public Integer getRecipeingredientid() {
        return recipeingredientid;
    }

    public void setRecipeingredientid(Integer recipeingredientid) {
        this.recipeingredientid = recipeingredientid;
    }

    public Integer getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(Integer recipeid) {
        this.recipeid = recipeid;
    }

    public Integer getIngredientid() {
        return ingredientid;
    }

    public void setIngredientid(Integer ingredientid) {
        this.ingredientid = ingredientid;
    }
}