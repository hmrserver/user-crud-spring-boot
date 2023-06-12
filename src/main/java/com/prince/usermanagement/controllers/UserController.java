package com.prince.usermanagement.controllers;

import com.prince.usermanagement.dtos.UserDto;
import com.prince.usermanagement.services.DatabaseUserService;
import com.prince.usermanagement.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    DatabaseUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(value = "includeDeleted", defaultValue = "false") boolean includeDeleted) {
        List<UserDto> users = userService.getAllUsers(includeDeleted);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id,@RequestParam(value = "includeDeleted", defaultValue = "false") boolean includeDeleted) {
        UserDto user = userService.getUserById(id, includeDeleted);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long id) {
        UserDto updatedUser = userService.updateUser(userDto, id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> softDeleteUser(@PathVariable("id") Long id) {
        userService.softDeleteUser(id);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }

    @PutMapping("/users/{id}/undelete")
    public ResponseEntity<?> softUnDeleteUser(@PathVariable("id") Long id) {
        UserDto userDto = userService.getUserById(id, true);
        userService.softUnDeleteUser(userDto.getId());
        return new ResponseEntity<>(new ApiResponse("User restored successfully", true), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}/hard")
    public ResponseEntity<?> hardDeleteUser(@PathVariable("id") Long id) {
        userService.hardDeleteUser(id);
        return new ResponseEntity<>(new ApiResponse("User permanently deleted successfully", true), HttpStatus.OK);
    }
}
