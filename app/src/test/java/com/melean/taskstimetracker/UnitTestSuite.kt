package com.melean.taskstimetracker

import com.melean.taskstimetracker.tests.AddDataPresenterTests
import com.melean.taskstimetracker.tests.RecordTaskPresenterTests
import com.melean.taskstimetracker.tests.TaskRepositoryTests

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        TaskRepositoryTests::class,
        RecordTaskPresenterTests::class,
        AddDataPresenterTests::class
)
class UnitTestSuite
