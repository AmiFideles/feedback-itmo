package ru.niu.itmo.feedback.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.niu.itmo.feedback.dto.request.FeedbackRequestDto;
import ru.niu.itmo.feedback.dto.response.FeedbackResponseDto;
import ru.niu.itmo.feedback.entity.Color;
import ru.niu.itmo.feedback.entity.Feedback;
import ru.niu.itmo.feedback.entity.FeedbackStatus;
import ru.niu.itmo.feedback.mapper.FeedbackMapper;
import ru.niu.itmo.feedback.mapper.FeedbackSpecifications;
import ru.niu.itmo.feedback.repository.ColorRepository;
import ru.niu.itmo.feedback.repository.FeedbackRepository;
import ru.niu.itmo.feedback.service.exceptions.ColorNotFountException;
import ru.niu.itmo.feedback.service.exceptions.FeedbackNotFoundException;
import ru.niu.itmo.feedback.service.exceptions.FileStorageException;
import ru.niu.itmo.feedback.service.exceptions.PhotoAlreadyExistsException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;

/**
 * @author amifideles
 */
@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private ColorRepository colorRepository;

    @Value("${upload-dir}")
    private String uploadDir;

    @Transactional
    public Long saveFeedback(FeedbackRequestDto feedbackRequestDto) {
        Color color1 = colorRepository.findById(feedbackRequestDto.getColor()).orElseThrow(() -> new ColorNotFountException("Color not found with id " + feedbackRequestDto.getColor()));
        Feedback feedback = FeedbackMapper.INSTANCE.fromRequestDtoToEntity(feedbackRequestDto);
        feedback.setColor(color1);
        feedback.setStatus(FeedbackStatus.NOT_MODERATED);
        feedback.setDateTime(LocalDateTime.now(ZoneOffset.UTC));
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return savedFeedback.getId();
    }

    @Transactional
    public Long updateFeedbackStatus(long feedbackId, FeedbackStatus feedbackStatus) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElse(null);
        if (feedback != null) {
            feedback.setStatus(feedbackStatus);
            feedbackRepository.save(feedback);
            return feedback.getId();
        }
        return null;
    }

    public void savePhoto(MultipartFile file, Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new FeedbackNotFoundException("Feedback not found with ID: " + feedbackId));
        if (feedback.getPhotoUrl() != null) {
            throw new PhotoAlreadyExistsException("Feedback with ID " + feedbackId + " already has a photo.");
        }
        Path uploadPath = Path.of(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new FileStorageException("Failed to create upload directory", e);
            }
        }
        String fileName = RandomStringUtils.randomAlphanumeric(8) + "_" + Objects.requireNonNull(file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Failed to save photo", e);
        }
        feedback.setPhotoUrl(fileName);
        feedbackRepository.save(feedback);
    }

    public FeedbackResponseDto getFeedbackById(Long feedbackId) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackId);
        return feedbackOptional.map(FeedbackMapper.INSTANCE::toResponseDto)
                .orElseThrow(() -> new FeedbackNotFoundException("Feedback not found with ID: " + feedbackId));
    }

    public Page<FeedbackResponseDto> getNotModeratedFeedback(Pageable pageable) {
        Page<Feedback> feedbackPage = feedbackRepository.findByStatusOrderByDateTimeDesc(FeedbackStatus.NOT_MODERATED, pageable);
        return feedbackPage.map(FeedbackMapper.INSTANCE::toResponseDto);
    }

    public Page<FeedbackResponseDto> getAll(FeedbackStatus feedbackStatus, Pageable pageable) {
        Page<Feedback> feedbackPage = feedbackRepository.findByStatusOrderByDateTimeDesc(feedbackStatus, pageable);
        return feedbackPage.map(FeedbackMapper.INSTANCE::toResponseDto);
    }

    public Long getTotalFeedbackCount() {
        return feedbackRepository.count();
    }

    public Page<FeedbackResponseDto> getAllFeedback(Pageable pageable) {
        Page<Feedback> all = feedbackRepository.findAll(pageable);
        return all.map(FeedbackMapper.INSTANCE::toResponseDto);
    }

    public Page<FeedbackResponseDto> getFeedback(String faculty, Integer graduationYear, Integer seed, Pageable pageable) {
        Specification<Feedback> specification = FeedbackSpecifications.buildSpecification(faculty, graduationYear, seed);

        Page<Feedback> resultPage = feedbackRepository.findAll(specification, pageable);
        return resultPage.map(FeedbackMapper.INSTANCE::toResponseDto);
    }
}
