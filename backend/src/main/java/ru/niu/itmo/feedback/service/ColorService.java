package ru.niu.itmo.feedback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.niu.itmo.feedback.dto.request.ColorRequestDto;
import ru.niu.itmo.feedback.entity.Color;
import ru.niu.itmo.feedback.repository.ColorRepository;

import java.util.List;

/**
 * @author amifideles
 */
@Service
@RequiredArgsConstructor
public class ColorService {
    private final ColorRepository colorRepository;

    public Color addColor(ColorRequestDto colorResponseDto) {
        Color color = new Color();
        color.setColor(colorResponseDto.getColor());
        color.setLight(colorResponseDto.getLight());
        return colorRepository.save(color);
    }

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }
}
