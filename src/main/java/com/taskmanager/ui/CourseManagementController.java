package com.taskmanager.ui;

import com.taskmanager.manager.AppManager;
import com.taskmanager.model.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class CourseManagementController {
    private AppManager appManager;

    @FXML
    private ListView<Course> courseListView;

    @FXML
    private TextField newCourseField;

    /**
     * Sets the shared AppManager and loads the courses into the list view
     * @param appManager the shared AppManager
     */
    public void setAppManager(AppManager appManager) {
        this.appManager = appManager;
        courseListView.getItems().clear();
        courseListView.getItems().addAll(appManager.getCourses());
    }


    /**
     * Handles the Add Button
     * Reads course name, creates a new course, adds course to AppManager and list view
     */
    @FXML
    public void handleAddCourse(ActionEvent event) {
        String courseName = newCourseField.getText();

        if (courseName == null || courseName.isBlank()) {
            return;
        }

        Course course = new Course(courseName);
        appManager.addCourse(course);
        courseListView.getItems().add(course);
        newCourseField.clear();
    }

    /**
     * Handles the delete button
     * Removes the selected course from the AppManager and list view
     */
    @FXML
    public void handleDeleteCourse(ActionEvent event) {
        Course selected = courseListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        appManager.deleteCourse(selected);
        courseListView.getItems().remove(selected);
    }

    /**
     * Closes the Course Management window
     */
    @FXML
    public void handleClose(ActionEvent event) {
        courseListView.getScene().getWindow().hide();
    }

}
