package com.testtasks.blackJack.server.exception.handler;

import com.testtasks.blackJack.server.dao.DaoException;
import com.testtasks.blackJack.server.exception.*;
import com.testtasks.blackJack.server.exception.Error;
import com.testtasks.blackJack.server.validator.ParamValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    private static final String ERROR_TEXT_FORMAT = "%s -> %s";

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletResponse response){
        Error error = Error.WRONG_JSON;
        prepareResponse(error, ex, response);
        return new ErrorResponse(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public ErrorResponse handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, HttpServletResponse response){
        Error error = Error.WRONG_MEDIA_TYPE;
        prepareResponse(error, ex, response);
        return new ErrorResponse(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public ErrorResponse handleValidationException(ParamValidationException ex, HttpServletResponse response){
        prepareResponse(ex.getError(), ex, response);
        return new ErrorResponse(ex.getError().getCode(), ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public ErrorResponse handleDaoException(DaoException ex, HttpServletResponse response){
        prepareResponse(ex.getError(), ex, response);
        return new ErrorResponse(ex.getError().getCode(), ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public ErrorResponse handleServiceException(ServiceException ex, HttpServletResponse response){
        prepareResponse(ex.getError(), ex, response);
        return new ErrorResponse(ex.getError());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex,
                                                                      HttpServletResponse response){
        Error error = Error.NOT_SUPPORTED_HTTP_METHOD;
        prepareResponse(error, ex, response);
        return new ErrorResponse(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public ErrorResponse handleException(Exception ex, HttpServletResponse response){
        Error error = Error.NOT_KNOWN_EXCEPTION_ERROR;
        prepareResponse(error, ex, response);
        return new ErrorResponse(error);
    }

    private void prepareResponse(Error error, Throwable cause, HttpServletResponse response) {
        LOGGER.error(String.format(ERROR_TEXT_FORMAT, error.getCode(), error.getMessage()), cause);
        response.setStatus(error.getStatus());
    }
}
