package com.Matheuszin.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserPostRequest {
    @NotBlank(message = "the field is required")
    private String firstName;
    @NotBlank(message = "the field is required")
    private String lastName;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]]+\\.)+[\\w-]{2,4}$"  ,message = "email is not valid")
    @NotBlank(message = "the field is required")
    private String email;
}
