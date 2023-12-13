package ru.niu.itmo.feedback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.niu.itmo.feedback.dto.request.MessageDto;
import ru.niu.itmo.feedback.dto.response.FeedbackResponseDto;
import ru.niu.itmo.feedback.entity.FeedbackStatus;
import ru.niu.itmo.feedback.service.FeedbackService;

import static ru.niu.itmo.feedback.configuration.SwaggerConfiguration.BEARER_KEY_SECURITY_SCHEME;

/**
 * @author amifideles
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moderator")
public class ModeratorController {
    private final FeedbackService feedbackService;

    @ApiResponse(responseCode = "401", description = "unauthorized")
    @ApiResponse(responseCode = "403", description = "access is denied")
    @Operation(
            summary = "Update feedback status",
            description = "Update the status of a feedback entry by providing its ID and the new status",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}
    )

    @ApiResponse(responseCode = "401", description = "unauthorized")
    @ApiResponse(responseCode = "403", description = "access is denied")
    @PutMapping("/status/{feedbackId}")
    public ResponseEntity<Void> updateFeedbackStatus(
            @PathVariable Long feedbackId,
            @RequestParam FeedbackStatus feedbackStatus) {
        Long updatedFeedbackId = feedbackService.updateFeedbackStatus(feedbackId, feedbackStatus);

        if (updatedFeedbackId != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiResponse(responseCode = "401", description = "unauthorized")
    @ApiResponse(responseCode = "403", description = "access is denied")
    @GetMapping("/all")
    @Operation(
            summary = "Get all feedback",
            description = "Get a paginated and sorted page of all feedback.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    public ResponseEntity<Page<FeedbackResponseDto>> getAllFeedback(
            @PageableDefault(page = 0, size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FeedbackResponseDto> feedbackPage = feedbackService.getAllFeedback(pageable);
        return ResponseEntity.ok(feedbackPage);
    }

    @ApiResponse(responseCode = "401", description = "unauthorized")
    @ApiResponse(responseCode = "403", description = "access is denied")
    @Operation(
            summary = "Get not moderated feedback",
            description = "Retrieve a page of feedback entries that are not yet moderated.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}
    )
    @GetMapping("/notModerated")
    public ResponseEntity<Page<FeedbackResponseDto>> getNotModeratedFeedback(
            @PageableDefault(page = 0, size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FeedbackResponseDto> feedbackPage = feedbackService.getNotModeratedFeedback(pageable);
        return ResponseEntity.ok(feedbackPage);
    }


//    @ApiResponse(responseCode = "401", description = "unauthorized")
//    @ApiResponse(responseCode = "403", description = "access is denied")
//    @Operation(
//            summary = "Get all feedback by status",
//            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
//    @GetMapping("/all")
//    public ResponseEntity<Page<FeedbackResponseDto>> getAll(@RequestParam FeedbackStatus status,
//                                                            @PageableDefault(page = 0, size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<FeedbackResponseDto> feedbackPage = feedbackService.getAll(status, pageable);
//        return ResponseEntity.ok(feedbackPage);
//    }


    @ApiResponse(responseCode = "401", description = "unauthorized")
    @ApiResponse(responseCode = "403", description = "access is denied")
    @Operation(
            summary = "Update message text by id",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}
    )
    @PutMapping("/messageText/{feedbackId}")
    public ResponseEntity<Void> updateFeedback(
            @PathVariable Long feedbackId,
            @RequestBody MessageDto messageDto) {
        feedbackService.updateFeedbackMessageText(feedbackId, messageDto);
        return ResponseEntity.ok().build();
    }


}
