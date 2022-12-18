package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.ReviewImages;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ReviewImagesMapper {
    int deleteByPrimaryKey(Integer reviewimageid);

    int insert(ReviewImages record);

    int insertSelective(ReviewImages record);

    ReviewImages selectByPrimaryKey(Integer reviewimageid);

    int updateByPrimaryKeySelective(ReviewImages record);

    int updateByPrimaryKey(ReviewImages record);
}