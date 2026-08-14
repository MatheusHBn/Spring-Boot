package com.Matheuszin.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileGetResponse {
    private Long id;
    private String name;
    private String description;

}


