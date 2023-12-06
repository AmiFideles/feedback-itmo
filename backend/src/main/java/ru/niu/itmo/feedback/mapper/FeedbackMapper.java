package ru.niu.itmo.feedback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.niu.itmo.feedback.dto.request.FeedbackRequestDto;
import ru.niu.itmo.feedback.dto.response.FeedbackResponseDto;
import ru.niu.itmo.feedback.entity.Feedback;
import ru.niu.itmo.feedback.entity.FeedbackStatus;

/**
 * @author amifideles
 */

@Mapper(uses = ColorMapper.class)
public interface FeedbackMapper {
    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    Feedback fromResponseDtoToEntity(FeedbackResponseDto feedbackResponseDto);
    @Mapping(source = "feedback.color", target = "colorId")
    FeedbackResponseDto toResponseDto(Feedback feedback);

    Feedback fromRequestDtoToEntity(FeedbackRequestDto feedbackResponseDto);

    FeedbackRequestDto toRequestDto(Feedback feedback);

    default String map(FeedbackStatus status) {
        return status != null ? status.name() : null;
    }

    default FeedbackStatus map(String status) {
        return status != null ? FeedbackStatus.valueOf(status) : null;
    }
}
