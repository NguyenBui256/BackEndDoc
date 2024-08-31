package com.btvn.resume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AuthorDTO {
    @NotEmpty(message="Author name missing")
    @JsonProperty("author_name")
    private String authorName;

    @Email(message="Invalid email")
    @JsonProperty("email")
    private String email;

    @NotEmpty(message="Password missing")
    @Length(min=8,message="Password has at least 8 characters")
    @JsonProperty("password")
    private String password;
}
