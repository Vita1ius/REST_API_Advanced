package com.epam.esm.REST_API_Advanced.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum Exceptions {
    CERTIFICATE_NOT_FOUND(40401, "Certificate not found", HttpStatus.NOT_FOUND),
    TAG_NOT_FOUND(40402, "Tag not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(40403, "User not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(40404, "Order not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXIST(40403, "User with such name already exist", HttpStatus.BAD_REQUEST),
    CERTIFICATE_ALREADY_EXIST(40401, "Certificate with such name already exist", HttpStatus.BAD_REQUEST),
    ENTITY_INVALID_CREATE(40401, "Invalid parameters for create",HttpStatus.BAD_REQUEST),
    PAGE_DOESNT_EXIST(40405,"This page doesn't exist", HttpStatus.BAD_REQUEST);

    private final Integer id;
    private final String message;
    private final HttpStatus status;
}
