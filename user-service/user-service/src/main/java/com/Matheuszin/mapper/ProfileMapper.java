package com.Matheuszin.mapper;

import com.Matheuszin.domain.Profile;
import com.Matheuszin.request.ProfilePostRequest;
import com.Matheuszin.response.ProfileGetResponse;
import com.Matheuszin.response.ProfilePostResponse;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile toProfile(ProfilePostRequest profilePostRequest);

    ProfileGetResponse toProfileGetResponse(Profile profile);

    List<ProfileGetResponse> toProfileGetResponseList(List<Profile> profile);

    Profile toProfile(ProfilePostResponse profilePostRequest);
}
