package com.taskmanager.model;

import com.taskmanager.enums.Priority;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentTask extends PersonalTask {
    private String contactPerson;
    private LocalTime appointmentTime;

    /**
     * Constructs an appointment task
     * @param title title of the task
     * @param description description of the task
     * @param deadline here represents the date of the task
     * @param priority priority of the task
     * @param estimatedHours hours to complete the task
     * @param location location the meeting happens
     * @param contactPerson the person you will meet
     */
    public AppointmentTask(String title, String description, LocalDate deadline, Priority priority,
                           int estimatedHours, Reminder reminder, String location, String contactPerson,
                           LocalTime appointmentTime) {
        super(title, description, deadline, priority, estimatedHours, reminder, location);
        this.contactPerson = contactPerson;
        this.appointmentTime = appointmentTime;
    }

    /**
     * Sets the name of the person that we meet
     * @param contactPerson the name of the person
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * Gets the name of the person we meet with
     * @return the name of the person
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * Sets the time of the appointment
     * @param appointmentTime time of the appointment
     */
    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * Gets the time of the appointment
     * @return appointment time
     */
    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

}
