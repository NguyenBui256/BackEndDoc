package com.btvn.resume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    @NotEmpty(message="Title missing")
    @JsonProperty("title")
    private String bookTitle;

    @NotEmpty(message = "Publisher missing")
    @JsonProperty("publisher")
    private String publisher;
}
