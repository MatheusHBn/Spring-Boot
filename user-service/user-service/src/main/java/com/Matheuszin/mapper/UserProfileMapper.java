package com.Matheuszin.mapper;

import com.Matheuszin.domain.User;
import com.Matheuszin.domain.UserProfile;
import com.Matheuszin.response.UserProfileGetResponse;
import com.Matheuszin.response.UserProfileUserGetResponse;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    List<UserProfileGetResponse> toUserProfileGetResponse(List<UserProfile> userProfiles);

    List<UserProfileUserGetResponse> toUserProfileUserGetResponseList(List<User> users);

}
