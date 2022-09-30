package com.example.moviegallery.controller;

import com.example.moviegallery.controller.model.UserGetDetailResponse;
import com.example.moviegallery.controller.model.UserGetDetailV2Response;
import com.example.moviegallery.controller.model.UserRegisterRequest;
import com.example.moviegallery.controller.model.UserUpdateAvatarRequest;
import com.example.moviegallery.dao.FriendsMapper;
import com.example.moviegallery.dao.UserMapper;
import com.example.moviegallery.dao.entity.Friends;
import com.example.moviegallery.dao.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendsMapper friendsMapper;


    @PutMapping("/User/register")
    @Transactional
    public ResponseEntity<User> register(@Valid @RequestBody UserRegisterRequest request) {
        User oldUser = userMapper.findByPhoneNumber(request.getPhone_number());
        if (oldUser != null) {
            BeanUtils.copyProperties(request, oldUser);
            userMapper.updateByPrimaryKeySelective(oldUser);

        } else {
            User nUser = new User();
            BeanUtils.copyProperties(request, nUser);
            userMapper.insert(nUser);

        }
        User nUser = userMapper.findByPhoneNumber(request.getPhone_number());

        return ResponseEntity.ok(nUser);
    }

    @GetMapping("/User/getDetail")
    public ResponseEntity<UserGetDetailResponse> getDetail(@RequestParam Integer uid) {
        User user = userMapper.selectByPrimaryKey(uid);
        UserGetDetailResponse response = new UserGetDetailResponse();
        BeanUtils.copyProperties(user, response);

        return ResponseEntity.ok(response);
    }

    /**
     * bad design, friend relationship should be separated from user, it should be queried from FriendContoller
     */
    @Deprecated
    @GetMapping("User/getDetailV2")
    public ResponseEntity<UserGetDetailV2Response> getDetailV2(@RequestParam Integer selfUid,
                                                               @RequestParam Integer uid
    ) {

        User user = userMapper.selectByPrimaryKey(uid);
        UserGetDetailV2Response response = new UserGetDetailV2Response();
        BeanUtils.copyProperties(user, response);

        Friends friends = friendsMapper.selectByUidAndFriendUid(selfUid, uid);
        response.setAreFriend(friends != null);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/User/updateAvatar")
    public ResponseEntity<Void> updateAvatar(@RequestBody UserUpdateAvatarRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);

        userMapper.updateByPrimaryKeySelective(user);

        return ResponseEntity.ok(null);
    }


}
