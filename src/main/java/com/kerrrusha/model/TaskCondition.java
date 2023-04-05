package com.kerrrusha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class TaskCondition {

    private final int n;
    private final int m;
    private final ScheduleType scheduleType;
    private final List<ScheduleElement> inputSchedule;
    private List<Integer> possibleAltAnswerCounts;

    public void updatePossibleAltAnswerCounts(int altAnswersCount) {
        possibleAltAnswerCounts.add(altAnswersCount);
        possibleAltAnswerCounts = possibleAltAnswerCounts
                .stream()
                .distinct()
                .collect(toList());
        shuffle(possibleAltAnswerCounts);
    }

    @Override
    public String toString() {
        return "TaskCondition{" +
                "n=" + n +
                ", m=" + m +
                ", scheduleType=" + scheduleType +
                ", inputSchedule=" + inputSchedule +
                ", possibleAltAnswerCounts=" + possibleAltAnswerCounts +
                '}';
    }
}
