package com.example.FirstProject.dto.response;

import lombok.Getter;

@Getter
enum ErrorCodes {
    SUCCESS("0000"),
    FAILED("0001"),
    TWO("0002"),
    THREE("0003");

    <T> ErrorCodes(T message) {
    }
}
