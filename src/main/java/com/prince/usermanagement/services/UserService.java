package com.prince.usermanagement.services;

import com.prince.usermanagement.dtos.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUsers(boolean includeDeleted);
    UserDto getUserById(Long id, boolean includeDeleted);
    UserDto updateUser(UserDto user, Long id);
    void softDeleteUser(Long id);
    void softUnDeleteUser(Long id);
    void hardDeleteUser(Long id);
}
