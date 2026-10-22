package com.taskmanager.model;
import com.taskmanager.enums.*;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Task {
    private final String id;
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate deadline;
    private int estimatedHours;
    private Priority priority;
    private Status status;
    private Reminder reminder;

    /**
     * Constructs a courseTask
     * @param title the title of task
     * @param description the description of task
     * @param deadline deadline
     * @param priority priority
     * @param estimatedHours estimated hours to complete the task
     */
    public Task(String title, String description, LocalDate deadline, Priority priority,
                int estimatedHours, Reminder reminder) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.estimatedHours = estimatedHours;
        this.creationDate = LocalDate.now();
        this.status = Status.PENDING;
        this.reminder = reminder;
    }

    /**
     * Gets the ID of the task
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the title of the task
     * @param title the title of the task
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the title of the task
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the description of the task
     * @param description the description of task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the task
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the day that the task was created
     * @param creationDate the date of creation
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the day of creation
     * @return the day of creation
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the deadline of the task
     * @param deadline deadline of the task
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets the day of deadline
     * @return deadline
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * The number of hours expected for finishing the task
     * @param estimatedHours number of hours
     */
    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    /**
     * Gets the estimated hours
     * @return estimated hours
     */
    public int getEstimatedHours() {
        return estimatedHours;
    }

    /**
     * Sets the priority of the task
     * @param priority the priority
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Gets the priority of the task
     * @return priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the status of the task
     * @param status the status of task
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the status of the task
     * @return status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set the reminder of the task
     * @param reminder the reminder of task
     */
    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    /**
     * Get the reminder of the task
     * @return the reminder
     */
    public Reminder getReminder() {
        return reminder;
    }
}