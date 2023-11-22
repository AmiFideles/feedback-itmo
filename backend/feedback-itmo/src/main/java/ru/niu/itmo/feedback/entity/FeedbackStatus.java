package ru.niu.itmo.feedback.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author amifideles
 */
@Getter
@RequiredArgsConstructor
public enum FeedbackStatus {
    NOT_MODERATED("Не просмотрен"),
    APPROVED("Одобрен"),
    REJECTED("Отклонен");

    private final String displayName;

    }
