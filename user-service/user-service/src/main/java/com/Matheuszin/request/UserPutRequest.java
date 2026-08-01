package com.Matheuszin.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class UserPutRequest {
    @NotNull(message = "the field cannot be null")
    private Long id;
    @NotBlank(message = "the field is required")
    private String firstName;
    @NotBlank(message = "the field is required")
    private String lastName;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]]+\\.)+[\\w-]{2,4}$", message = "email is not valid")
    @NotBlank(message = "the field is required")
    private String email;
}
