package com.taskmanager.model;

import com.taskmanager.enums.ReminderType;

import java.time.LocalDate;

public class Reminder {
    private ReminderType reminderType;
    private boolean triggered;

    public Reminder (ReminderType reminderType){
        this.reminderType = reminderType;
    }

    /**
     * Sets the reminder type
     * @param reminderType the reminder type
     */
    public void setReminderType(ReminderType reminderType) {
        this.reminderType = reminderType;
    }

    /**
     * Gets the reminder type
     * @return the reminder type
     */
    public ReminderType getReminderType() {
        return reminderType;
    }

    /**
     * Get the value of triggered
     * @return true or false
     */
    public boolean isTriggered() {
        return triggered;
    }

    /**
     * Set trigger to true
     */
    public void trigger() {
        this.triggered = true;
    }

    /**
     * Checks whether the reminder is due and has not already been triggered.
     *
     * @param deadline the task deadline
     * @return true if the reminder should be shown
     */
    public boolean isDue(LocalDate deadline) {
        LocalDate reminderDate = getReminderDate(deadline);
        return !triggered && !reminderDate.isAfter(LocalDate.now());
    }

    /**
     * Calculates the reminder date based on the task deadline.
     *
     * @param deadline the task deadline
     * @return the date on which the reminder becomes due
     */
    public LocalDate getReminderDate(LocalDate deadline) {
        return switch (reminderType) {
            case ONE_DAY -> deadline.minusDays(1);
            case TWO_DAYS -> deadline.minusDays(2);
            case ONE_WEEK -> deadline.minusWeeks(1);
        };
    }

    @Override
    public String toString() {
        return switch (reminderType) {
            case ONE_DAY -> "One day left!";
            case TWO_DAYS -> "Two days left!";
            case ONE_WEEK ->"One week left!";
        };
    }

}