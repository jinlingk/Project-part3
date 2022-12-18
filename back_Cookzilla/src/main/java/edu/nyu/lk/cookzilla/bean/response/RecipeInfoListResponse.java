package edu.nyu.lk.cookzilla.bean.response;

import java.util.List;

public class RecipeInfoListResponse {

    private List<RecipeInfo> recipeInfoList;

    private String avatar;

    private Integer length;

    public List<RecipeInfo> getRecipeInfoList() {
        return recipeInfoList;
    }

    public void setRecipeInfoList(List<RecipeInfo> recipeInfoList) {
        this.recipeInfoList = recipeInfoList;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
