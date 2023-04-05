package com.kerrrusha.util;

import com.kerrrusha.model.ScheduleElement;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public class TaskUtil {

    public static int getRandomInt(int fromInclusive, int toExclusive) {
        return new Random().nextInt(toExclusive - fromInclusive) + fromInclusive;
    }

    public static int factorial(int n) {
        return (n == 0)
                ? 1
                : (n * factorial(n - 1));
    }

    public static <R> int getElementEntriesCount(List<R> list, R element) {
        return (int) list
                .stream()
                .filter(e -> Objects.equals(e, element))
                .count();
    }

    public static int getDuplicatingTimeValue(List<ScheduleElement> list) {
        List<Integer> timeValues = list
                .stream()
                .map(ScheduleElement::getT)
                .collect(toList());
        return timeValues
                .stream()
                .filter(e -> getElementEntriesCount(timeValues, e) > 1)
                .findFirst().orElse(Integer.MIN_VALUE);
    }

}
