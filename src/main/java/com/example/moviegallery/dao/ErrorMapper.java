package com.example.moviegallery.dao;

import com.example.moviegallery.dao.entity.Error;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ErrorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Error record);

    int insertOrUpdate(Error record);

    int insertOrUpdateSelective(Error record);

    int insertSelective(Error record);

    Error selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Error record);

    int updateByPrimaryKey(Error record);

    int updateBatch(List<Error> list);

    int batchInsert(@Param("list") List<Error> list);
}
