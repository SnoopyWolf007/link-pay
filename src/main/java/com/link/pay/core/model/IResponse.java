package com.link.pay.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IResponse {
    private Integer code;
    private String message;
    private Object data;

    public static IResponse getInstance() {
        return new IResponse();
    }

    public static IResponse success() {
        return getInstance().setCode(1).setMessage("success");
    }

    public static IResponse fail() {
        return getInstance().setCode(-1).setMessage("fail");
    }
}
