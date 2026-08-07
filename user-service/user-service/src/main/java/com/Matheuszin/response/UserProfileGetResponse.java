package com.Matheuszin.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserProfileGetResponse {
    private Long id;
    public record User(Long id, String firstName){}
    public record Profile(Long id, String name){}
    private User user;
    private Long userId;
    private String userFirstName;

}


