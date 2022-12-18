package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.GroupEvent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GroupEventMapper {
    int deleteByPrimaryKey(Integer groupeventid);

    int insert(GroupEvent record);

    int insertSelective(GroupEvent record);

    GroupEvent selectByPrimaryKey(Integer groupeventid);

    int updateByPrimaryKeySelective(GroupEvent record);

    int updateByPrimaryKey(GroupEvent record);
}