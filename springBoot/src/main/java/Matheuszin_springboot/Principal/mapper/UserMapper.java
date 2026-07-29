package Matheuszin_springboot.Principal.mapper;

import Matheuszin_springboot.Principal.domain.User;
import Matheuszin_springboot.Principal.request.UserPostRequest;
import Matheuszin_springboot.Principal.request.UserPutRequest;
import Matheuszin_springboot.Principal.response.UserGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(1000))")
    User toUser(UserPutRequest userPutRequest);
    User toUser(UserPostRequest userPostRequest);
    UserGetResponse toUserGetResponse(User user);
    List<UserGetResponse> toUserListGetResponse(List<UserGetResponse> userListGetResponse);
    List<UserGetResponse> toUserGetResponse(List<User> users);
}
