package com.Matheuszin.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @NotNull
    @Schema(description = "User's id", example = "2")
    private Long id;
    @NotBlank(message = "the field is required")
    @Schema(description = "User's firstName", example = "Matheus")
    private String firstName;
    @Schema(description = "User's lastName", example = "Nascimento")
    @NotBlank(message = "the field is required")
    private String lastName;
    @Email(message = "email is not valid")
    @NotBlank(message = "the field is required")
    @Schema(description = "User's email", example = "mathhes@gmail.com")
    private String email;
}
