package com.testtasks.blackJack.server.exception;

import org.apache.http.HttpStatus;

public enum Error {

    WRONG_JSON("BJ_0001", "Не верный формат Json", HttpStatus.SC_BAD_REQUEST),
    WRONG_MEDIA_TYPE("BJ_0002", "Не поддерживаемый Media-Type", HttpStatus.SC_BAD_REQUEST),
    WRONG_PARAM("BJ_0003", "Не валидный параметр %s!", HttpStatus.SC_BAD_REQUEST),
    DB_PROBLEMS("BJ_0004", "Проблема при работе с БД!", HttpStatus.SC_INTERNAL_SERVER_ERROR),
    NOT_FOUND_ACCOUNT("BJ_0005", "Не найден счет с номером %s!", HttpStatus.SC_NOT_FOUND),
    NOT_FOUND_GAME("BJ_0005", "Не найдена игра с номером %s привязанная к счету %s!", HttpStatus.SC_NOT_FOUND),
    NOT_FOUND_HAND("BJ_0006", "Не найдена ни одна карта для игры с номером %s", HttpStatus.SC_INTERNAL_SERVER_ERROR),
    NOT_ENOUGH_MONEY("BJ_0007", "Не достаточно средств для начала игры!", HttpStatus.SC_BAD_REQUEST),
    NOT_VALID_GAME_STATE("BJ_0008", "Не доступное действие для данной игры", HttpStatus.SC_CONFLICT),
    NOT_KNOWN_EXCEPTION_ERROR("BJ_0009", "Системная ошибка!", HttpStatus.SC_INTERNAL_SERVER_ERROR),
    NOT_SUPPORTED_HTTP_METHOD("BJ_0010", "Не поддерживаемый http метод!", HttpStatus.SC_METHOD_NOT_ALLOWED);

    private String code;
    private String message;
    private int status;

    Error(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
