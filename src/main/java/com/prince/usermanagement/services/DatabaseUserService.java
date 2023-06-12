package com.prince.usermanagement.services;

import com.prince.usermanagement.dtos.UserDto;
import com.prince.usermanagement.exceptions.ResourceNotFoundException;
import com.prince.usermanagement.models.User;
import com.prince.usermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseUserService implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public List<UserDto> getAllUsers(boolean includeDeleted) {
        List<User> users;
        if(includeDeleted) {
            users = userRepository.findAll();
        }else{
            users = userRepository.findByDeletedFalse();

        }

        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId, boolean includeDeleted) {
        if(includeDeleted) {
            User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

            return userToDto(user);
        }

        User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        return userToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(user);
        return userToDto(updatedUser);
    }

    @Override
    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        if(user != null) {
            user.setDeleted(true);
            userRepository.save(user);
        }
    }

    @Override
    public void softUnDeleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        if(user != null) {
            user.setDeleted(false);
            userRepository.save(user);
        }
    }

    @Override
    public void hardDeleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDto userToDto(User user) {
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getFullName(), user.getEmail());
        return userDto;
    }

    public User dtoToUser(UserDto userDto) {
        User user = new User(userDto.getId(), userDto.getUsername(), userDto.getFullName(), userDto.getEmail());
        return user;
    }
}
