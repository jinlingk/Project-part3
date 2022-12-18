package edu.nyu.lk.cookzilla.services;

import edu.nyu.lk.cookzilla.bean.request.PostRecipeInfoRequest;
import edu.nyu.lk.cookzilla.bean.request.ReviewPostRequest;
import edu.nyu.lk.cookzilla.bean.response.BaseResponse;
import edu.nyu.lk.cookzilla.bean.response.RecipeInfo;
import edu.nyu.lk.cookzilla.bean.response.RecipeIngredientResponse;
import edu.nyu.lk.cookzilla.bean.response.ReviewsInfo;
import edu.nyu.lk.cookzilla.dao.*;
import edu.nyu.lk.cookzilla.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeInfoServices {
    @Autowired
    private RepiceMapper repiceMapper;

    @Autowired
    private IngredientsMapper ingredientsMapper;

    @Autowired
    private RecipeIngredientMapper recipeIngredientMapper;

    @Autowired
    private ReviewsMapper reviewsMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;
    final private Integer offset = 9;

    public List<RecipeInfo> getRecipeInfoList(Integer page){
        List<Repice> repiceList = repiceMapper.selectRepiceList(page * offset, offset);
        List<RecipeInfo> recipeInfoList = new ArrayList<>();
        for(int i =0; i< repiceList.size(); i++){
            RecipeInfo recipeInfo = new RecipeInfo();
            recipeInfo.setImages(repiceList.get(i).getImage());
            recipeInfo.setTitle(repiceList.get(i).getTitle());
            recipeInfo.setServing(Float.valueOf(repiceList.get(i).getServing()));
            recipeInfo.setId(repiceList.get(i).getRecipeid());
            recipeInfoList.add(recipeInfo);
        }
        return recipeInfoList;
    }

    public BaseResponse insertRecipe(PostRecipeInfoRequest postRecipeInfoRequest){
        BaseResponse baseResponse = new BaseResponse();
        Repice repice = new Repice();
        repice.setImage(postRecipeInfoRequest.getImage());
        repice.setTitle(postRecipeInfoRequest.getTitle());
        repice.setServing(postRecipeInfoRequest.getServeing());
        try {
            repiceMapper.insert(repice);
            baseResponse.setMsg("success");
            baseResponse.setRecipeid(repice.getRecipeid());
            return baseResponse;
        }
        catch (Exception e){
            baseResponse.setMsg("insert failed");
            return baseResponse;
        }

    }

    public String addIngredient(Ingredients ingredients, Integer recipeId){

        /**
         * insert ingredient
         */
        Integer ingredientId;
        try {
            ingredientsMapper.insert(ingredients);
            ingredientId = ingredients.getIngredientsid();
        }
        catch (Exception e){
            return  "insert ingredinet failed";
        }

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipeid(recipeId);
        recipeIngredient.setIngredientid(ingredientId);
        try{
            recipeIngredientMapper.insert(recipeIngredient);
        }
        catch (Exception e){
            return  "insert recipeIngredient failed";
        }
        return "success";
    }

    public List<RecipeInfo> searchRecipe(String query, Integer page){
        query = "%" + query + "%";
        List<RecipeInfo> recipeInfoList = new ArrayList<>();
        List<Repice> repiceList;
        try{
            repiceList = repiceMapper.queryRepiceList(query, page * offset, offset);
        }
        catch (Exception e){
            return recipeInfoList;
        }
        for(int i=0; i<repiceList.size();i++){
            RecipeInfo recipeInfo = new RecipeInfo();
            recipeInfo.setId(repiceList.get(i).getRecipeid());
            recipeInfo.setServing(Float.valueOf(repiceList.get(i).getServing()));
            recipeInfo.setTitle(repiceList.get(i).getTitle());
            recipeInfo.setImages(repiceList.get(i).getImage());
            recipeInfoList.add(recipeInfo);
        }
        return recipeInfoList;
    }

    public RecipeIngredientResponse getRecipeIngredient(Integer recipeId){
        RecipeIngredientResponse recipeIngredientResponse = new RecipeIngredientResponse();
        try {
            Repice repice = repiceMapper.selectByPrimaryKey(recipeId);
            RecipeInfo recipeInfo = new RecipeInfo();
            recipeInfo.setImages(repice.getImage());
            recipeInfo.setId(repice.getRecipeid());
            recipeInfo.setServing(Float.valueOf(repice.getServing()));
            recipeInfo.setTitle(repice.getTitle());
            recipeIngredientResponse.setRecipeInfo(recipeInfo);
        }
        catch (Exception e){
            recipeIngredientResponse.setMsg("select Recipe error");
            return recipeIngredientResponse;
        }

        /**
         * get ingredinet list
         */
        List<RecipeIngredient> ingredientIds;
        try {
            ingredientIds = recipeIngredientMapper.selectIngredientIds(recipeId);
        }
        catch (Exception e){
            recipeIngredientResponse.setMsg("select ingredient error");
            return recipeIngredientResponse;
        }
        List<Ingredients> ingredientsList = new ArrayList<>();
        for(int i=0; i<ingredientIds.size(); i++){
            Integer ingredientId = ingredientIds.get(i).getIngredientid();
            Ingredients ingredients = ingredientsMapper.selectByPrimaryKey(ingredientId);
            ingredientsList.add(ingredients);
        }
        recipeIngredientResponse.setIngredientsList(ingredientsList);

        /**
         * get reviews list
         */
        List<ReviewsInfo> reviewsInfoList = new ArrayList<>();
        List<Reviews> reviewsList;
        try {
            reviewsList = reviewsMapper.selectReviewsList(recipeId);
        }
        catch (Exception e){
            recipeIngredientResponse.setMsg("select reviews error");
            return recipeIngredientResponse;
        }
        for(int i=0; i<reviewsList.size();i++){
            ReviewsInfo reviewsInfo = new ReviewsInfo();
            ReviewPostRequest reviewPostRequest = new ReviewPostRequest();
            Reviews reviews = reviewsList.get(i);
            reviewPostRequest.setRecipeid(reviews.getRecipeid());
            reviewPostRequest.setContent(reviews.getContent());
            reviewPostRequest.setUserid(reviews.getUserid());
            reviewPostRequest.setTitle(reviews.getTitle());
            reviewPostRequest.setRating(Integer.valueOf(reviews.getRating()));
            reviewPostRequest.setReciewid(reviewPostRequest.getReciewid());
            reviewsInfo.setReviewPostRequest(reviewPostRequest);
            UserInfo userInfo = userInfoMapper.selectByUserId(reviewsList.get(i).getUserid());
            reviewsInfo.setUsername(userInfo.getUsername());
            reviewsInfoList.add(reviewsInfo);
        }
        recipeIngredientResponse.setReviewsInfoList(reviewsInfoList);
        recipeIngredientResponse.setMsg("success");
        return recipeIngredientResponse;
    }

    public String postReview(ReviewPostRequest reviewPostRequest){
        Reviews reviews = new Reviews();
        reviews.setRecipeid(reviewPostRequest.getRecipeid());
        reviews.setContent(reviewPostRequest.getContent());
        reviews.setUserid(reviewPostRequest.getUserid());
        reviews.setTitle(reviewPostRequest.getTitle());
        reviews.setRating(reviewPostRequest.getRating().toString());
        reviews.setReciewid(reviewPostRequest.getReciewid());
        try {
            reviewsMapper.insert(reviews);
        }
        catch (Exception e){
            return "insert failed";
        }
        return "success";
    }
}
