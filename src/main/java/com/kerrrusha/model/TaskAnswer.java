package com.kerrrusha.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class TaskAnswer {

    private final List<ScheduleElement> schedule;
    private final int altAnswersCount;

    @Override
    public String toString() {
        return "TaskAnswer{" +
                "schedule=" + schedule +
                ", altAnswersCount=" + altAnswersCount +
                '}';
    }

}
