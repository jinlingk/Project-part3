package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.RecipeRevice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RecipeReviceMapper {
    int deleteByPrimaryKey(Integer recipereviewid);

    int insert(RecipeRevice record);

    int insertSelective(RecipeRevice record);

    RecipeRevice selectByPrimaryKey(Integer recipereviewid);

    int updateByPrimaryKeySelective(RecipeRevice record);

    int updateByPrimaryKey(RecipeRevice record);
}