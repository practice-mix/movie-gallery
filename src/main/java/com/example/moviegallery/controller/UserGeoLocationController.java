package com.example.moviegallery.controller;

import com.example.moviegallery.controller.model.PageResponseWrapper;
import com.example.moviegallery.controller.model.UserGeoLocationAddLocationRequest;
import com.example.moviegallery.controller.model.UserGeoLocationSearchNearbyRequest;
import com.example.moviegallery.dao.UserGeoLocationMapper;
import com.example.moviegallery.dao.model.SearchNearbyDTO;
import com.example.moviegallery.dao.model.UserGeoLocationCoordinate;
import com.example.moviegallery.dao.model.UserLocationProjection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UserGeoLocationController {

    @Autowired
    private UserGeoLocationMapper userGeoLocationMapper;


    @PutMapping("/UserGeoLocation/addLocation")
    public ResponseEntity<Boolean> addLocation(@Valid @RequestBody UserGeoLocationAddLocationRequest request) {

        UserGeoLocationCoordinate model = new UserGeoLocationCoordinate();
        model.setUid(request.getUid());
        model.setLatitude(request.getLatitude());
        model.setLongitude(request.getLongitude());
        model.setUpdatedTime(LocalDateTime.now());
        userGeoLocationMapper.upsertLocation(model);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/UserGeoLocation/searchNearby")
    public ResponseEntity<PageResponseWrapper<UserLocationProjection>> searchNearby(@Valid UserGeoLocationSearchNearbyRequest request) {
        SearchNearbyDTO searchNearbyDTO = new SearchNearbyDTO();

        BeanUtils.copyProperties(request, searchNearbyDTO);
        List<UserLocationProjection> list = userGeoLocationMapper.searchNearby(searchNearbyDTO);
        int total = userGeoLocationMapper.searchNearbyTotal(searchNearbyDTO);
        PageResponseWrapper<UserLocationProjection> wrapper = new PageResponseWrapper<>(request, list, total);

        return ResponseEntity.ok(wrapper);

    }


}
