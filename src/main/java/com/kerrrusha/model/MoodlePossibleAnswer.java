package com.kerrrusha.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MoodlePossibleAnswer {

    private final int id;
    private final String answer;
    private final String possibleAnswerGroup;

}
