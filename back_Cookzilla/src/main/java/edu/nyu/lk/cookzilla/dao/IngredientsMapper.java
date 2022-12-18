package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.Ingredients;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface IngredientsMapper {
    int deleteByPrimaryKey(Integer ingredientsid);

    int insert(Ingredients record);

    int insertSelective(Ingredients record);

    Ingredients selectByPrimaryKey(Integer ingredientsid);

    int updateByPrimaryKeySelective(Ingredients record);

    int updateByPrimaryKey(Ingredients record);
}