package ru.niu.itmo.feedback.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.niu.itmo.feedback.dto.UserDto;
import ru.niu.itmo.feedback.entity.security.User;
import ru.niu.itmo.feedback.mapper.UserMapper;
import ru.niu.itmo.feedback.repository.UserRepository;
import ru.niu.itmo.feedback.service.exceptions.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto findById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("there is no user with this id"));
        return UserMapper.INSTANCE.toDto(user);
    }

    public boolean existById(Long id) {
        return userRepository
                .existsById(id);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("there is no user with this username"));
        return UserMapper.INSTANCE.toDto(user);
    }

    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


}
