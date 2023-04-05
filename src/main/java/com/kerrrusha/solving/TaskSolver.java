package com.kerrrusha.solving;

import com.kerrrusha.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static com.kerrrusha.model.MinOrMax.MAX;
import static com.kerrrusha.util.TaskUtil.factorial;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
public class TaskSolver {

    private final Task task;

    @Getter
    private List<ScheduleElement> solved;

    public TaskAnswer solve() {
        return new TaskAnswer(getSolvedSchedule(), getAltAnswersCount());
    }

    public List<ScheduleElement> getSolvedSchedule() {
        if (nonNull(solved)) {
            return solved;
        }
        if (task.getTaskCondition().getScheduleType().getType() == MAX) {
            solved = task.getTaskCondition().getInputSchedule().stream()
                    .sorted(Comparator.comparing(ScheduleElement::getI))
                    .sorted(Comparator.comparing(ScheduleElement::getTDivideU).reversed())
                    .collect(toList());
        } else {
            solved = task.getTaskCondition().getInputSchedule().stream()
                    .sorted(Comparator.comparing(ScheduleElement::getI))
                    .sorted(Comparator.comparing(ScheduleElement::getTDivideU))
                    .collect(toList());
        }
        return solved;
    }

    public int getAltAnswersCount() {
        solveIfNotSolved();
        Map<Double, Integer> tDivideUValueEntriesToCountMap = getUniqueTDivideUValues()
                .stream()
                .collect(toMap(tDivideU -> tDivideU, this::getEntriesCountForTDivideU));
        int elementsThatCauseAltAnswersCount = tDivideUValueEntriesToCountMap
                .keySet().stream()
                .filter(tDivideU -> tDivideUValueEntriesToCountMap.get(tDivideU) > 1)
                .map(tDivideUValueEntriesToCountMap::get)
                .mapToInt(Integer::valueOf)
                .sum();
        return factorial(elementsThatCauseAltAnswersCount);
    }

    private void solveIfNotSolved() {
        if (isNull(solved)) {
            solved = getSolvedSchedule();
        }
    }

    private Integer getEntriesCountForTDivideU(Double tDivideU) {
        return Math.toIntExact(solved.stream()
                .filter(e -> e.getTDivideU() == tDivideU)
                .count());
    }

    private Set<Double> getUniqueTDivideUValues() {
        return solved.stream()
                .map(ScheduleElement::getTDivideU)
                .collect(toSet());
    }

}
