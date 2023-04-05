package com.kerrrusha.generating;

import com.kerrrusha.model.MoodlePossibleAnswer;
import com.kerrrusha.model.ScheduleElement;
import com.kerrrusha.model.Task;
import com.kerrrusha.util.FileReaderUtil;
import com.kerrrusha.util.FileWriterUtil;

import java.util.ArrayList;
import java.util.List;

import static com.kerrrusha.util.TaskUtil.getDuplicatingTimeValue;

public class TaskToMoodleXmlConverter {

    private static final String TASK_NAME_TAG = "{TASK_NAME}";
    private static final String N_TAG = "{N}";
    private static final String M_TAG = "{M}";
    private static final String SCHEDULE_TYPE_TAG = "{SCHEDULE_TYPE}";
    private static final String CORRECT_TASK_SCHEDULE_SCHEMA_TAG = "{CORRECT_TASK_SCHEDULE_SCHEMA}";
    private static final String CORRECT_ALT_ANSWERS_COUNT_ID_TAG = "{CORRECT_ALT_ANSWERS_COUNT_ID}";
    private static final String DRAGBOXES_TAG = "{DRAGBOXES}";
    private static final String QUESTIONS_TAG = "{QUESTIONS}";

    private static final String DRAGBOX_TEMPLATE = "\n" +
            "<dragbox>\n" +
            "      <text>{ANSWER}</text>\n" +
            "      <group>{GROUP}</group>\n" +
            "    </dragbox>";

    private static final String resultFilename = "output.xml";

    private final Task task;
    private final List<MoodlePossibleAnswer> possibleAnswerList;

    private final String taskName;
    private final String questionXmlTemplate;

    private String resultQuestionXml;

    public TaskToMoodleXmlConverter(Task task, List<MoodlePossibleAnswer> possibleAnswerList) {
        this.task = task;
        this.possibleAnswerList = possibleAnswerList;

        taskName = TaskNameGeneratorUtil.generateTaskName(task);
        questionXmlTemplate = FileReaderUtil.read("res/question-template.xml");
    }

    public static void createMoodleXmlFile(String questionsXml) {
        String outputXml = FileReaderUtil.read("res/quiz-template.xml")
                .replace(QUESTIONS_TAG, questionsXml);

        FileWriterUtil.write(resultFilename, outputXml);
    }

    public String getMoodleXmlQuestion() {
        resultQuestionXml = questionXmlTemplate;

        setTaskName();
        setN();
        setM();
        setScheduleType();
        setCorrectTaskScheduleSchema();
        setCorrectAltAnswersCountId();
        setDragboxes();

        return resultQuestionXml;
    }

    private void setDragboxes() {
        setTagValue(DRAGBOXES_TAG, createDragboxes());
    }

    private String createDragboxes() {
        StringBuilder result = new StringBuilder();

        for (MoodlePossibleAnswer possibleAnswer : possibleAnswerList) {
            result.append(createDragbox(possibleAnswer));
        }

        return result.toString();
    }

    private String createDragbox(MoodlePossibleAnswer possibleAnswer) {
        return DRAGBOX_TEMPLATE
                .replace("{ANSWER}", possibleAnswer.getAnswer())
                .replace("{GROUP}", possibleAnswer.getPossibleAnswerGroup());
    }

    private void setCorrectAltAnswersCountId() {
        setTagValue(CORRECT_ALT_ANSWERS_COUNT_ID_TAG, createCorrectAltAnswersCountIdTag());
    }

    private String createCorrectAltAnswersCountIdTag() {
        int id = possibleAnswerList
                .stream()
                .filter(e -> e.getAnswer().equals(task.getTaskAnswer().getAltAnswersCount() + ""))
                .map(MoodlePossibleAnswer::getId)
                .findFirst()
                .orElse(Integer.MIN_VALUE);
        return possibleAnswerIdToSchemaElement(id);
    }

    private void setCorrectTaskScheduleSchema() {
        setTagValue(CORRECT_TASK_SCHEDULE_SCHEMA_TAG, createCorrectTaskScheduleSchemaTag());
    }

    private String createCorrectTaskScheduleSchemaTag() {
        StringBuilder result = new StringBuilder();

        List<String> correctTaskSchedule = getCorrectTaskSchedule();

        for (String possibleAnswerText : correctTaskSchedule) {
            for (MoodlePossibleAnswer possibleAnswer : possibleAnswerList) {
                if (possibleAnswer.getAnswer().equals(possibleAnswerText)) {
                    result.append(possibleAnswerIdToSchemaElement(possibleAnswer.getId())).append(" ");
                }
            }
        }

        return result.toString();
    }

    private List<String> getCorrectTaskSchedule() {
        int duplicatingTimeValue = getDuplicatingTimeValue(task.getTaskAnswer().getSchedule());
        List<String> result = new ArrayList<>();

        List<ScheduleElement> scheduleElements = task.getTaskAnswer().getSchedule();
        for (int i = 0; i < scheduleElements.size(); i++) {
            ScheduleElement scheduleElement = scheduleElements.get(i);
            if (i == 0 && scheduleElement.getT() == duplicatingTimeValue
                    || scheduleElement.getT() == duplicatingTimeValue && scheduleElements.get(i - 1).getT() != duplicatingTimeValue) {
                result.add("(");
            }
            result.add(scheduleElement.toShortString());
            if (i == scheduleElements.size() - 1 && scheduleElement.getT() == duplicatingTimeValue
                    || scheduleElement.getT() == duplicatingTimeValue && scheduleElements.get(i + 1).getT() != duplicatingTimeValue) {
                result.add(")");
            }
        }

        return result;
    }

    private void setScheduleType() {
        setTagValue(SCHEDULE_TYPE_TAG, task.getTaskCondition().getScheduleType().getCriterion());
    }

    private void setTaskName() {
        setTagValue(TASK_NAME_TAG, taskName);
    }

    private void setN() {
        setTagValue(N_TAG, task.getTaskCondition().getN() + "");
    }

    private void setM() {
        setTagValue(M_TAG, task.getTaskCondition().getM() + "");
    }

    private void setTagValue(String tag, String value) {
        resultQuestionXml = resultQuestionXml.replace(tag, value);
    }

    private String possibleAnswerIdToSchemaElement(int id) {
        return "[[" + id + "]]";
    }

}
