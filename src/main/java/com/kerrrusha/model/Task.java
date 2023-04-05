package com.kerrrusha.model;

import com.kerrrusha.solving.TaskSolver;
import lombok.Getter;

@Getter
public class Task {

    private static int objectsCreated = 0;

    private final int id;
    private final TaskCondition taskCondition;
    private final TaskAnswer taskAnswer;

    public Task(TaskCondition taskCondition) {
        this.id = ++objectsCreated;
        this.taskCondition = taskCondition;
        this.taskAnswer = new TaskSolver(this).solve();
        taskCondition.updatePossibleAltAnswerCounts(taskAnswer.getAltAnswersCount());
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskCondition=" + taskCondition +
                ", taskAnswer=" + taskAnswer +
                '}';
    }
}
