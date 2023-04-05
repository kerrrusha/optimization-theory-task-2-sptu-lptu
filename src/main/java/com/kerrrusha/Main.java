package com.kerrrusha;

import com.kerrrusha.config.ConfigReader;
import com.kerrrusha.generating.PossibleAnswersCreator;
import com.kerrrusha.generating.TaskGenerator;
import com.kerrrusha.generating.TaskToMoodleXmlConverter;
import com.kerrrusha.model.MoodlePossibleAnswer;
import com.kerrrusha.model.Task;

import java.util.List;

import static com.kerrrusha.util.ConverterUtil.toInt;

public class Main {

    public static void main(String[] args) {
        int AMOUNT_OF_QUESTIONS_TO_GENERATE = toInt(new ConfigReader().getProperty("AMOUNT_OF_QUESTIONS_TO_GENERATE"));

        List<Task> tasks = new TaskGenerator().generateAmountOfTasks(AMOUNT_OF_QUESTIONS_TO_GENERATE);

        StringBuilder questionsXml = new StringBuilder();
        for (Task task : tasks) {
            List<MoodlePossibleAnswer> possibleAnswerList = new PossibleAnswersCreator(task).create();
            questionsXml.append(new TaskToMoodleXmlConverter(task, possibleAnswerList).getMoodleXmlQuestion()).append(System.lineSeparator());
        }

        TaskToMoodleXmlConverter.createMoodleXmlFile(questionsXml.toString());
    }

}
