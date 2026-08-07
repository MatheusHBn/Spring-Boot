package com.Matheuszin.controller;

import com.Matheuszin.mapper.UserProfileMapper;
import com.Matheuszin.response.UserProfileGetResponse;
import com.Matheuszin.response.UserProfileUserGetResponse;
import com.Matheuszin.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/user-profile")
@Slf4j
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService service;
    private final UserProfileMapper mapper;

    @GetMapping()
    public ResponseEntity<List<UserProfileGetResponse>> listAllUsers() {
        var userProfileList = service.findAll();
        var userProfileGetResponse = mapper.toUserProfileGetResponse(userProfileList);
        return ResponseEntity.ok(userProfileGetResponse);
    }


    @GetMapping("profiles/{id}/users")
    public ResponseEntity<List<UserProfileUserGetResponse>> listAllUsers(@PathVariable Long id) {
        var userProfileList = service.findAllUsersByProfileId(id);
        var userProfileUserGetResponseList = mapper.toUserProfileUserGetResponseList(userProfileList);
        return ResponseEntity.ok(userProfileUserGetResponseList);
    }
}
