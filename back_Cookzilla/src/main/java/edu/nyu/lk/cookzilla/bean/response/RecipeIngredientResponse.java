package edu.nyu.lk.cookzilla.bean.response;

import edu.nyu.lk.cookzilla.model.Ingredients;
import edu.nyu.lk.cookzilla.model.Reviews;

import java.util.List;

public class RecipeIngredientResponse {
    private RecipeInfo recipeInfo;

    private List<Ingredients> ingredientsList;

    private List<ReviewsInfo> reviewsInfoList;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RecipeInfo getRecipeInfo() {
        return recipeInfo;
    }

    public void setRecipeInfo(RecipeInfo recipeInfo) {
        this.recipeInfo = recipeInfo;
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<ReviewsInfo> getReviewsInfoList() {
        return reviewsInfoList;
    }

    public void setReviewsInfoList(List<ReviewsInfo> reviewsInfoList) {
        this.reviewsInfoList = reviewsInfoList;
    }
}
