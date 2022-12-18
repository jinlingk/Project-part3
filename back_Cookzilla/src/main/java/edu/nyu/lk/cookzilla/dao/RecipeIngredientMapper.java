package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.RecipeIngredient;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RecipeIngredientMapper {
    int deleteByPrimaryKey(Integer recipeingredientid);

    int insert(RecipeIngredient record);

    int insertSelective(RecipeIngredient record);

    List<RecipeIngredient> selectIngredientIds(Integer recipeId);

    RecipeIngredient selectByPrimaryKey(Integer recipeingredientid);

    int updateByPrimaryKeySelective(RecipeIngredient record);

    int updateByPrimaryKey(RecipeIngredient record);
}