package com.example.moviegallery.controller.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public abstract class BasePageRequest {
    private final int PAGE_SIZE = 10;
    private final int DEFAULT_PAGE = 1;


    @Min(1)
    @NotNull
    protected Integer pageNumber = DEFAULT_PAGE;
    @Max(100)
    @NotNull
    protected Integer pageSize = PAGE_SIZE;
}
