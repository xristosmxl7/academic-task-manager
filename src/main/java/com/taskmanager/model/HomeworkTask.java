package com.taskmanager.model;

import com.taskmanager.enums.Priority;

import java.time.LocalDate;

public class HomeworkTask extends CourseTask {
    private String referenceChapter;

    /**
     * Constructs a Homework task
     * @param title title of task
     * @param description description of task
     * @param deadline deadline of task
     * @param priority priority of task
     * @param estimatedHours hours to complete the task
     * @param course the course that the task is related to
     * @param referenceChapter the exact chapter
     */
    public HomeworkTask(String title, String description, LocalDate deadline, Priority priority,
                        int estimatedHours, Reminder reminder, Course course, String referenceChapter) {
        super(title, description, deadline, priority, estimatedHours, reminder, course);
        this.referenceChapter = referenceChapter;
    }

    /**
     * Set reference chapter of the task
     * @param referenceChapter reference chapter
     */
    public void setReferenceChapter(String referenceChapter) {
        this.referenceChapter = referenceChapter;
    }

    /**
     * Gets the reference chapter of the task
     * @return reference chapter
     */
    public String getReferenceChapter() {
        return referenceChapter;
    }
}