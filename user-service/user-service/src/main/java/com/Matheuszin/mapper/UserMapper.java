package com.Matheuszin.mapper;

import com.Matheuszin.domain.User;
import com.Matheuszin.request.UserPostRequest;
import com.Matheuszin.request.UserPutRequest;
import com.Matheuszin.response.UserPostResponse;
import com.Matheuszin.response.UserGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(1000))")
    User toUser(UserPutRequest request);

    User toUser(UserPostRequest userPostRequest);

    UserGetResponse toUserGetResponse(User user);

    List<UserGetResponse> toUserGetResponseList(List<User> user);

    User toUser(UserPostResponse userPostRequest);
}
