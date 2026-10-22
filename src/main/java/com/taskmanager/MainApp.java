package com.taskmanager;

import com.taskmanager.manager.AppManager;
import com.taskmanager.model.Task;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Academic Task Manager");
        stage.setScene(scene);
        stage.show();

        // Check for due reminders on startup
        AppManager appManager = new AppManager();
        ObservableList<Task> allTasks = FXCollections.observableArrayList();
        allTasks.addAll(appManager.getHomeworkTasks());
        allTasks.addAll(appManager.getProjectTasks());
        allTasks.addAll(appManager.getExamPreparationTasks());
        allTasks.addAll(appManager.getAppointmentTasks());
        allTasks.addAll(appManager.getAdministrativeTasks());

        StringBuilder reminderMessage = new StringBuilder();
        for (Task task : allTasks) {
            if (task.getReminder().isDue(task.getDeadline())) {
                reminderMessage.append("• ").append(task.getTitle())
                        .append(" - ").append(task.getReminder().toString())
                        .append("\n");
                task.getReminder().trigger();
                appManager.saveAllTasks();
            }
        }

        if (reminderMessage.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reminders");
            alert.setHeaderText("You have pending reminders!");
            alert.setContentText(reminderMessage.toString());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}