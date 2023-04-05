package com.kerrrusha.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleElement {

    private final int i;
    private final int t;
    private final int d;
    private final int u;

    @Override
    public String toString() {
        return "(" +
                "№" + i +
                " / " + t +
                " / " + d +
                " / " + u +
                ')';
    }

    public String toShortString() {
        return "№" + i +
                " / " + t +
                " / " + d +
                " / " + u;
    }

}
