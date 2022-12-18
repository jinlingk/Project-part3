package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.RecipeLink;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RecipeLinkMapper {
    int deleteByPrimaryKey(Integer recipelinkid);

    int insert(RecipeLink record);

    int insertSelective(RecipeLink record);

    RecipeLink selectByPrimaryKey(Integer recipelinkid);

    int updateByPrimaryKeySelective(RecipeLink record);

    int updateByPrimaryKey(RecipeLink record);
}