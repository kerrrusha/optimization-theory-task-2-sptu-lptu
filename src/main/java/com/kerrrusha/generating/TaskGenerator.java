package com.kerrrusha.generating;

import com.kerrrusha.config.ConfigReader;
import com.kerrrusha.model.ScheduleElement;
import com.kerrrusha.model.ScheduleType;
import com.kerrrusha.model.Task;
import com.kerrrusha.model.TaskCondition;

import java.util.*;

import static com.kerrrusha.util.ConverterUtil.toInt;
import static com.kerrrusha.util.TaskUtil.getElementEntriesCount;
import static com.kerrrusha.util.TaskUtil.getRandomInt;
import static java.util.stream.Collectors.toList;

public class TaskGenerator {

    private final int EXPECTED_TIME_VALUE_DUPLICATE_GROUPS_AMOUNT;

    private final int AMOUNT_OF_POSSIBLE_ALT_ANSWER_COUNTS;
    private final int ALT_ANSWER_COUNT_FROM_INCLUSIVE;
    private final int ALT_ANSWER_COUNT_TO_EXCLUSIVE;

    private final int N_FROM_INCLUSIVE;
    private final int N_TO_EXCLUSIVE;

    private final int M_DEFAULT_VALUE;

    private final int T_FROM_INCLUSIVE;
    private final int T_TO_INCLUSIVE;

    private final int D_FROM_INCLUSIVE;
    private final int D_TO_EXCLUSIVE;

    private final int U_FROM_INCLUSIVE;
    private final int U_TO_INCLUSIVE;

    public TaskGenerator() {
        ConfigReader configReader = new ConfigReader();

        EXPECTED_TIME_VALUE_DUPLICATE_GROUPS_AMOUNT = toInt(configReader.getProperty("EXPECTED_TIME_VALUE_DUPLICATE_GROUPS_AMOUNT"));
        AMOUNT_OF_POSSIBLE_ALT_ANSWER_COUNTS = toInt(configReader.getProperty("AMOUNT_OF_POSSIBLE_ALT_ANSWER_COUNTS"));
        ALT_ANSWER_COUNT_FROM_INCLUSIVE = toInt(configReader.getProperty("ALT_ANSWER_COUNT_FROM_INCLUSIVE"));
        ALT_ANSWER_COUNT_TO_EXCLUSIVE = toInt(configReader.getProperty("ALT_ANSWER_COUNT_TO_EXCLUSIVE"));
        N_FROM_INCLUSIVE = toInt(configReader.getProperty("N_FROM_INCLUSIVE"));
        N_TO_EXCLUSIVE = toInt(configReader.getProperty("N_TO_EXCLUSIVE"));
        M_DEFAULT_VALUE = toInt(configReader.getProperty("M_DEFAULT_VALUE"));
        T_FROM_INCLUSIVE = toInt(configReader.getProperty("T_FROM_INCLUSIVE"));
        T_TO_INCLUSIVE = toInt(configReader.getProperty("T_TO_INCLUSIVE"));
        D_FROM_INCLUSIVE = toInt(configReader.getProperty("D_FROM_INCLUSIVE"));
        D_TO_EXCLUSIVE = toInt(configReader.getProperty("D_TO_EXCLUSIVE"));
        U_FROM_INCLUSIVE = toInt(configReader.getProperty("U_FROM_INCLUSIVE"));
        U_TO_INCLUSIVE = toInt(configReader.getProperty("U_TO_INCLUSIVE"));
    }

    public Task generateSingleTask() {
       final int n = getRandomInt(N_FROM_INCLUSIVE, N_TO_EXCLUSIVE);

        ScheduleType scheduleType = getRandomScheduleType();
        List<ScheduleElement> schedule = generateSchedule(n);
        List<Integer> possibleAltAnswerCountsExceptCorrectOne = generatePossibleAltAnswerCountsExceptCorrectOne();

        TaskCondition taskCondition = new TaskCondition(n, M_DEFAULT_VALUE, scheduleType, schedule, possibleAltAnswerCountsExceptCorrectOne);
        return new Task(taskCondition);
    }

    private List<Integer> generatePossibleAltAnswerCountsExceptCorrectOne() {
        Set<Integer> result = new HashSet<>();
        for (int i = 1; i < AMOUNT_OF_POSSIBLE_ALT_ANSWER_COUNTS; i++) {
            result.add(getRandomInt(ALT_ANSWER_COUNT_FROM_INCLUSIVE, ALT_ANSWER_COUNT_TO_EXCLUSIVE));
            if (result.size() != i) {
                i -= 1;
            }
        }
        return new ArrayList<>(result);
    }

    private List<ScheduleElement> generateSchedule(int n) {
        List<ScheduleElement> result = new ArrayList<>();
        do {
            result.clear();
            for (int i = 1; i <= n; i++) {
                int t = getRandomInt(T_FROM_INCLUSIVE, T_TO_INCLUSIVE);
                if (getTimeValueDuplicateGroupsAmount(result, t) > EXPECTED_TIME_VALUE_DUPLICATE_GROUPS_AMOUNT) {
                    i -= 1;
                    continue;
                }
                int d = getRandomInt(D_FROM_INCLUSIVE, D_TO_EXCLUSIVE);
                int u = getRandomInt(U_FROM_INCLUSIVE, U_TO_INCLUSIVE);
                result.add(new ScheduleElement(i, t, d, u));
            }
        } while (getTimeValueDuplicateGroupsAmount(result) != EXPECTED_TIME_VALUE_DUPLICATE_GROUPS_AMOUNT);
        return result;
    }

    private ScheduleType getRandomScheduleType() {
        ScheduleType[] scheduleTypes = ScheduleType.values();
        int randIndex = getRandomInt(0, scheduleTypes.length);
        return scheduleTypes[randIndex];
    }

    private int getTimeValueDuplicateGroupsAmount(List<ScheduleElement> schedule) {
        List<Integer> timeValues = schedule
                .stream()
                .map(ScheduleElement::getT)
                .collect(toList());
        return (int) timeValues
                .stream()
                .distinct()
                .filter(t -> getElementEntriesCount(timeValues, t) > 1)
                .count();
    }

    private int getTimeValueDuplicateGroupsAmount(List<ScheduleElement> schedule, int newT) {
        List<Integer> timeValues = schedule
                .stream()
                .map(ScheduleElement::getT)
                .collect(toList());
        timeValues.add(newT);
        return (int) timeValues
                .stream()
                .distinct()
                .filter(t -> getElementEntriesCount(timeValues, t) > 1)
                .count();
    }

    public List<Task> generateAmountOfTasks(int amountOfTasksToBeGenerated) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < amountOfTasksToBeGenerated; i++) {
            tasks.add(generateSingleTask());
        }
        return tasks;
    }

}
