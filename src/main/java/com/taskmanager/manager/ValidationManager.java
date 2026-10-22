package com.taskmanager.manager;

import com.taskmanager.model.Course;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ValidationManager {

    /**
     * Checks that title is not null or empty, and < 100  characters
     * @param title title of the task
     * @return message if title not in the right form, null if everything is ok
     */
    public static String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            return "Title cannot be empty!";
        }
        if (title.length() > 100) {
            return "Title cannot be more than 100 characters!";
        }
        return null;
    }

    /**
     * Checks that description is not null and less than 500 characters
     * @param description description of the task
     * @return message if description not in the right form, null if everything is ok
     */
    public static String validateDescription(String description) {
        if (description != null && description.length() > 500) {
            return "The description cannot contain more than 500 characters!";
        }
        return null;
    }

    /**
     * Checks that deadline is not null and not before current date
     * @param deadline deadline to complete the task
     * @return message if deadline is not in the right form, null if everything is ok
     */
    public static String validateDeadline(LocalDate deadline) {
        if (deadline == null) {
            return "Please select a deadline!";
        }
        if (deadline.isBefore(LocalDate.now())) {
            return "The deadline must not be before current date!";
        }
        return null;
    }

    /**
     * Checks that estimatedHours >= 0 and < 150
     * @param estimatedHours hours to complete that task
     * @return message if estimatedHours not in the right form, null if everything is ok
     */
    public static String validateEstimatedHours(int estimatedHours) {
        if (estimatedHours <= 0) {
            return "You must give a time!";
        }
        if (estimatedHours > 150) {
            return "Estimated hours seems unrealistic!";
        }
        return null;
    }

    /**
     * Checks that course is not null
     * @param course course
     * @return message if course is null, null if everything is ok
     */
    public static String validateCourse(Course course) {
        if (course == null) {
            return "You must enter a course!";
        }
        return null;
    }

    /**
     * Checks that reference chapter is not null, blank or > 100 characters
     * @param referenceChapter reference chapter of the homework task
     * @return message if reference chapter is not in the right form, null if everything is ok
     */
    public static String validateReferenceChapter(String referenceChapter) {
        if (referenceChapter == null || referenceChapter.isBlank()) {
            return "Enter a reference chapter!";
        }
        if (referenceChapter.length() > 100) {
            return "Reference chapter cannot contain more than 100 characters!";
        }
        return null;
    }

    /**
     * Checks that exam date and deadline are not null, not after the current date
     * also that deadline is before examDate
     * @param examDate date of the exam
     * @param deadline deadline of the task
     * @return message if something is in the wrong form, null if everything is ok
     */
    public static String validateExamDate(LocalDate examDate, LocalDate deadline) {
        if (examDate == null) {
            return "Please enter exam date!";
        }
        if (deadline == null) {
            return "Please enter deadline!";
        }
        if (examDate.isBefore(LocalDate.now())) {
            return "Exam date must not be before current day!";
        }
        if (!deadline.isBefore(examDate)) {
            return "Deadline must be before exam date!";
        }
        return null;
    }

    /**
     * Checks that location is not null and not > 100
     * @param location location of the appointment
     * @return messages if something goes wrong, null if everything is ok
     */
    public static String validateLocation(String location) {
        if (location == null) {
            return "Please enter location!";
        }
        if (location.length() > 100) {
            return "Location cannot contain more than 100 characters!";
        }
        return null;
    }

    /**
     * Checks that contact person is not null or blank, and not > 100 characters
     * @param contactPerson name of the person you meet in the appointment
     * @return messages if something goes wrong, null if everything is ok
     */
    public static String validateContactPerson(String contactPerson) {
        if (contactPerson == null || contactPerson.isBlank()) {
            return "Please enter name of contact person!";
        }
        if (contactPerson.length() > 100) {
            return "Contact person's name cannot contain more than 100 characters!";
        }
        return null;
    }

    /**
     * Checks that document name is not null or empty, and not > 100 characters
     * @param documentName name of the document
     * @return messages if something goes wrong, null if everything is ok
     */
    public static String validateDocumentName(String documentName) {
        if (documentName == null || documentName.isBlank()) {
            return "Please enter document name!";
        }
        if (documentName.length() > 100) {
            return "Document name cannot contain more than 100 characters!";
        }
        return null;
    }

    /**
     * Checks that appointment time is not null
     * If appointment day is today, check that appointment time is after current time
     * @param appointmentTime time of the appointment
     * @param deadline day of the appointment
     * @return messages if something not valid, else null
     */
    public static String validateAppointmentTime(LocalTime appointmentTime, LocalDate deadline) {
        if (deadline.isEqual(LocalDate.now()) && appointmentTime.isBefore(LocalTime.now())) {
            return "Time must be after current time!";
        }
        return null;
    }
}
