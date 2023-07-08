package com.example.db_test;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not found")  // 404
public class NumberNotFoundException extends RuntimeException {
    // ...
}