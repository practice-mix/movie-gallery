package com.example.moviegallery.controller;

import com.example.moviegallery.controller.model.ErrorReportPostRequest;
import com.example.moviegallery.dao.ErrorMapper;
import com.example.moviegallery.dao.entity.Error;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorReportController {
    @Autowired
    ErrorMapper errorMapper;

    @PostMapping("/errorReport/post")
    public ResponseEntity<Void> post(@RequestBody ErrorReportPostRequest request) {
        Error error = new Error();
        BeanUtils.copyProperties(request, error);
        errorMapper.insert(error);

        return ResponseEntity.ok(null);
    }
}
