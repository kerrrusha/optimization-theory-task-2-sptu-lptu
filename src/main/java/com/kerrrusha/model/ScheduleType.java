package com.kerrrusha.model;

import lombok.Getter;

import static com.kerrrusha.model.MinOrMax.MAX;
import static com.kerrrusha.model.MinOrMax.MIN;

@Getter
public enum ScheduleType {

    MAX_T(MAX, "максимальним сумарним зваженим часом закінчення", "T"),
    MAX_W(MAX, "максимальним сумарним зваженим очікуванням", "W"),
    MAX_L(MAX, "максимальним сумарним зваженим часовим зміщення", "L"),
    MAX_F(MAX, "максимальною сумарною зваженою тривалістю проходження", "F"),

    MIN_T(MIN, "мінімальним сумарним зваженим часом закінчення", "T"),
    MIN_W(MIN, "мінімальним сумарним зваженим очікуванням", "W"),
    MIN_L(MIN, "мінімальним сумарним зваженим часовим зміщенням", "L"),
    MIN_F(MIN, "мінімальною сумарною зваженою тривалістю проходження", "F");

    private final MinOrMax type;
    private final String criterion;
    private final String shortCriterion;

    ScheduleType(MinOrMax type, String criterion, String shortCriterion) {
        this.type = type;
        this.criterion = criterion;
        this.shortCriterion = shortCriterion;
    }

}
