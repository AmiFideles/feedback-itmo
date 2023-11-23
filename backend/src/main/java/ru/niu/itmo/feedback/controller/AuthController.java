package ru.niu.itmo.feedback.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.niu.itmo.feedback.dto.request.AuthDto;
import ru.niu.itmo.feedback.dto.request.RefreshTokenDto;
import ru.niu.itmo.feedback.dto.response.TokenDto;
import ru.niu.itmo.feedback.service.AuthenticationService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticateService;

    @ApiResponse(responseCode = "401", description = "unauthorized")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDto signUpDto) {
        return ResponseEntity.ok(authenticateService.authenticate(signUpDto));
    }

    @ApiResponse(responseCode = "409", description = "user already exist")
    @PostMapping("/register")
    public ResponseEntity<?> signup(@Valid @RequestBody AuthDto signUpDto) {
        return ResponseEntity.ok(authenticateService.register(signUpDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenDto tokenDto) {
        authenticateService.logout(tokenDto.getRefreshToken());
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/logout-all")
    public ResponseEntity<?> logoutAll(@RequestBody TokenDto tokenDto) {
        authenticateService.logoutAll(tokenDto.getRefreshToken());
        return ResponseEntity.noContent().build();

    }

    @ApiResponse(responseCode = "401", description = "unauthorized")
    @PostMapping("/access-token")
    public ResponseEntity<?> accessToken(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(authenticateService.generateAccessToken(tokenDto));

    }

    @ApiResponse(responseCode = "401", description = "unauthorized")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(authenticateService.generateRefreshToken(tokenDto));
    }

}
