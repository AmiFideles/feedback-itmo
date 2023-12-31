package ru.niu.itmo.feedback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.niu.itmo.feedback.dto.SiteReviewDto;
import ru.niu.itmo.feedback.service.SiteReviewService;

import static ru.niu.itmo.feedback.configuration.SwaggerConfiguration.BEARER_KEY_SECURITY_SCHEME;

/**
 * @author amifideles
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/site-review")
public class SiteReviewController {
    private final SiteReviewService siteReviewService;

    @Operation(summary = "send a website review")
    @PostMapping
    public ResponseEntity<?> addSiteReview(@RequestBody SiteReviewDto siteReviewDto) {
        siteReviewService.addSiteReview(siteReviewDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get websites reviews", security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public ResponseEntity<Page<SiteReviewDto>> getAllSiteReviews(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<SiteReviewDto> allSiteReviews = siteReviewService.getAllSiteReviews(pageable);
        return ResponseEntity.ok(allSiteReviews);
    }
}
