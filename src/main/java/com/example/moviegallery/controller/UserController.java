package com.example.moviegallery.controller;

import com.example.moviegallery.dao.UserMapper;
import com.example.moviegallery.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;


    @PutMapping("/User/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        userMapper.upsert(user);
        User nUser = userMapper.findByPhoneNumber(user.getPhone_number());

        return ResponseEntity.ok(nUser);
    }



}
