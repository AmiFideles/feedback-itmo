package ru.niu.itmo.feedback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.niu.itmo.feedback.dto.SiteReviewDto;
import ru.niu.itmo.feedback.entity.SiteReview;

/**
 * @author amifideles
 */
@Mapper
public interface SiteReviewMapper {
    SiteReviewMapper INSTANCE = Mappers.getMapper(SiteReviewMapper.class);
    SiteReview toEntity(SiteReviewDto siteReviewDto);

    SiteReviewDto toDto(SiteReview siteReviewDto);
}
