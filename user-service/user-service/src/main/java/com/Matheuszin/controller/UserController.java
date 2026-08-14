package com.Matheuszin.controller;

import Matheuszin_springboot.exception.ApiError;
import Matheuszin_springboot.exception.DefaultErrorMessage;
import com.Matheuszin.domain.User;
import com.Matheuszin.mapper.UserMapper;
import com.Matheuszin.request.UserPostRequest;
import com.Matheuszin.request.UserPutRequest;
import com.Matheuszin.response.UserGetResponse;
import com.Matheuszin.response.UserPostResponse;
import com.Matheuszin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Consmer API", description = "This API show all functions in Spirng (Delete, put etc.)")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all users", description = "Get all users available", responses = {
            @ApiResponse(description = "List all users", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserGetResponse.class)))),
            @ApiResponse(description = "User not found", responseCode = "404",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = DefaultErrorMessage.class))))
    })
    public ResponseEntity<List<UserGetResponse>> listAllUsers(@RequestParam(required = false) String firstName) {
        var users = service.findAll(firstName);
        var userGetResponse = mapper.toUserGetResponseList(users);
        return ResponseEntity.ok(userGetResponse);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get all users", responses = {
            @ApiResponse(description = "List all users", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserGetResponse.class)))
    })
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
    @Operation(summary = "Create ser", responses = {
            @ApiResponse(description = "Save user in the database", responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserGetResponse.class)))),
            @ApiResponse(description = "User not found", responseCode = "404",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ApiError.class))))
    })
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
