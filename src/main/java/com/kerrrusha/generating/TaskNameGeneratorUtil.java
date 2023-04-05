package com.kerrrusha.generating;

import com.kerrrusha.model.ScheduleType;
import com.kerrrusha.model.Task;

import static com.kerrrusha.model.MinOrMax.MIN;

public class TaskNameGeneratorUtil {

    private static final String FILENAME_TEMPLATE = "Т2 %s-%s alt %s";

    public static String generateTaskName(Task task) {
        return String.format(
                FILENAME_TEMPLATE,
                decideSptOrLpt(task.getTaskCondition().getScheduleType()),
                task.getTaskCondition().getScheduleType().getShortCriterion(),
                task.getId()
        );
    }

    private static String decideSptOrLpt(ScheduleType scheduleType) {
        return scheduleType.getType().equals(MIN)
                ? "SPT"
                : "LPT";
    }

}
