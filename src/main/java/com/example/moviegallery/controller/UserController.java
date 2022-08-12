package com.example.moviegallery.controller;

import com.example.moviegallery.controller.model.UserRegisterRequest;
import com.example.moviegallery.dao.UserMapper;
import com.example.moviegallery.dao.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;


    @PutMapping("/User/register")
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



}
