package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.RecipeImages;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RecipeImagesMapper {
    int deleteByPrimaryKey(Integer recipeimageid);

    int insert(RecipeImages record);

    int insertSelective(RecipeImages record);

    RecipeImages selectByPrimaryKey(Integer recipeimageid);

    int updateByPrimaryKeySelective(RecipeImages record);

    int updateByPrimaryKey(RecipeImages record);
}