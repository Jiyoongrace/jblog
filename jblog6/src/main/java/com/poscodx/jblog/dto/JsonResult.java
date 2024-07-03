package com.poscodx.jblog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResult {
    private String result;   // "success" or "fail"
    private String message;  // if fail, set
    private Object data;     // if success, set

    private JsonResult(Object data) {
        result = "success";
        this.data = data;
    }

    private JsonResult(String message) {
        result = "fail";
        this.message = message;
    }

    public static JsonResult success(Object data) {
        return new JsonResult(data);
    }

    public static JsonResult fail(String message) {
        return new JsonResult(message);
    }
}