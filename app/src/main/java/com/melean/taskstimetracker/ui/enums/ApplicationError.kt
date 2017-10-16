package com.melean.taskstimetracker.ui.enums


enum class ApplicationError private constructor(val code: Int, val description: String) {
    NOT_FULL_SELECTION(0, "User did not select all required values"),
    NOT_PERMITTED_WHILE_RECORDING(1, "User trying action that is not permitted while recording"),
    PERMITTED_ONLY_WHILE_RECORDING(2, "User trying action that is permitted only while recording")
}