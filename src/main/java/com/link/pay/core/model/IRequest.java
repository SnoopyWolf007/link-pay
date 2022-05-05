package com.link.pay.core.model;

import lombok.Data;

@Data
public class IRequest<T> {
    private Header header;
    private T body;

    @Data
    public static class Header {
        private String token;
    }

}
