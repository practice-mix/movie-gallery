package com.example.moviegallery.dao;

import com.example.moviegallery.dao.entity.Friends;
import java.util.List;

import com.example.moviegallery.dao.entity.User;
import com.example.moviegallery.dao.model.FriendsListDTO;
import org.apache.ibatis.annotations.Param;

public interface FriendsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Friends record);

    int insertOrUpdate(Friends record);

    int insertOrUpdateSelective(Friends record);

    int insertSelective(Friends record);

    Friends selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Friends record);

    int updateByPrimaryKey(Friends record);

    int updateBatch(List<Friends> list);

    int batchInsert(@Param("list") List<Friends> list);

    List<User> list(FriendsListDTO friendsListDTO);

    Integer listTotal(FriendsListDTO friendsListDTO);

    Friends selectByUidAndFriendUid(@Param("uid") Integer uid, @Param("friend_uid") Integer friend_uid);
}
