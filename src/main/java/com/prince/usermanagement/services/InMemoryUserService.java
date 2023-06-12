package com.prince.usermanagement.services;

import com.prince.usermanagement.dtos.UserDto;
import com.prince.usermanagement.exceptions.ResourceNotFoundException;
import com.prince.usermanagement.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InMemoryUserService implements UserService {

    private final List<User> users;
    private Long nextId;

    public InMemoryUserService() {
        this.users = new ArrayList<>();
        this.nextId = 0L;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Long userId = nextId++;
        User user = dtoToUser(userDto);
        user.setId(userId);
        users.add(user);
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers(boolean includeDeleted) {
        if(includeDeleted){
            return users.stream().map(this::userToDto).collect(Collectors.toList());
        }

        return users.stream().filter(user -> !user.isDeleted()).map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id, boolean includeDeleted) {

        User SelectedUser = users.stream().filter(user -> user.getId().equals(id)).findFirst().orElseThrow(()-> new ResourceNotFoundException("User", "id", id));

        if(SelectedUser != null) {
            if(!includeDeleted && SelectedUser.isDeleted()){
                return null;
            }
        }

        assert SelectedUser != null;
        return userToDto(SelectedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto,Long id) {
        Long userId = userDto.getId();
        for(int index = 0; index < users.size(); index++) {
            User oldUser = users.get(index);
            if(oldUser.getId().equals(userId)) {
                User user = dtoToUser(userDto);
                users.set(index, user);
                return userDto;
            }
        }

        throw new ResourceNotFoundException("User", "id", id);
    }

    @Override
    public void softDeleteUser(Long userId) {
        for(int index = 0; index < users.size(); index++) {
            User user = users.get(index);
            if(user.getId().equals(userId)) {
                user.setDeleted(true);
                users.set(index, user);
            }
        }
    }

    @Override
    public void softUnDeleteUser(Long userId) {
        for(int index = 0; index < users.size(); index++) {
            User user = users.get(index);
            if(user.getId().equals(userId)) {
                user.setDeleted(false);
                users.set(index, user);
            }
        }
    }

    @Override
    public void hardDeleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
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
