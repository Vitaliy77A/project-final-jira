package com.javarush.jira.bugtracking.task;

import com.javarush.jira.BaseTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest extends BaseTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    /*
     * NOTE: The test verifies correct time calculation (it discards old dates).
     *
     * How to run locally:
     * The activity test inserts (IDs 1001, 1002, 1003) must be placed at the end of the data.sql file.
     * If they are placed in changelog.sql, the test will fail with a Foreign Key error (USERS not found),
     * because Liquibase executes before data.sql.
     *
     * For the JavaRush validator:
     * Per the task requirements, these inserts MUST be moved to the very bottom of changelog.sql!
     */
    @Test
    void calculateTimeInProgressAndTesting() {

        Task task = taskRepository.findById(1L).orElseThrow();

        Duration timeInProgress = taskService.calculateTimeInProgress(task);
        Duration timeInTesting = taskService.calculateTimeInTesting(task);

        System.out.println("Development time : " + timeInProgress.toHours() + " hours");
        System.out.println("Time spent testing: " + timeInTesting.toMinutes() + " minutes");

        assertEquals(4, timeInProgress.toHours(), "Time in_progress calculated incorrectly");

        assertEquals(150, timeInTesting.toMinutes(), "The testing time has been calculated incorrectly.");
    }

}