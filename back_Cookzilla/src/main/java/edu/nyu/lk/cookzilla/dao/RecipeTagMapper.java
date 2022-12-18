package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.RecipeTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RecipeTagMapper {
    int deleteByPrimaryKey(Integer recipetagid);

    int insert(RecipeTag record);

    int insertSelective(RecipeTag record);

    RecipeTag selectByPrimaryKey(Integer recipetagid);

    int updateByPrimaryKeySelective(RecipeTag record);

    int updateByPrimaryKey(RecipeTag record);
}