package dev.junyeong.sikggu.global.error;

public record ErrorResponse(
    int status,
    String code,
    String message
) {

}