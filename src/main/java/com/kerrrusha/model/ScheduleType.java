package com.kerrrusha.model;

import lombok.Getter;

import static com.kerrrusha.model.MinOrMax.MAX;
import static com.kerrrusha.model.MinOrMax.MIN;

@Getter
public enum ScheduleType {

    MAX_T(MAX, "максимальним сумарним часом закінчення", "T"),
    MAX_W(MAX, "максимальним сумарним очікуванням", "W"),
    MAX_L(MAX, "максимальним сумарним часовим зміщення", "L"),
    MAX_F(MAX, "максимальною сумарною тривалістю проходження", "F"),

    MIN_T(MIN, "мінімальним сумарним часом закінчення", "T"),
    MIN_W(MIN, "мінімальним сумарним очікуванням", "W"),
    MIN_L(MIN, "мінімальним сумарним часовим зміщенням", "L"),
    MIN_F(MIN, "мінімальною сумарною тривалістю проходження", "F");

    private final MinOrMax type;
    private final String criterion;
    private final String shortCriterion;

    ScheduleType(MinOrMax type, String criterion, String shortCriterion) {
        this.type = type;
        this.criterion = criterion;
        this.shortCriterion = shortCriterion;
    }

}
