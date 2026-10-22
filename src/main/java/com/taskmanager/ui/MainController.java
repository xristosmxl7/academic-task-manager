package com.taskmanager.ui;

import com.taskmanager.enums.Status;
import com.taskmanager.manager.AppManager;
import com.taskmanager.model.AppointmentTask;
import com.taskmanager.model.CourseTask;
import com.taskmanager.model.PersonalTask;
import com.taskmanager.model.Task;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class MainController {

    private AppManager appManager = new AppManager();

    // The current status filter
    private Status statusFilter = null;

    // The current category filter
    private String categoryFilter = null;

    // The TableView
    @FXML
    private TableView<Task> taskTableView;

    // Each of the columns
    @FXML
    private TableColumn<Task, String> titleColumn;
    @FXML
    private TableColumn<Task, String> categoryColumn;
    @FXML
    private TableColumn<Task, String> deadlineColumn;
    @FXML
    private  TableColumn<Task, String> priorityColumn;
    @FXML
    private TableColumn<Task, String> statusColumn;

    // action buttons
    @FXML
    private TableColumn<Task, Void> actionsColumn;

    @FXML
    private Button newTaskBtn;

    // The labels that show the stats
    @FXML
    private Label totalLabel;
    @FXML
    private Label pendingLabel;
    @FXML
    private Label expiringSoonLabel;
    @FXML
    private Label completedLabel;

    // ComboBox for filtering and sorting
    @FXML
    private ComboBox<String> categoryFilterComboBox;
    @FXML
    private ComboBox<String> sortComboBox;

    // Search bar
    @FXML
    private TextField searchField;

    /**
     * Sets up the TableView columns and loads task from the AppManager
     */
    public void initialize() {

        // Returns the title of the object for that row
        titleColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTitle()));

        // Returns the type of the task for that row
        categoryColumn.setCellValueFactory(data -> {
            String className = data.getValue().getClass().getSimpleName();
            String category = className.replace("Task", "");

            // Adds spaces between words, e.g. "ExamPeparation" -> "Exam Preparation"
            category = category.replaceAll("([a-z])([A-Z])", "$1 $2");

            return new SimpleStringProperty(category);
        });

        // Returns the deadline of the task for that row
        deadlineColumn.setCellValueFactory(data -> {
            Task task = data.getValue();
            if (task instanceof AppointmentTask) { // We add a specific time only for appointments
                AppointmentTask t = (AppointmentTask) task;
                String dateTime = t.getDeadline().toString() + " " + t.getAppointmentTime().toString();
                return new SimpleStringProperty(dateTime);
            }
            return new SimpleStringProperty(task.getDeadline().toString());
        });

        // Returns the priority of the task for that row
        priorityColumn.setCellValueFactory(data -> {
            String priority = data.getValue().getPriority().toString().toLowerCase();

            // Capitalizes the first letter
            priority = priority.substring(0, 1).toUpperCase() + priority.substring(1);

            return new SimpleStringProperty(priority);
        });

        // Returns the status of the task for that row
        statusColumn.setCellValueFactory(data -> {
            String status = data.getValue().getStatus().toString();

            // Convert enum format to user-friendly text
            status = status.replace("_", " ").toLowerCase();

            // Capitalize the first letter
            status = status.substring(0, 1).toUpperCase() + status.substring(1);

            return new SimpleStringProperty(status);
        });

        // We use this kind of list because it updates the table view automatically when changes happen
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        // Add all tasks from every list in AppManager
        tasks.addAll(appManager.getHomeworkTasks());
        tasks.addAll(appManager.getProjectTasks());
        tasks.addAll(appManager.getExamPreparationTasks());
        tasks.addAll(appManager.getAppointmentTasks());
        tasks.addAll(appManager.getAdministrativeTasks());

        // Set the list as the data source for the tableView
        taskTableView.setItems(tasks);

        actionsColumn.setCellFactory(col -> new TableCell<>() {

            // Create the buttons and put them in HBox to appear next to each other
            private final Button viewBtn = new Button("►");
            private final Button editBtn = new Button("✏");
            private final Button deleteBtn = new Button("✖");
            private final HBox buttons = new HBox(5, viewBtn, editBtn, deleteBtn);

            @Override
            // Void item because actionsColumn doesn't show data from task, just buttons.
            // Protected because it's override from class TableCell, can't be private
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    // If the row is empty, show nothing
                    setGraphic(null);
                }
                else {
                    // If not empty, show data
                    setGraphic(buttons);

                    deleteBtn.setOnAction(e -> {
                        // Finds the task
                        Task task = getTableView().getItems().get(getIndex());
                        // Removes the task from AppManager and JSON
                        appManager.deleteTask(task.getId());
                        // Removes the task from tableView
                        taskTableView.getItems().remove(task);
                    });

                    editBtn.setOnAction(e -> {
                        // Finds the task
                        Task task = getTableView().getItems().get(getIndex());
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edit-task-view.fxml"));
                            Parent root = loader.load();

                            // We create the object controller in order to use the methods of EditTaskController class
                            EditTaskController controller = loader.getController();
                            controller.setAppManager(appManager);
                            controller.setTask(task);

                            // Opens new window
                            Stage stage = new Stage();
                            stage.setTitle("Edit Task");
                            stage.setScene(new Scene(root));
                            stage.setOnHiding(ev -> refreshTable());
                            stage.show();
                        } catch (IOException ex) {
                            System.out.println("Error in opening Edit Task window!");
                        }
                    });

                    viewBtn.setOnAction(e -> {
                        // Finds the task
                        Task task = getTableView().getItems().get(getIndex());
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/task-detail-view.fxml"));
                            Parent root = loader.load();

                            // We don't call setAppManager, as we don't make any changes here
                            TaskDetailController controller = loader.getController();
                            controller.setTask(task);

                            Stage stage = new Stage();
                            stage.setTitle("Task Details");
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException ex) {
                            System.out.println("Error opening Task Detail window!");
                        }
                    });
                }
            }
        });

        // Fill comboBox of filter and sort with proper values
        categoryFilterComboBox.getItems().addAll(
                "All categories", "Homework", "Project",
                "ExamPreparation", "Appointment", "Administrative"
        );
        categoryFilterComboBox.setValue("All categories");

        sortComboBox.getItems().addAll(
                "Sort by: Deadline", "Sort by: Title", "Sort by: Priority"
        );
        sortComboBox.setValue("Sort by: Deadline");

        // We call it to load the tasks
        refreshTable();

        // Calls refreshTable every time the user changes the choice of the filter
        categoryFilterComboBox.setOnAction(e -> refreshTable());
        sortComboBox.setOnAction(e -> refreshTable());
        searchField.textProperty().addListener((obs, oldVal, newVal) ->
                refreshTable());

    }

    /**
     * Handles the New Task button
     * When new task button is clicked, opens the form to add create the task
     */
    public void handleNewTask(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add-task-view.fxml"));
            Parent root = loader.load();

            AddTaskController controller = loader.getController();
            controller.setAppManager(appManager);

            // Opens a new window
            Stage stage = new Stage();
            stage.setTitle("Add New Task");
            stage.setScene(new Scene(root));
            // Updates tableView when the window closes, so that tableView contains the new task
            stage.setOnHiding(e -> refreshTable());
            stage.show();
        } catch (IOException e) {
            System.out.println("Error opening Add Task window!");
        }
    }

    /**
     * Handles the Manage Courses button
     * Opens the form to manage courses
     */
    public void handleManageCourses(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/course-management-view.fxml"));
            Parent root = loader.load();

            CourseManagementController controller = loader.getController();
            controller.setAppManager(appManager);

            Stage stage = new Stage();
            stage.setTitle("Manage Courses");
            stage.setScene(new Scene(root));
            stage.setOnHiding(e -> refreshTable());
            stage.show();
        } catch (IOException e) {
            System.out.println("Error opening Course Management window!");
        }
    }

    private void refreshTable() {
        // Update task table
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        tasks.addAll(appManager.getHomeworkTasks());
        tasks.addAll(appManager.getProjectTasks());
        tasks.addAll(appManager.getExamPreparationTasks());
        tasks.addAll(appManager.getAppointmentTasks());
        tasks.addAll(appManager.getAdministrativeTasks());
        taskTableView.setItems(tasks);

        // Auto-update to EXPIRES SOON if deadline within 7 days
        for (Task task : tasks) {
            if (task.getDeadline().isBefore(LocalDate.now().plusDays(7))
            && task.getStatus() == Status.PENDING) {
                appManager.changeStatus(task.getId(), Status.EXPIRES_SOON);
                task.setStatus(Status.EXPIRES_SOON);
            }
        }
        // Refresh tableView in order to show the changes (if any) in status
        taskTableView.refresh();

        // Filter by category
        String selectedCategory = categoryFilterComboBox.getValue();
        if (selectedCategory != null && !selectedCategory.equals("All categories")) {
            tasks.removeIf(t -> !t.getClass().getSimpleName().replace("Task", "")
                    .equals(selectedCategory));
        }

        // Filter by status
        if (statusFilter != null) {
            tasks.removeIf(t -> t.getStatus() != statusFilter);
        }

        // Filter by task type (Course or Personal)
        if (categoryFilter != null) {
            if (categoryFilter.equals("Course")) {
                tasks.removeIf(t -> !(t instanceof CourseTask));
            }
            else if (categoryFilter.equals("Personal")) {
                tasks.removeIf(t -> !(t instanceof PersonalTask));
            }
        }

        String searchText = searchField.getText();
        if (searchText != null && !searchText.isBlank()) {
            tasks.removeIf(t -> !t.getTitle().toLowerCase().contains(searchText.toLowerCase()));
        }

        // Sort
        String selectedSort = sortComboBox.getValue();
        if (selectedSort != null) {
            switch (selectedSort) {
                case "Sort by: Deadline":
                {
                    tasks.sort((a, b) -> a.getDeadline().compareTo(b.getDeadline()));
                    break;
                }
                case "Sort by: Title":
                {
                    tasks.sort((a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
                    break;
                }
                case "Sort by: Priority":
                {
                    // From high to low priority
                    tasks.sort((a, b) -> b.getPriority().compareTo(a.getPriority()));
                    break;
                }
            }
        }

        // Update stat cards
        totalLabel.setText(String.valueOf(tasks.size()));

        long pending = tasks.stream()
                .filter(t -> t.getStatus() == Status.PENDING)
                .count();
        pendingLabel.setText(String.valueOf(pending));

        long expiringSoon = tasks.stream()
                .filter(t -> t.getStatus() == Status.EXPIRES_SOON)
                        .count();
        expiringSoonLabel.setText(String.valueOf(expiringSoon));

        long completed = tasks.stream()
                .filter(t -> t.getStatus() == Status.DONE)
                .count();
        completedLabel.setText(String.valueOf(completed));

    }

    /**
     * Show all tasks
     */
    @FXML
    public void handleAllTasks(ActionEvent event) {
        statusFilter = null;
        categoryFilter = null;
        refreshTable();
    }

    /**
     * Show only PENDING tasks
     */
    @FXML
    public void handlePending(ActionEvent event) {
        statusFilter = Status.PENDING;
        refreshTable();
    }

    /**
     * Show only EXPIRES_SOON tasks
     */
    @FXML
    public void handleExpiringSoon(ActionEvent event) {
        statusFilter = Status.EXPIRES_SOON;
        refreshTable();
    }

    /**
     * Show only COMPLETED tasks
     */
    @FXML
    public void handleCompleted(ActionEvent event) {
        statusFilter = Status.DONE;
        refreshTable();
    }

    /**
     * Show only COURSE tasks
     */
    @FXML
    public void handleCourseTasks(ActionEvent event) {
        categoryFilter = "Course";
        statusFilter = null;
        refreshTable();
    }

    /**
     * Show only PERSONAL tasks
     */
    @FXML
    public void handlePersonalTasks(ActionEvent event) {
        categoryFilter = "Personal";
        statusFilter = null;
        refreshTable();
    }
}

