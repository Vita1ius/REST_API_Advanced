package com.epam.esm.REST_API_Advanced.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException {

    private final Exceptions exception;

}
