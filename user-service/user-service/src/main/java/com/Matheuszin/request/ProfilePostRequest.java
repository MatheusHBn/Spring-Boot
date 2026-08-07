package com.Matheuszin.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProfilePostRequest {
    @NotBlank(message = "the field is required")
    private String name;
    @NotBlank(message = "the field is required")
    private String description;

}
