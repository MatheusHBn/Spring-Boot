package com.Matheuszin.controller;

import com.Matheuszin.domain.User;
import com.Matheuszin.mapper.UserMapper;
import com.Matheuszin.request.UserPostRequest;
import com.Matheuszin.request.UserPutRequest;
import com.Matheuszin.response.UserGetResponse;
import com.Matheuszin.response.UserPostResponse;
import com.Matheuszin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping()
    public ResponseEntity<List<UserGetResponse>> listAllUsers(@RequestParam(required = false) String firstName) {
        var users = service.findAll(firstName);
        var userGetResponse = mapper.toUserGetResponseList(users);
        return ResponseEntity.ok(userGetResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        var user = service.findByIdOrThrowNotFound(id);
        var userGetResponse = mapper.toUserGetResponse(user);
        return ResponseEntity.ok(userGetResponse);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<User>> findAllPaginated(Pageable pageable) {
        var pageUser = service.findAllPaged(pageable);
        return ResponseEntity.ok(pageUser);

    }

    @PostMapping()
    public ResponseEntity<UserGetResponse> save(@Valid @RequestBody UserPostRequest userPostRequest) {
        var user = mapper.toUser(userPostRequest);
        var userSaved = service.save(user);
        var userGetResponse = mapper.toUserGetResponse(userSaved);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userGetResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UserPutRequest request){
        var userToUpdate = mapper.toUser(request);
        service.update(userToUpdate);
        return ResponseEntity.noContent().build();
    }
}
