package com.poscodx.jblog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class CategoryVo {
    private Long no;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private String regDate;
    private String blogId;
}
