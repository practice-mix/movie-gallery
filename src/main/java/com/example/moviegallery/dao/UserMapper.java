package com.example.moviegallery.dao;

import com.example.moviegallery.dao.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);
    int upsert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);
    User findByPhoneNumber(@Param("phone_number") String phone_number);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateBatch(List<User> list);

    int batchInsert(@Param("list") List<User> list);
}
