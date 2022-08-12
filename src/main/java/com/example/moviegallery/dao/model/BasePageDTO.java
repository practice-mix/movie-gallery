package com.example.moviegallery.dao.model;

import lombok.Data;

@Data
public class BasePageDTO {
    protected int pageNumber;
    protected int pageSize;

    public int getStartIndex() {
        return  pageNumber <2? 0 :  (pageNumber - 1) * pageSize;
    }
}
