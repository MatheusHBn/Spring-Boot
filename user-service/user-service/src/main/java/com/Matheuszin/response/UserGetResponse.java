package com.Matheuszin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserGetResponse {
    @Schema(description = "User's id", example = "2")
    private Long id;
    @Schema(description = "User's firstName", example = "Matheus")
    private String firstName;
    @Schema(description = "User's lastName", example = "Nascimento")
    private String lastName;
    @Email(message = "email is not valid")
    @Schema(description = "User's email", example = "mathhes@gmail.com")
    private String email;
}


