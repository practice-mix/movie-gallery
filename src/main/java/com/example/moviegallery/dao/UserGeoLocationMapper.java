package com.example.moviegallery.dao;

import com.example.moviegallery.dao.entity.UserGeoLocation;
import java.util.List;

import com.example.moviegallery.dao.model.SearchNearbyDTO;
import com.example.moviegallery.dao.model.UserGeoLocationCoordinate;
import com.example.moviegallery.dao.model.UserLocationProjection;
import org.apache.ibatis.annotations.Param;

public interface UserGeoLocationMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(UserGeoLocation record);

    int insertSelective(UserGeoLocation record);

    UserGeoLocation selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(UserGeoLocation record);

    int updateByPrimaryKey(UserGeoLocation record);

    int updateBatch(List<UserGeoLocation> list);

    int batchInsert(@Param("list") List<UserGeoLocation> list);

    int upsertLocation(UserGeoLocationCoordinate row);

    List<UserLocationProjection> searchNearby(SearchNearbyDTO searchNearbyDTO);

    int searchNearbyTotal(SearchNearbyDTO searchNearbyDTO);
}
