package com.poscodx.jblog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class PostVo {
    private Long no;

    @NotEmpty
    private String title;

    @NotEmpty
    private String contents;

    private String regDate;
    private Long categoryNo;
}