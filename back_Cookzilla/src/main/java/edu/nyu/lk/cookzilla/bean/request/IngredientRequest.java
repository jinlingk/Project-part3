package edu.nyu.lk.cookzilla.bean.request;

import edu.nyu.lk.cookzilla.model.Ingredients;

public class IngredientRequest {

    private Integer recipeId;

    private Ingredients ingredients;

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }
}
