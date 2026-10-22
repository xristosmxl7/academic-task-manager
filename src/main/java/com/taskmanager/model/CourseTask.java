package com.taskmanager.model;

import com.taskmanager.enums.Priority;

import java.time.LocalDate;

public abstract class CourseTask extends Task {
    private Course course;

    /**
     * Constructs a courseTask
     * @param title the title of task
     * @param description the description of task
     * @param deadline deadline
     * @param priority priority
     * @param estimatedHours estimated hours to complete the task
     * @param course the course that the task his related to
     */
    public CourseTask(String title, String description, LocalDate deadline, Priority priority,
                      int estimatedHours, Reminder reminder, Course course) {
        super(title, description, deadline, priority, estimatedHours, reminder);
        this.course = course;
    }

    /**
     * Set the course that the task has to do with
     * @param course the course
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Get the course
     * @return course
     */
    public Course getCourse() {
        return course;
    }
}