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
import ru.niu.itmo.feedback.entity.Feedback;
import ru.niu.itmo.feedback.entity.FeedbackStatus;
import ru.niu.itmo.feedback.mapper.FeedbackMapper;
import ru.niu.itmo.feedback.mapper.FeedbackSpecifications;
import ru.niu.itmo.feedback.repository.FeedbackRepository;
import ru.niu.itmo.feedback.service.exceptions.FeedbackNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * @author amifideles
 */
@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;


    @Value("${upload-dir}")
    private String uploadDir;

    public Long saveFeedback(FeedbackRequestDto feedbackRequestDto) {
        Feedback feedback = FeedbackMapper.INSTANCE.fromRequestDtoToEntity(feedbackRequestDto);
        feedback.setStatus(FeedbackStatus.NOT_MODERATED);
        feedback.setDateTime(LocalDateTime.now());
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

    // TODO Обработка ошибок
    public void savePhoto(MultipartFile file, Long feedbackId) {
        Path uploadPath = Path.of(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String fileName = RandomStringUtils.randomAlphanumeric(8) + "_" + Objects.requireNonNull(file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Feedback feedback = feedbackRepository.findById(feedbackId).orElse(null);
        if (feedback != null) {
            feedback.setPhotoUrl(fileName);
            feedbackRepository.save(feedback);
        }else{
            throw new FeedbackNotFoundException("Feedback not found with ID: " + feedbackId);
        }
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

    public Page<FeedbackResponseDto> getFilteredAndApprovedFeedback(Integer graduationYear, String faculty, Pageable pageable, int seed) {
        if (graduationYear != null && graduationYear < 0 && faculty == null) {
            return findWithoutBothConditions(seed, pageable);
        } else if (graduationYear != null && faculty == null) {
            return findWithGraduationYear(graduationYear, seed, pageable);
        } else if (graduationYear == null && faculty != null) {
            return findWithFaculty(faculty, seed, pageable);
        } else if (graduationYear != null && graduationYear < 0 && faculty != null) {
            return findWithFaculty(faculty, seed, pageable);
        } else if (graduationYear != null && faculty != null) {
            return findWithBothConditions(graduationYear, faculty, seed, pageable);
        } else {
            return findWithoutBothConditions(seed, pageable);
        }
    }


    private Page<FeedbackResponseDto> findWithGraduationYear(Integer graduationYear, int seed, Pageable pageable) {
        Page<Feedback> feedbackPage = feedbackRepository.findWithGraduationYear(graduationYear, seed, pageable);
        return feedbackPage.map(FeedbackMapper.INSTANCE::toResponseDto);
    }

    private Page<FeedbackResponseDto> findWithFaculty(String faculty, int seed, Pageable pageable) {
        Page<Feedback> feedbackPage = feedbackRepository.findWithFaculty(faculty, seed, pageable);
        return feedbackPage.map(FeedbackMapper.INSTANCE::toResponseDto);
    }

    private Page<FeedbackResponseDto> findWithBothConditions(Integer graduationYear, String faculty, int seed, Pageable pageable) {
        Page<Feedback> feedbackPage = feedbackRepository.findWithBothConditions(graduationYear, faculty, seed, pageable);
        return feedbackPage.map(FeedbackMapper.INSTANCE::toResponseDto);
    }

    private Page<FeedbackResponseDto> findWithoutBothConditions(int seed, Pageable pageable) {
        Page<Feedback> feedbackPage = feedbackRepository.findWithoutBothConditions(seed, pageable);
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
