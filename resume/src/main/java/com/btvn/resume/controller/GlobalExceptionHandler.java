package com.btvn.resume.controller;

import com.btvn.resume.dto.CustomResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ChangeSetPersister.NotFoundException.class, NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomResponse<Object> handleNotFoundException(Exception exception) {
        return new CustomResponse<>(exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomResponse<Object> handleBadInput(MethodArgumentNotValidException e) {
        return new CustomResponse<>("Fields not validated: " + e.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomResponse<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return new CustomResponse<>("Missing required parameter: " + ex.getParameterName());
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomResponse<Object> handleJsonProcessingException(Exception exception) {
        return new CustomResponse<>(exception.getMessage());
    }

}
