package com.btvn.resume.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonPropertyOrder({"data", "message"})
public class CustomResponse<T> implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String message;

    public CustomResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public CustomResponse(String message) {
        this.message = message;
    }

    public CustomResponse(T data) {
        this.data = data;
    }
}