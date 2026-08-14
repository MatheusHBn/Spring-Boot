package com.Matheuszin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPostResponse {
    @Schema(description = "User's id", example = "2")
    private Long id;
}
