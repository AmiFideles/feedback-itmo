package ru.niu.itmo.feedback.mapper;

import org.mapstruct.Mapper;
import ru.niu.itmo.feedback.dto.response.ColorResponseDto;
import ru.niu.itmo.feedback.entity.Color;

/**
 * @author amifideles
 */
@Mapper
public interface ColorMapper {
    ColorResponseDto toDto(Color color);
    Color toEntity(ColorResponseDto colorDto);
    default Integer map(Color color) {
        return color != null ? color.getId() : null;
    }
}
