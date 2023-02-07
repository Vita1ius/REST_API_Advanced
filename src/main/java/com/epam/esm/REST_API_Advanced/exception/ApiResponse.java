package com.epam.esm.REST_API_Advanced.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true, builderMethodName = "of")
public class ApiResponse {

    public static final String RESPONSE_JSON_TEMPLATE = "{ "
            + "\"errorCode\": %d,"
            + "\"errorName\": \"%s\","
            + "\"message\": \"%s\","
            + "\"path\": \"%s\" "
            + "}";

    private Integer errorCode;
    private String errorName;
    private String message;
    private String path;
}
