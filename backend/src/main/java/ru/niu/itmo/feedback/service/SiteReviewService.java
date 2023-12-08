package ru.niu.itmo.feedback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.niu.itmo.feedback.dto.SiteReviewDto;
import ru.niu.itmo.feedback.entity.SiteReview;
import ru.niu.itmo.feedback.mapper.SiteReviewMapper;
import ru.niu.itmo.feedback.repository.SiteReviewRepository;

/**
 * @author amifideles
 */
@Service
@RequiredArgsConstructor
public class SiteReviewService {
    private final SiteReviewRepository siteReviewRepository;


    public void addSiteReview(SiteReviewDto siteReviewDto) {
        SiteReview siteReview = SiteReviewMapper.INSTANCE.toEntity(siteReviewDto);
        siteReviewRepository.save(siteReview);
    }

    public Page<SiteReviewDto> getAllSiteReviews(Pageable pageable) {
        Page<SiteReview> all = siteReviewRepository.findAll(pageable);
        return all.map(SiteReviewMapper.INSTANCE::toDto);
    }
}
