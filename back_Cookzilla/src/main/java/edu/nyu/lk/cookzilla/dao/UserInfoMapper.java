package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserInfoMapper {
    int deleteByPrimaryKey(String username);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String username);

    UserInfo selectByUserId(Integer usedId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}