package ru.niu.itmo.feedback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.niu.itmo.feedback.repository.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;


}
