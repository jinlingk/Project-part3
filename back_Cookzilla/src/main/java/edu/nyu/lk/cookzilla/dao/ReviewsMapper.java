package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.Reviews;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ReviewsMapper {
    int deleteByPrimaryKey(Integer reciewid);

    int insert(Reviews record);

    int insertSelective(Reviews record);

    List<Reviews> selectReviewsList(Integer recipeId);

    Reviews selectByPrimaryKey(Integer reciewid);

    int updateByPrimaryKeySelective(Reviews record);

    int updateByPrimaryKey(Reviews record);
}