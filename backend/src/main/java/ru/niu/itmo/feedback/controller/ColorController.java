package ru.niu.itmo.feedback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.niu.itmo.feedback.dto.request.ColorRequestDto;
import ru.niu.itmo.feedback.entity.Color;
import ru.niu.itmo.feedback.service.ColorService;

import java.util.List;

import static ru.niu.itmo.feedback.configuration.SwaggerConfiguration.BEARER_KEY_SECURITY_SCHEME;

/**
 * @author amifideles
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/color")
public class ColorController {
    private final ColorService colorService;

    @ApiResponse(responseCode = "401", description = "unauthorized")
    @ApiResponse(responseCode = "403", description = "access is denied")
    @Operation(
            summary = "add color",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}
    )
    @PostMapping
    public ResponseEntity<Color> addColor(@RequestBody ColorRequestDto colorResponseDto) {
        colorService.addColor(colorResponseDto);
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "401", description = "unauthorized")
    @ApiResponse(responseCode = "403", description = "access is denied")
    @Operation(
            summary = "Get all colors",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}
    )
    @GetMapping
    public ResponseEntity<List<Color>> getAllColors() {
        List<Color> colors = colorService.getAllColors();
        return ResponseEntity.ok(colors);
    }
}
