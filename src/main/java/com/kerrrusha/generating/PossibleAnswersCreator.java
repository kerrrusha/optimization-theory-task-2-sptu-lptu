package com.kerrrusha.generating;

import com.kerrrusha.model.MoodlePossibleAnswer;
import com.kerrrusha.model.ScheduleElement;
import com.kerrrusha.model.Task;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PossibleAnswersCreator {

    private final Task task;

    private int answersCounter = 0;

    public List<MoodlePossibleAnswer> create() {
        List<MoodlePossibleAnswer> possibleAnswerList = new ArrayList<>();

        possibleAnswerList.add(createSingle("(", "1"));
        possibleAnswerList.add(createSingle(")", "1"));

        List<ScheduleElement> scheduleElements = task.getTaskCondition().getInputSchedule();
        for (ScheduleElement scheduleElement : scheduleElements) {
            possibleAnswerList.add(createSingle(scheduleElement.toShortString(), "1"));
        }

        List<Integer> possibleAltAnswerCounts = task.getTaskCondition().getPossibleAltAnswerCounts();
        for (Integer possibleAltAnswerCount : possibleAltAnswerCounts) {
            possibleAnswerList.add(createSingle(possibleAltAnswerCount + "", "2"));
        }

        return possibleAnswerList;
    }

    private MoodlePossibleAnswer createSingle(String answer, String group) {
        return new MoodlePossibleAnswer(++answersCounter, answer, group);
    }

}
