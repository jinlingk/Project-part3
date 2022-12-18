package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.Images;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ImagesMapper {
    int deleteByPrimaryKey(Integer imagesid);

    int insert(Images record);

    int insertSelective(Images record);

    Images selectByPrimaryKey(Integer imagesid);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKey(Images record);
}