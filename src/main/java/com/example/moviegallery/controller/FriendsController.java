package com.example.moviegallery.controller;

import com.example.moviegallery.controller.model.FriendsAddRequest;
import com.example.moviegallery.controller.model.FriendsListRequest;
import com.example.moviegallery.controller.model.PageResponseWrapper;
import com.example.moviegallery.dao.FriendsMapper;
import com.example.moviegallery.dao.entity.Friends;
import com.example.moviegallery.dao.entity.User;
import com.example.moviegallery.dao.model.FriendsListDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FriendsController {
    @Autowired
    private FriendsMapper friendsMapper;

    @GetMapping("Friends/list")
    public ResponseEntity<PageResponseWrapper<User>> list(FriendsListRequest request) {
        FriendsListDTO friendsListDTO = new FriendsListDTO();
        BeanUtils.copyProperties(request, friendsListDTO);
        List<User> list = friendsMapper.list(friendsListDTO);
        Integer total = friendsMapper.listTotal(friendsListDTO);

        PageResponseWrapper<User> wrapper = new PageResponseWrapper<>(request, list, total);
        return ResponseEntity.ok(wrapper);
    }

    @PutMapping("Friends/add")
    public ResponseEntity<Void> add(@Valid @RequestBody FriendsAddRequest request){
        Friends record = new Friends();
        BeanUtils.copyProperties(request,record);
        Friends friends = friendsMapper.selectByUidAndFriendUid(request.getUid(), request.getFriend_uid());
        if (friends != null) {
            return ResponseEntity.ok(null);
        }

        friendsMapper.insert(record);
        return ResponseEntity.ok(null);
    }
}
