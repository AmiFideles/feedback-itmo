package ru.niu.itmo.feedback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.niu.itmo.feedback.dto.request.FeedbackRequestDto;
import ru.niu.itmo.feedback.dto.response.ErrorResponseDto;
import ru.niu.itmo.feedback.dto.response.FeedbackResponseDto;
import ru.niu.itmo.feedback.entity.FeedbackStatus;
import ru.niu.itmo.feedback.service.FeedbackService;
import ru.niu.itmo.feedback.service.SeedService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author amifideles
 */
@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final SeedService seedService;

    @PostMapping("/add")
    @Operation(summary = "Submit new feedback", description = "Submit a new feedback and get its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully submitted feedback and received its ID", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<Long> submitFeedback(@Valid @RequestBody FeedbackRequestDto feedbackRequestDto) {
        Long feedbackId = feedbackService.saveFeedback(feedbackRequestDto);
        return ResponseEntity.ok(feedbackId);
    }

    @PostMapping("/photo/{feedbackId}")
    @ApiResponse(responseCode = "404", description = "Feedback not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    @Operation(summary = "Submit photo for feedback", description = "Submit a photo for an existing feedback")
    public ResponseEntity<Void> submitPhoto(@RequestPart MultipartFile file, @PathVariable Long feedbackId) {
        feedbackService.savePhoto(file, feedbackId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{feedbackId}")
    @Operation(summary = "Get feedback by ID", description = "Get feedback details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved feedback.", content = @Content(schema = @Schema(implementation = FeedbackResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Feedback not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<FeedbackResponseDto> getFeedbackById(@PathVariable Long feedbackId) {
        FeedbackResponseDto feedbackResponseDto = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.ok(feedbackResponseDto);
    }

    @GetMapping("/approved")
    @Operation(summary = "Get a paginated and sorted page of filtered and approved feedback.", description = "This endpoint returns a paginated and sorted page of feedback with optional filtering by graduation year and faculty.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved feedback page.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FeedbackResponseDto.class)))),})
    public ResponseEntity<Page<FeedbackResponseDto>> getFilteredAndApprovedFeedback(@Parameter(description = "Graduation year for filtering.") @RequestParam(required = false) Integer graduationYear, @Parameter(description = "Faculty for filtering.") @RequestParam(required = false) String faculty, @PageableDefault(page = 0, size = 10) Pageable pageable, HttpServletResponse response, HttpServletRequest request) {
        Sort sort = pageable.getSort();

        if (sort.isSorted()) {
            for (Sort.Order order : sort) {
                if ("dateTime".equals(order.getProperty())) {
                    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "dateTime"));
                }
            }
        }

        if (sort == Sort.unsorted()) {
            Optional<String> cookie = readCookie(request.getCookies());
            int seed = 0;
            if (cookie.isEmpty()) {
                seed = seedService.getRandomSeed();
                Cookie cookie1 = new Cookie("seed", String.valueOf(seed));
                cookie1.setMaxAge(120 * 10);
                response.addCookie(cookie1);
            } else {
                seed = Integer.parseInt(cookie.get());
            }
            Page<FeedbackResponseDto> feedbackPage = feedbackService.getFeedback(faculty, graduationYear, seed, pageable);
            return ResponseEntity.ok(feedbackPage);
        } else {
            Page<FeedbackResponseDto> feedbackPage = feedbackService.getSortedFeedbackByStatus(FeedbackStatus.APPROVED, pageable);
            return ResponseEntity.ok(feedbackPage);
        }
    }

    private Optional<String> readCookie(Cookie[] cookies) {
        if (cookies == null) return Optional.empty();
        return Arrays.stream(cookies).filter(c -> "seed".equals(c.getName())).map(Cookie::getValue).findAny();
    }

    @GetMapping("/count")
    @Operation(summary = "Get the total count of feedback.", description = "This endpoint returns the total count of feedback in the system.")
    public ResponseEntity<Long> getTotalFeedbackCount() {
        Long totalFeedbackCount = feedbackService.getTotalFeedbackCount();
        return ResponseEntity.ok(totalFeedbackCount);
    }
}