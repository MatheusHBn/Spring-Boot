package com.Matheuszin.controller;

import com.Matheuszin.mapper.ProfileMapper;
import com.Matheuszin.request.ProfilePostRequest;
import com.Matheuszin.response.ProfileGetResponse;
import com.Matheuszin.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/profiles")
@Slf4j
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService service;
    private final ProfileMapper mapper;

    @GetMapping()
    public ResponseEntity<List<ProfileGetResponse>> listAllProfiles() {
        var profiles = service.findAll();
        var profileGetResponse = mapper.toProfileGetResponseList(profiles);
        return ResponseEntity.ok(profileGetResponse);
    }

    @PostMapping()
    public ResponseEntity<ProfileGetResponse> save(@Valid @RequestBody ProfilePostRequest profilePostRequest) {
        var profile = mapper.toProfile(profilePostRequest);
        var profileSaved = service.save(profile);
        var profileGetResponse = mapper.toProfileGetResponse(profileSaved);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(profileGetResponse);
    }

}
