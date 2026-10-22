package com.taskmanager.model;

import com.taskmanager.enums.*;
import java.time.LocalDate;

public class AdministrativeTask extends PersonalTask {
    private String documentName;

    /**
     * Constructs an Administrative Task
     * @param title title of the task
     * @param description description of the task
     * @param deadline deadline of the task
     * @param priority priority of the task
     * @param estimatedHours estimated hours to finish the task
     * @param location location of the task
     * @param documentName name of the needed document
     */
    public AdministrativeTask(String title, String description, LocalDate deadline, Priority priority,
                                    int estimatedHours, Reminder reminder, String location, String documentName) {
        super(title, description, deadline, priority, estimatedHours, reminder, location);
        this.documentName = documentName;
    }

    /**
     *Set the name of the document
     * @param documentName name of document
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * Get the name of document
     * @return document name
     */
    public String getDocumentName() {
        return documentName;
    }
}
