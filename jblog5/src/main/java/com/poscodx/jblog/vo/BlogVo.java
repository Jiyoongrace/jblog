package com.poscodx.jblog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class BlogVo {
    private String id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String logo;
}
