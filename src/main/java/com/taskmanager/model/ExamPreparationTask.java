package com.taskmanager.model;

import com.taskmanager.enums.Priority;

import java.time.LocalDate;

public class ExamPreparationTask extends CourseTask {
    private LocalDate examDate;

    /**
     * Constructs an ExamPreparation Task
     * @param title the title of the task
     * @param description the description of the task
     * @param deadline the deadline of the task
     * @param priority priority of the task
     * @param estimatedHours estimated hours to complete the task
     * @param course the course that the exam is on
     * @param examDate the date of the exam
     */
    public ExamPreparationTask(String title, String description, LocalDate deadline, Priority priority,
                               int estimatedHours, Reminder reminder, Course course, LocalDate examDate) {
        super(title, description, deadline, priority, estimatedHours, reminder, course);
        this.examDate = examDate;
    }

    /**
     * Sets the date of the exam
     * @param examDate the date of the exam
     */
    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    /**
     * Gets the date of the exam
     * @return the date of the exam
     */
    public LocalDate getExamDate() {
        return examDate;
    }

}