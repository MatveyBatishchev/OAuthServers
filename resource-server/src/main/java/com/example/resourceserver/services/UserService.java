package com.example.resourceserver.services;

import com.example.resourceserver.dto.UserDto;
import com.example.resourceserver.error.UserAlreadyExistsException;
import com.example.resourceserver.mappers.UserMapper;
import com.example.resourceserver.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("There is already an account with that email: " + userDto.getEmail());
        }
        return userMapper.toDto(userRepository.save(userMapper.toUser(userDto)));
    }

}
