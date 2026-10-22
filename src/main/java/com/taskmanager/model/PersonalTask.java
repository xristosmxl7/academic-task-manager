package com.taskmanager.model;

import java.time.LocalDate;
import com.taskmanager.enums.*;

public abstract class PersonalTask extends Task {
    private String location;

    /**
     * Constructs a Personal Task
     * @param title the title of task
     * @param description description of task
     * @param deadline deadline of the task
     * @param priority priority of task
     * @param estimatedHours estimated hours to complete the task
     * @param location location (ex. zoom, registry...)
     */
    public PersonalTask(String title, String description, LocalDate deadline, Priority priority,
                        int estimatedHours, Reminder reminder, String location) {
        super(title, description, deadline, priority, estimatedHours, reminder);
        this.location = location;
    }

    /**
     * Set the location on which the task will take place
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get the location that the task takes place on
     * @return the location
     */
    public String getLocation() {
        return location;
    }
}