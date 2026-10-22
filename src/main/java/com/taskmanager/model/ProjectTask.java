package com.taskmanager.model;
import com.taskmanager.enums.Priority;

import java.time.LocalDate;

public class ProjectTask extends CourseTask{

    /**
     * Constructs a Project Task
     * @param title title of task
     * @param description description of task
     * @param deadline deadline of task
     * @param priority priority of task
     * @param estimatedHours estimated hours to complete the task
     * @param course course the task is related to
     */
    public ProjectTask(String title, String description, LocalDate deadline, Priority priority,
                       int estimatedHours, Reminder reminder, Course course) {
        super(title, description, deadline, priority, estimatedHours, reminder, course);
    }


}