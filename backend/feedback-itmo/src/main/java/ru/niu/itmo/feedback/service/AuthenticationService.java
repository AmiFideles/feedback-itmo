package ru.niu.itmo.feedback.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.niu.itmo.feedback.dto.UserDto;
import ru.niu.itmo.feedback.dto.request.AuthDto;
import ru.niu.itmo.feedback.dto.response.TokenDto;
import ru.niu.itmo.feedback.entity.security.RefreshToken;
import ru.niu.itmo.feedback.entity.security.User;
import ru.niu.itmo.feedback.mapper.UserMapper;
import ru.niu.itmo.feedback.repository.RefreshTokenRepository;
import ru.niu.itmo.feedback.repository.UserRepository;
import ru.niu.itmo.feedback.service.exceptions.InvalidRefreshTokenException;
import ru.niu.itmo.feedback.service.exceptions.UserAlreadyExist;
import ru.niu.itmo.feedback.service.exceptions.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;

    @Transactional
    public TokenDto authenticate(AuthDto dto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() -> new UserNotFoundException("there is no user with this username"));
        return generateTokenDto(user);
    }

    @Transactional
    public TokenDto register(AuthDto signUpDto) {
        User user = new User(signUpDto.getUsername(), passwordEncoder.encode(signUpDto.getPassword()));
        user.setRole("USER");
        if (userService.existByUsername(user.getUsername())) {
            throw new UserAlreadyExist("Username is taken");
        }
        userRepository.save(user);
        return generateTokenDto(user);
        // TODO убрать user id? я его на фронте нигде не использую

    }

    @Transactional
    public void logout(String refreshTokenString) {
        if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));
        } else {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
        /* TODO переделать. что должно произойти вообще если на логоут приходит токен
            который не сущестует в бд?
        * */
    }

    @Transactional
    public void logoutAll(String refreshTokenString) {
        if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            refreshTokenRepository.deleteByUserId(Long.valueOf(jwtHelper.getUserIdFromRefreshToken(refreshTokenString)));
        } else {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
    }

    @Transactional
    public TokenDto generateAccessToken(TokenDto tokenDto) {
        String refreshTokenString = tokenDto.getRefreshToken();
        if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {

            UserDto user = userService.findById(Long.valueOf(jwtHelper.getUserIdFromRefreshToken(refreshTokenString)));
            String accessToken = jwtHelper.generateAccessToken(user.getId());
            return new TokenDto(accessToken, refreshTokenString);
        } else {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
    }

    @Transactional
    public TokenDto generateRefreshToken(TokenDto tokenDto) {
        String refreshTokenString = tokenDto.getRefreshToken();
        if (refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));
            UserDto userDto = userService.findById(Long.valueOf(jwtHelper.getUserIdFromRefreshToken(refreshTokenString)));
            User user = UserMapper.INSTANCE.toEntity(userDto);
            return generateTokenDto(user);
        } else {
            throw new InvalidRefreshTokenException("Invalid refresh token");
            // TODO надо вернуть другую ошибку RefreshTokenNotExist
        }

    }


    private TokenDto generateTokenDto(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);
        String accessToken = jwtHelper.generateAccessToken(user.getId());
        String refreshTokenString = jwtHelper.generateRefreshToken(user.getId(), refreshToken.getId());
        return new TokenDto(accessToken, refreshTokenString);
    }

}
