package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.domain.User;
import Matheuszin_springboot.Principal.mapper.UserMapper;
import Matheuszin_springboot.Principal.request.UserPostRequest;
import Matheuszin_springboot.Principal.request.UserPutRequest;
import Matheuszin_springboot.Principal.response.UserGetResponse;
import Matheuszin_springboot.Principal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("list")
    public List<User> findAllList() {
        return service.findAllUser();
    }

    @GetMapping("firstName")
    public ResponseEntity<List<UserGetResponse>> findByFirstName(@RequestParam(required = false) String firstName) {
        var users = service.findAllUserFirstName(firstName);
        var producerGetResponse = mapper.toUserGetResponse(users);
        return ResponseEntity.ok(producerGetResponse);
    }

    @GetMapping("lastName")
    public ResponseEntity<List<UserGetResponse>> findByLastName(@RequestParam(required = false) String lastName) {
        var users = service.findAllUserLastName(lastName);
        var producerGetResponse = mapper.toUserGetResponse(users);
        return ResponseEntity.ok(producerGetResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        var user = service.findByIdOrThrowNotFoundUser(id);
        var userGetId = mapper.toUserGetResponse(user);
        return ResponseEntity.ok(userGetId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetResponse> save(@RequestBody @Valid UserPostRequest userPostRequest) {
        var user = mapper.toUser(userPostRequest);
        var userSaved = service.save(user);
        var userGetReponse = mapper.toUserGetResponse(userSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(userGetReponse);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UserPutRequest userPutRequest) {
        var user = mapper.toUser(userPutRequest);
        service.update(user);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteByIdUser(id);

        return ResponseEntity.noContent().build();
    }
}
