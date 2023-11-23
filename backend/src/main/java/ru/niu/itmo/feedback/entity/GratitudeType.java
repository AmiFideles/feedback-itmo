package ru.niu.itmo.feedback.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author amifideles
 */
@Getter
@RequiredArgsConstructor
public enum GratitudeType {
    TALENT_AND_FAITH("Спасибо за талант и веру в нас!"),
    INSPIRATION_AND_EXAMPLE("Спасибо за вдохновение и пример!"),
    IMPORTANT_ADVICE("Спасибо за самый нужный совет!"),
    HELP_AND_KNOWLEDGE("Спасибо за помощь и знания!");

    private final String displayName;

}
