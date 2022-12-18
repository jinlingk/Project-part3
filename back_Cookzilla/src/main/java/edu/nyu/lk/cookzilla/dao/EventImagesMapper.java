package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.EventImages;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface EventImagesMapper {
    int deleteByPrimaryKey(Integer eventimageid);

    int insert(EventImages record);

    int insertSelective(EventImages record);

    EventImages selectByPrimaryKey(Integer eventimageid);

    int updateByPrimaryKeySelective(EventImages record);

    int updateByPrimaryKey(EventImages record);
}