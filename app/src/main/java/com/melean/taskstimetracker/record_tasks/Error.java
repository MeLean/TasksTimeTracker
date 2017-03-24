package com.melean.taskstimetracker.record_tasks;


public enum Error {
    NOT_FULL_SELECTION(0, "User did not select all required values"),
    NOT_PERMITTED_WHILE_RECORDING(1, "User trying action that is not permitted while recording"),
    PERMITTED_ONLY_WHILE_RECORDING(2, "User trying action that is permitted only while recording");


    private final int code;
    private final String description;

    private Error(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}