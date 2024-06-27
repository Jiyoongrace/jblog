package com.poscodx.jblog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class UserVo {
    @NotEmpty
    @Length(min=2, max=8)
    private String id;

    private String name;

    @NotEmpty
    @Length(min=4, max=16)
    private String password;

    private String joinDate;
}
