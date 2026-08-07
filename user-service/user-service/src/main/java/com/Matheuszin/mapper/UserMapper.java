package com.Matheuszin.mapper;

import com.Matheuszin.domain.User;
import com.Matheuszin.request.UserPostRequest;
import com.Matheuszin.request.UserPutRequest;
import com.Matheuszin.response.UserGetResponse;
import com.Matheuszin.response.UserPostResponse;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserPutRequest request);

    User toUser(UserPostRequest userPostRequest);

    UserGetResponse toUserGetResponse(User user);

    List<UserGetResponse> toUserGetResponseList(List<User> user);

    User toUser(UserPostResponse userPostRequest);
}
