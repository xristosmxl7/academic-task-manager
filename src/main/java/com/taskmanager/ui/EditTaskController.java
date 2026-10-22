package com.taskmanager.ui;

import com.taskmanager.enums.Priority;
import com.taskmanager.enums.ReminderType;
import com.taskmanager.enums.Status;
import com.taskmanager.manager.AppManager;
import com.taskmanager.manager.ValidationManager;
import com.taskmanager.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;

public class EditTaskController {

    private AppManager appManager;
    private Priority priority;
    private Task currentTask;

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
    private Label taskTypeLabel;
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
    private ComboBox<ReminderType> reminderTypeComboBox;
    @FXML
    private ComboBox<Status> statusComboBox;
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
    private TextField documentNameField;
    @FXML
    private ComboBox<Integer> hourComboBox;
    @FXML
    private ComboBox<Integer> minuteComboBox;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;

    public void initialize() {
        for (int i = 0; i < 24; i++) {
            hourComboBox.getItems().add(i);
        }

        minuteComboBox.getItems().addAll(0, 15, 30, 45);

        reminderTypeComboBox.setConverter(createEnumConverter());
        statusComboBox.setConverter(createEnumConverter());
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

    public void updatePriorityButtons() {
        lowPriorityBtn.setStyle("");
        mediumPriorityBtn.setStyle("");
        highPriorityBtn.setStyle("");

        if (priority == Priority.LOW) {
            lowPriorityBtn.setStyle("-fx-background-color: #EAF3DE;");
        } else if (priority == Priority.MEDIUM) {
            mediumPriorityBtn.setStyle("-fx-background-color: #EAD3DE;");
        } else if (priority == Priority.HIGH) {
            highPriorityBtn.setStyle("-fx-background-color: #FCEBEB;");
        }
    }

    /**
     * Hides all task type-specific fields.
     */
    private void hideAllSpecificFields() {
        courseBox.setVisible(false);
        courseBox.setManaged(false);

        referenceChapterBox.setVisible(false);
        referenceChapterBox.setManaged(false);

        examDateBox.setVisible(false);
        examDateBox.setManaged(false);

        locationBox.setVisible(false);
        locationBox.setManaged(false);

        contactPersonBox.setVisible(false);
        contactPersonBox.setManaged(false);

        appointmentTimeBox.setVisible(false);
        appointmentTimeBox.setManaged(false);

        documentNameBox.setVisible(false);
        documentNameBox.setManaged(false);
    }

    /**
     * Sets the task giving it the proper values
     * @param task the task that the user edits
     */
    public void setTask(Task task) {
        this.currentTask = task;

        hideAllSpecificFields();

        taskTypeLabel.setText(formatTaskType(task));
        titleField.setText(task.getTitle());
        descriptionField.setText(task.getDescription());
        deadlinePicker.setValue(task.getDeadline());
        estimatedHoursField.setText(String.valueOf(task.getEstimatedHours()));
        reminderTypeComboBox.setValue(task.getReminder().getReminderType());
        priority = task.getPriority();
        updatePriorityButtons();
        statusComboBox.setValue(task.getStatus());

        // Specific fields that depend on the type of task
        if (task instanceof HomeworkTask) {
            HomeworkTask t = (HomeworkTask) task;

            courseComboBox.setValue(t.getCourse());
            referenceChapterField.setText(t.getReferenceChapter());

            courseBox.setVisible(true);
            courseBox.setManaged(true);

            referenceChapterBox.setVisible(true);
            referenceChapterBox.setManaged(true);
        }
        else if (task instanceof ProjectTask) {
            ProjectTask t = (ProjectTask) task;
            courseComboBox.setValue(t.getCourse());

            courseBox.setVisible(true);
            courseBox.setManaged(true);
        }
        else if (task instanceof ExamPreparationTask) {
            ExamPreparationTask t = (ExamPreparationTask) task;

            courseComboBox.setValue(t.getCourse());
            examDatePicker.setValue(t.getExamDate());

            courseBox.setVisible(true);
            courseBox.setManaged(true);

            examDateBox.setVisible(true);
            examDateBox.setManaged(true);
        }
        else if (task instanceof AppointmentTask) {
            AppointmentTask t = (AppointmentTask) task;

            locationField.setText(t.getLocation());
            contactPersonField.setText(t.getContactPerson());

            LocalTime appointmentTime = t.getAppointmentTime();
            hourComboBox.setValue(appointmentTime.getHour());
            minuteComboBox.setValue(appointmentTime.getMinute());

            locationBox.setVisible(true);
            locationBox.setManaged(true);

            contactPersonBox.setVisible(true);
            contactPersonBox.setManaged(true);

            appointmentTimeBox.setVisible(true);
            appointmentTimeBox.setManaged(true);
        }
        else if (task instanceof AdministrativeTask) {
            AdministrativeTask t = (AdministrativeTask) task;

            locationField.setText(t.getLocation());
            documentNameField.setText(t.getDocumentName());

            locationBox.setVisible(true);
            locationBox.setManaged(true);

            documentNameBox.setVisible(true);
            documentNameBox.setManaged(true);
        }
    }

    private String formatTaskType(Task task) {
        String type = task.getClass().getSimpleName().replace("Task", "");

        return type.replaceAll("([a-z])([A-Z])", "$1 $2");
    }

    /**
     * Sets the shared AppManager, populates fields that depend on it
     */
    public void setAppManager(AppManager appManager) {
        this.appManager = appManager;
        courseComboBox.getItems().addAll(appManager.getCourses());
        reminderTypeComboBox.getItems().addAll(ReminderType.values());
        statusComboBox.getItems().addAll(Status.values());
    }

    /**
     * handles the choice of low priority and highlights the selected button
     */
    public void handleLowPriority(ActionEvent event) {
        priority = Priority.LOW;
        updatePriorityButtons();
    }

    /**
     * handles the choice of medium priority and highlights the selected button
     */
    public void handleMediumPriority(ActionEvent event) {
        priority = Priority.MEDIUM;
        updatePriorityButtons();
    }

    /**
     * handles the choice of high priority and highlights the selected button
     */
    public void handleHighPriority(ActionEvent event) {
        priority = Priority.HIGH;
        updatePriorityButtons();
    }

    /**
     * handles Save button
     * Validates the user's input and informs them for something wrong.
     * if everything is good, edits the task and saves it in the proper JSON file.
     */
    public void handleSaveBtn(ActionEvent event) {

        // Read all fields
        String title = titleField.getText();
        String description = descriptionField.getText();
        LocalDate deadline = deadlinePicker.getValue();
        String hours = estimatedHoursField.getText();
        Course course = courseComboBox.getValue();
        ReminderType reminderType = reminderTypeComboBox.getValue();
        String taskType = taskTypeLabel.getText();

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
                appManager.editHomeworkTask(currentTask.getId(), title, description, deadline, priority, estimatedHours,
                        reminder, statusComboBox.getValue(), course, referenceChapterField.getText());
                break;

            case "Project":
                String courseError2 = ValidationManager.validateCourse(course);
                if (courseError2 != null) {
                    showAlert(courseError2);
                    return;
                }
                appManager.editProjectTask(currentTask.getId(), title, description, deadline, priority, estimatedHours,
                        reminder, statusComboBox.getValue(), course);
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
                appManager.editExamPreparationTask(currentTask.getId(), title, description, deadline, priority, estimatedHours,
                        reminder, statusComboBox.getValue(), course, examDate);
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

                appManager.editAppointmentTask(currentTask.getId(), title, description, deadline, priority, estimatedHours,
                        reminder, statusComboBox.getValue(), location, contactPerson, appointmentTime);
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

                appManager.editAdministrativeTask(currentTask.getId(), title, description, deadline, priority, estimatedHours,
                        reminder, statusComboBox.getValue(), location2, documentName);
                break;
        }

        // Close the window
        saveBtn.getScene().getWindow().hide();
    }

    /**
     * Handles the Cancel button, closes the window without making any changes
     */
    public void handleCancelBtn(ActionEvent event) {
        cancelBtn.getScene().getWindow().hide();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
