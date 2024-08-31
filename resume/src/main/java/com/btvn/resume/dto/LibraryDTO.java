package com.btvn.resume.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDTO {
    @NotEmpty
    @JsonProperty("library_name")
    private String name;
    @NotEmpty
    @JsonProperty("address")
    private String address;
}
