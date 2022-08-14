package com.example.moviegallery.controller;

import com.example.moviegallery.controller.model.UserRegisterRequest;
import com.example.moviegallery.controller.model.UserUpdateAvatarRequest;
import com.example.moviegallery.dao.UserMapper;
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


    @PutMapping("/User/register")
    @Transactional
    public ResponseEntity<User> register(@Valid @RequestBody UserRegisterRequest request) {
        User oldUser = userMapper.findByPhoneNumber(request.getPhone_number());
        if (oldUser != null) {
            BeanUtils.copyProperties(request, oldUser);
            userMapper.updateByPrimaryKey(oldUser);

        } else {
            User nUser = new User();
            BeanUtils.copyProperties(request, nUser);
            userMapper.insert(nUser);

        }
        User nUser = userMapper.findByPhoneNumber(request.getPhone_number());

        return ResponseEntity.ok(nUser);
    }

    @GetMapping("/User/getDetail")
    public ResponseEntity<User> getDetail(@RequestParam Integer uid) {
        User nUser = userMapper.selectByPrimaryKey(uid);

        return ResponseEntity.ok(nUser);
    }

    @PatchMapping("/User/updateAvatar")
    public ResponseEntity<Void> updateAvatar(@RequestBody UserUpdateAvatarRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);

        userMapper.updateByPrimaryKeySelective(user);

        return ResponseEntity.ok(null);
    }


}
