package com.example.moviegallery.controller.model;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class PageResponseWrapper<T> {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer total;
    private List<T> list;

    public PageResponseWrapper( BasePageRequest pageRequest, List<T> list, int total) {
        BeanUtils.copyProperties(pageRequest,this);
        this.total = total;
        this.list = list;

    }


}
