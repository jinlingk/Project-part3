package edu.nyu.lk.cookzilla.dao;

import edu.nyu.lk.cookzilla.model.Repice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RepiceMapper {
    int deleteByPrimaryKey(Integer recipeid);

    int insert(Repice record);

    int insertSelective(Repice record);

    Repice selectByPrimaryKey(Integer recipeid);

    List<Repice> selectRepiceList(Integer left, Integer offset);

    List<Repice> queryRepiceList(@Param("query") String query, @Param("page") Integer page, @Param("offset") Integer offset);

    int updateByPrimaryKeySelective(Repice record);

    int updateByPrimaryKey(Repice record);
}