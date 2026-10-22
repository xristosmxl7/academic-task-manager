package com.taskmanager.ui;

import com.taskmanager.enums.Priority;
import com.taskmanager.enums.ReminderType;
import com.taskmanager.manager.AppManager;
import com.taskmanager.manager.ValidationManager;
import com.taskmanager.model.Course;
import com.taskmanager.model.Reminder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddTaskController {

    private AppManager appManager;
    private Priority priority;

    @FXML
    private ComboBox<String> taskTypeComboBox;
    @FXML
    private VBox courseBox;
    @FXML
    private VBox referenceChapterBox;
    @FXML
    private VBox examDateBox;
    @FXML
    private VBox locationBox;
    @FXML
    private VBox contactPersonBox;
    @FXML
    private VBox appointmentTimeBox;
    @FXML
    private VBox documentNameBox;

    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private DatePicker deadlinePicker;
    @FXML
    private TextField estimatedHoursField;
    @FXML
    private ComboBox<Course> courseComboBox;
    @FXML
    private ComboBox<ReminderType> reminderComboBox;

    @FXML
    private Button lowPriorityBtn;
    @FXML
    private Button mediumPriorityBtn;
    @FXML
    private Button highPriorityBtn;

    @FXML
    private TextField referenceChapterField;
    @FXML
    private DatePicker examDatePicker;
    @FXML
    private TextField locationField;
    @FXML
    private TextField contactPersonField;
    @FXML
    private ComboBox<Integer> hourComboBox;
    @FXML
    private ComboBox<Integer> minuteComboBox;
    @FXML
    private TextField documentNameField;

    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;

    /**
     * Populates taskTypeComboBox, hourComboBox and minuteComboBox
     */
    public void initialize() {
        taskTypeComboBox.getItems().addAll(
                "Homework",
                "Project",
                "Exam Preparation",
                "Appointment",
                "Administrative"
        );

        updateFieldsForTaskType(null);

        for (int i = 0; i < 24; i++) {
            hourComboBox.getItems().add(i);
        }

        minuteComboBox.getItems().addAll(0, 15, 30, 45);

        taskTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
                updateFieldsForTaskType(newValue));

        reminderComboBox.setConverter(createEnumConverter());
    }

    private <T extends Enum<T>> StringConverter<T> createEnumConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(T value) {
                if (value == null) {
                    return "";
                }

                String text = value.name()
                        .toLowerCase()
                        .replace("_", " ");

                return Character.toUpperCase(text.charAt(0)) + text.substring(1);
            }

            @Override
            public T fromString(String string) {
                return null;
            }
        };
    }

    private void setFieldVisibility(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

    private void updateFieldsForTaskType(String taskType) {
        // Hide all task-specific fields
        setFieldVisibility(courseBox, false);
        setFieldVisibility(referenceChapterBox, false);
        setFieldVisibility(examDateBox, false);
        setFieldVisibility(locationBox, false);
        setFieldVisibility(contactPersonBox, false);
        setFieldVisibility(appointmentTimeBox, false);
        setFieldVisibility(documentNameBox, false);

        if (taskType == null) {
            return;
        }

        // Show only the fields required by the selected task type
        switch (taskType) {
            case "Homework":
                setFieldVisibility(referenceChapterBox, true);
                setFieldVisibility(courseBox, true);
                break;

            case "Project":
                setFieldVisibility(courseBox, true);
                break;

            case "Exam Preparation":
                setFieldVisibility(examDateBox, true);
                setFieldVisibility(courseBox, true);
                break;

            case "Appointment":
                setFieldVisibility(locationBox, true);
                setFieldVisibility(contactPersonBox, true);
                setFieldVisibility(appointmentTimeBox, true);
                break;

            case "Administrative":
                setFieldVisibility(locationBox, true);
                setFieldVisibility(documentNameBox, true);
                break;
        }
    }

    /**
     * Sets the shared AppManager, populates fields that depend on it
     */
    public void setAppManager(AppManager appManager) {
        this.appManager = appManager;
        reminderComboBox.getItems().addAll(ReminderType.values());
        courseComboBox.getItems().addAll(appManager.getCourses());
    }

    /**
     * Handles the choice of low priority and highlights the selected button
     */
    public void handleLowPriority(ActionEvent event) {
        priority = Priority.LOW;

        // Shows the button that is selected
        lowPriorityBtn.setStyle("-fx-background-color: #EAF3DE;");
        mediumPriorityBtn.setStyle("");
        highPriorityBtn.setStyle("");
    }

    /**
     * handles the choice of medium priority and highlights the selected button
     */
    public void handleMediumPriority(ActionEvent event) {
        priority = Priority.MEDIUM;

        // Shows the button that is selected
        lowPriorityBtn.setStyle("");
        mediumPriorityBtn.setStyle("-fx-background-color: #EAD3DE;");
        highPriorityBtn.setStyle("");
    }

    /**
     * handles the choice of high priority and highlights the selected button
     */
    public void handleHighPriority(ActionEvent event) {
        priority = Priority.HIGH;

        // Shows the button that is selected
        lowPriorityBtn.setStyle("");
        mediumPriorityBtn.setStyle("");
        highPriorityBtn.setStyle("-fx-background-color: #FCEBEB;");
    }

    /**
     * handles Save button
     * Validates the user's input and informs them for something wrong.
     * if everything is good, creates the proper type of task and saves it in the proper JSON file.
     */
    public void handleSave(ActionEvent event) {

        // Read all fields
        String title = titleField.getText();
        String description = descriptionField.getText();
        LocalDate deadline = deadlinePicker.getValue();
        String hours = estimatedHoursField.getText();
        Course course = courseComboBox.getValue();
        ReminderType reminderType = reminderComboBox.getValue();
        String taskType = taskTypeComboBox.getValue();

        // Validate the fields
        String titleError = ValidationManager.validateTitle(title);
        if (titleError != null) {
            showAlert(titleError);
            return;
        }

        String descriptionError = ValidationManager.validateDescription(description);
        if (descriptionError != null) {
            showAlert(descriptionError);
            return;
        }

        String deadlineError = ValidationManager.validateDeadline(deadline);
        if (deadlineError != null) {
            showAlert(deadlineError);
            return;
        }

        int estimatedHours;
        try {
            estimatedHours = Integer.parseInt(hours);
        } catch (NumberFormatException e) {
            showAlert("Estimated hours must be a number!");
            return;
        }

        String estimatedHoursError = ValidationManager.validateEstimatedHours(estimatedHours);
        if (estimatedHoursError != null) {
            showAlert(estimatedHoursError);
            return;
        }

        if (priority == null) {
            showAlert("Please select a priority!");
            return;
        }

        if (reminderType == null) {
            showAlert("Please select a reminder!");
            return;
        }

        if (taskType == null) {
            showAlert("Please select a task type!");
            return;
        }

        Reminder reminder = new Reminder(reminderType);

        // Extra validations for each type of task
        switch (taskType) {
            case "Homework" :
                String chapter = ValidationManager.validateReferenceChapter(referenceChapterField.getText());
                if (chapter != null) {
                    showAlert(chapter);
                    return;
                }
                String courseError = ValidationManager.validateCourse(course);
                if (courseError != null) {
                    showAlert(courseError);
                    return;
                }
                appManager.createHomeworkTask(title, description, deadline, priority, estimatedHours,
                        reminder, course, referenceChapterField.getText());
                break;

            case "Project":
                String courseError2 = ValidationManager.validateCourse(course);
                if (courseError2 != null) {
                    showAlert(courseError2);
                    return;
                }
                appManager.createProjectTask(title, description, deadline, priority, estimatedHours,
                        reminder, course);
                break;

            case "Exam Preparation":
                String courseError3 = ValidationManager.validateCourse(course);
                if (courseError3 != null) {
                    showAlert(courseError3);
                    return;
                }
                LocalDate examDate = examDatePicker.getValue();
                String examDateError = ValidationManager.validateExamDate(examDate, deadline);
                if (examDateError != null) {
                    showAlert(examDateError);
                    return;
                }
                appManager.createExamPreparationTask(title, description, deadline, priority, estimatedHours,
                        reminder, course, examDate);
                break;

            case "Appointment":
                String location = locationField.getText();
                String contactPerson = contactPersonField.getText();
                Integer hour = hourComboBox.getValue();
                Integer minute = minuteComboBox.getValue();

                String locationError = ValidationManager.validateLocation(location);
                if (locationError != null) {
                    showAlert(locationError);
                    return;
                }
                String contactPersonError = ValidationManager.validateContactPerson(contactPerson);
                if (contactPersonError != null) {
                    showAlert(contactPersonError);
                    return;
                }
                if (hour == null || minute == null) {
                    showAlert("Please select appointment time!");
                    return;
                }
                LocalTime appointmentTime = LocalTime.of(hour, minute);

                String appointmentTimeError = ValidationManager.validateAppointmentTime(appointmentTime, deadline);

                if (appointmentTimeError != null) {
                    showAlert(appointmentTimeError);
                    return;
                }

                appManager.createAppointmentTask(title, description, deadline, priority, estimatedHours,
                        reminder, location, contactPerson, appointmentTime);
                break;

            case "Administrative":
                String location2 = locationField.getText();
                String locationError2 = ValidationManager.validateLocation(location2);
                if (locationError2 != null) {
                    showAlert(locationError2);
                    return;
                }
                String documentName = documentNameField.getText();
                String documentNameError = ValidationManager.validateDocumentName(documentName);
                if (documentNameError != null) {
                    showAlert(documentNameError);
                    return;
                }

                appManager.createAdministrativeTask(title, description, deadline, priority, estimatedHours,
                        reminder, location2, documentName);
                break;
        }

        // Close the window
        saveBtn.getScene().getWindow().hide();

    }

    /**
     * Handles the Cancel button, closes the window without making any changes
     */
    public void handleCancel(ActionEvent event) {
        cancelBtn.getScene().getWindow().hide();
    }

    /**
     * Shows an error alert dialog with the given message
     * @param message the error message to display
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
