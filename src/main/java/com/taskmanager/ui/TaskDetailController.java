package com.taskmanager.ui;

import com.taskmanager.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TaskDetailController {
    @FXML
    private HBox courseBox;
    @FXML
    private HBox referenceChapterBox;
    @FXML
    private HBox examDateBox;
    @FXML
    private HBox locationBox;
    @FXML
    private HBox contactPersonBox;
    @FXML
    private HBox appointmentTimeBox;
    @FXML
    private HBox documentNameBox;

    @FXML
    private Label titleLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label deadlineLabel;
    @FXML
    private Label estimatedHoursLabel;
    @FXML
    private Label priorityLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label reminderLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private Label referenceChapterLabel;
    @FXML
    private Label examDateLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label contactPersonLabel;
    @FXML
    private Label appointmentTimeLabel;
    @FXML
    private Label documentNameLabel;

    @FXML
    private Button cancelBtn;

    /**
     * Sets the task details in the window
     */
    public void setTask(Task task) {
        hideAllSpecificFields();

        // Every task has these
        titleLabel.setText(task.getTitle());
        typeLabel.setText(formatTaskType(task));
        descriptionLabel.setText(task.getDescription());
        deadlineLabel.setText(task.getDeadline().toString());
        estimatedHoursLabel.setText(String.valueOf(task.getEstimatedHours()));
        priorityLabel.setText(formatEnum(task.getPriority()));
        statusLabel.setText(formatEnum(task.getStatus()));
        reminderLabel.setText(formatEnum(task.getReminder().getReminderType()));

        // Extra information related to the type of task
        if (task instanceof HomeworkTask) {
            HomeworkTask t = (HomeworkTask) task;

            courseLabel.setText(t.getCourse().getName());
            referenceChapterLabel.setText(t.getReferenceChapter());

            courseBox.setVisible(true);
            courseBox.setManaged(true);

            referenceChapterBox.setVisible(true);
            referenceChapterBox.setManaged(true);
        }
        else if (task instanceof ProjectTask) {
            ProjectTask t = (ProjectTask) task;

            courseLabel.setText(t.getCourse().getName());

            courseBox.setVisible(true);
            courseBox.setManaged(true);
        }
        else if (task instanceof ExamPreparationTask) {
            ExamPreparationTask t = (ExamPreparationTask) task;

            courseLabel.setText(t.getCourse().getName());
            examDateLabel.setText(t.getExamDate().toString());

            courseBox.setVisible(true);
            courseBox.setManaged(true);

            examDateBox.setVisible(true);
            examDateBox.setManaged(true);
        }
        else if (task instanceof AppointmentTask) {
            AppointmentTask t = (AppointmentTask) task;

            locationLabel.setText(t.getLocation());
            contactPersonLabel.setText(t.getContactPerson());
            appointmentTimeLabel.setText(t.getAppointmentTime().toString());

            locationBox.setVisible(true);
            locationBox.setManaged(true);

            contactPersonBox.setVisible(true);
            contactPersonBox.setManaged(true);

            appointmentTimeBox.setVisible(true);
            appointmentTimeBox.setManaged(true);
        }
        else if (task instanceof AdministrativeTask) {
            AdministrativeTask t = (AdministrativeTask) task;

            locationLabel.setText(t.getLocation());
            documentNameLabel.setText(t.getDocumentName());

            locationBox.setVisible(true);
            locationBox.setManaged(true);

            documentNameBox.setVisible(true);
            documentNameBox.setManaged(true);
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

    private String formatEnum(Enum<?> value) {
        if (value == null) {
            return "";
        }

        String text = value.name()
                .toLowerCase()
                .replace("_", " ");

        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    private String formatTaskType(Task task) {
        String type = task.getClass().getSimpleName().replace("Task", "");

        return type.replaceAll("([a-z])([A-Z])", "$1 $2");
    }

    /**
     * Closes the task detail window
     */
    @FXML
    public void handleClose(ActionEvent event) {
        titleLabel.getScene().getWindow().hide();
    }

}
