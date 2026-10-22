package com.taskmanager.manager;

import com.taskmanager.enums.Priority;
import com.taskmanager.enums.Status;
import com.taskmanager.model.*;
import com.taskmanager.storage.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.ArrayList;

public class AppManager {
    private List<HomeworkTask> homeworkTasks = new ArrayList<>();
    private List<ProjectTask> projectTasks = new ArrayList<>();
    private List<ExamPreparationTask> examPreparationTasks = new ArrayList<>();
    private List<AppointmentTask> appointmentTasks = new ArrayList<>();
    private List<AdministrativeTask> administrativeTasks = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    /**
     * Constructs the class AppManager
     */
    public AppManager() {
        this.homeworkTasks = DataManager.loadHomeworkTasks();
        this.projectTasks = DataManager.loadProjectTasks();
        this.examPreparationTasks = DataManager.loadExamPrepTasks();
        this.appointmentTasks = DataManager.loadAppointmentTasks();
        this.administrativeTasks = DataManager.loadAdminTasks();
        this.courses = DataManager.loadCourses();
    }

    /**
     * Gets the list of homework tasks
     * @return list of homework tasks
     */
    public List<HomeworkTask> getHomeworkTasks() {
        return homeworkTasks;
    }

    /**
     * Gets the list of project tasks
     * @return list of project tasks
     */
    public List<ProjectTask> getProjectTasks() {
        return projectTasks;
    }

    /**
     * Gets the list of exam preparation tasks
     * @return list of exam preparation tasks
     */
    public List<ExamPreparationTask> getExamPreparationTasks() {
        return examPreparationTasks;
    }

    /**
     * Gets the list of appointment tasks
     * @return list of appointment tasks
     */
    public List<AppointmentTask> getAppointmentTasks() {
        return appointmentTasks;
    }

    /**
     * Gets the list of administrative tasks
     * @return list of administrative tasks
     */
    public List<AdministrativeTask> getAdministrativeTasks() {
        return administrativeTasks;
    }

    /**
     * Gets the list of courses
     * @return list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Creates a new HomeworkTask. Adds the task in the list homeworkTasks and saves it
     * in the JSON file homework_tasks
     * @param title title of the task
     * @param description description of the task
     * @param deadline deadline to complete the task
     * @param priority priority of the task
     * @param estimatedHours estimated hours to complete the task
     * @param reminder reminder for the task
     * @param course course the task is related to
     * @param referenceChapter the chapter the task refers to
     * @return HomeworkTask
     */
    public HomeworkTask createHomeworkTask(String title, String description, LocalDate deadline, Priority priority,
                                           int estimatedHours, Reminder reminder, Course course,
                                           String referenceChapter) {
        HomeworkTask homeworkTask = new HomeworkTask(title, description, deadline, priority, estimatedHours,
                reminder, course, referenceChapter);
        homeworkTasks.add(homeworkTask);
        DataManager.saveHomeworkTasks(homeworkTasks);
        return homeworkTask;
    }

    /**
     * Creates a new ProjectTask. Adds the task in the list projectTasks and saves it
     * in the JSON file project_tasks
     * @param title title of the task
     * @param description description of the task
     * @param deadline deadline to complete the task
     * @param priority priority of the task
     * @param estimatedHours estimated hours to complete the task
     * @param reminder reminder for the task
     * @param course course the task is related to
     * @return ProjectTask
     */
    public ProjectTask createProjectTask(String title, String description, LocalDate deadline, Priority priority,
                                         int estimatedHours, Reminder reminder, Course course) {
        ProjectTask projectTask = new ProjectTask(title, description, deadline, priority, estimatedHours, reminder,
                course);
        projectTasks.add(projectTask);
        DataManager.saveProjectTasks(projectTasks);
        return projectTask;
    }

    /**
     * Creates a new ExamPreparationTask. Adds the task in the list examPreparationTasks and saves it
     * in the JSON file exam_tasks
     * @param title title of the task
     * @param description description of the task
     * @param deadline deadline to complete the task
     * @param priority priority of the task
     * @param estimatedHours estimated hours to complete the task
     * @param reminder reminder for the task
     * @param course course the task is related to
     * @param examDate the date of the exam
     * @return ExamPreparationTask
     */
    public ExamPreparationTask createExamPreparationTask(String title, String description, LocalDate deadline,
                                                         Priority priority, int estimatedHours, Reminder reminder,
                                                         Course course, LocalDate examDate) {
        ExamPreparationTask examPreparationTask = new ExamPreparationTask(title, description, deadline, priority,
                estimatedHours, reminder, course, examDate);
        examPreparationTasks.add(examPreparationTask);
        DataManager.saveExamPreparationTasks(examPreparationTasks);
        return examPreparationTask;
    }

    /**
     * Creates a new AppointmentTask. Adds the task in the list appointmentTasks and saves it
     * in the JSON file appointment_tasks
     * @param title title of the task
     * @param description description of the task
     * @param deadline the date of the appointment in this case
     * @param priority priority of the task
     * @param estimatedHours estimated hours to complete the task
     * @param reminder reminder for the task
     * @param location location the meeting takes place
     * @param contactPerson the person you meet in the appointment
     * @return AppointmentTask
     */
    public AppointmentTask createAppointmentTask(String title, String description, LocalDate deadline, Priority priority,
                                                 int estimatedHours, Reminder reminder, String location,
                                                 String contactPerson, LocalTime appointmentTime) {
        AppointmentTask appointmentTask = new AppointmentTask(title, description, deadline, priority, estimatedHours,
                reminder, location, contactPerson, appointmentTime);
        appointmentTasks.add(appointmentTask);
        DataManager.saveAppointmentTasks(appointmentTasks);
        return appointmentTask;
    }

    /**
     * Creates a new AdministrativeTask. Adds the task in the list administrativeTasks and saves it
     * in the JSON file administrative_tasks
     * @param title title of the task
     * @param description description of the task
     * @param deadline deadline to complete the task
     * @param priority priority of the task
     * @param estimatedHours estimated hours to complete the task. Note: here usually 0
     * @param reminder reminder of the task
     * @param location where you get the document from
     * @param documentName name of the document
     * @return AdministrativeTask
     */
    public AdministrativeTask createAdministrativeTask(String title, String description, LocalDate deadline,
                                                       Priority priority, int estimatedHours, Reminder reminder,
                                                       String location, String documentName) {
        AdministrativeTask administrativeTask = new AdministrativeTask(title, description, deadline, priority,
                estimatedHours, reminder, location, documentName);
        administrativeTasks.add(administrativeTask);
        DataManager.saveAdministrativeTasks(administrativeTasks);
        return administrativeTask;
    }

    /**
     * Edit a homework task. Check if the id exists in the list, and make the given changes
     * @param id id of the task we want to edit
     * @param title title of the task
     * @param description description of the task
     * @param deadline deadline to complete the task
     * @param priority priority of the task
     * @param estimatedHours estimated hours to complete the task
     * @param reminder reminder of the task
     * @param course course the task is related to
     * @param referenceChapter reference chapter of the task
     */
    public void editHomeworkTask(String id, String title, String description, LocalDate deadline, Priority priority,
                                 int estimatedHours, Reminder reminder, Status status, Course course, String referenceChapter) {
        for (HomeworkTask task : homeworkTasks) {
            if (task.getId().equals(id)) {
                task.setTitle(title);
                task.setDescription(description);
                task.setDeadline(deadline);
                task.setPriority(priority);
                task.setEstimatedHours(estimatedHours);
                task.setReminder(reminder);
                task.setStatus(status);
                task.setCourse(course);
                task.setReferenceChapter(referenceChapter);
            }
        }
        DataManager.saveHomeworkTasks(homeworkTasks);
    }

    /**
     * Edit a project task. Check if the id exists in the list, and make the given changes
     * @param id id of the task we want to edit
     * @param title title of the task
     * @param description description of the task
     * @param deadline deadline to complete the task
     * @param priority priority of the task
     * @param estimatedHours estimated hours to complete the task
     * @param reminder reminder of the task
     * @param course course the task is related to
     */
    public void editProjectTask(String id, String title, String description, LocalDate deadline, Priority priority,
                                int estimatedHours, Reminder reminder, Status status, Course course) {
        for (ProjectTask task : projectTasks) {
            if (task.getId().equals(id)) {
                task.setTitle(title);
                task.setDescription(description);
                task.setDeadline(deadline);
                task.setPriority(priority);
                task.setEstimatedHours(estimatedHours);
                task.setReminder(reminder);
                task.setStatus(status);
                task.setCourse(course);
            }
        }
        DataManager.saveProjectTasks(projectTasks);
    }

    public void editExamPreparationTask(String id, String title, String description, LocalDate deadline,
                                        Priority priority, int estimatedHours, Reminder reminder, Status status,
                                        Course course, LocalDate examDate) {
        for (ExamPreparationTask task : examPreparationTasks) {
            if (task.getId().equals(id)) {
                task.setTitle(title);
                task.setDescription(description);
                task.setDeadline(deadline);
                task.setPriority(priority);
                task.setEstimatedHours(estimatedHours);
                task.setReminder(reminder);
                task.setStatus(status);
                task.setCourse(course);
                task.setExamDate(examDate);
            }
        }
        DataManager.saveExamPreparationTasks(examPreparationTasks);
    }

    public void editAppointmentTask(String id, String title, String description, LocalDate deadline, Priority priority,
                                    int estimatedHours, Reminder reminder, Status status, String location,
                                    String contactPerson, LocalTime appointmentTime) {
        for (AppointmentTask task : appointmentTasks) {
            if (task.getId().equals(id)) {
                task.setTitle(title);
                task.setDescription(description);
                task.setDeadline(deadline);
                task.setPriority(priority);
                task.setEstimatedHours(estimatedHours);
                task.setReminder(reminder);
                task.setStatus(status);
                task.setLocation(location);
                task.setContactPerson(contactPerson);
                task.setAppointmentTime(appointmentTime);
            }
        }
        DataManager.saveAppointmentTasks(appointmentTasks);
    }

    public void editAdministrativeTask(String id, String title, String description, LocalDate deadline, Priority priority,
                                       int estimatedHours, Reminder reminder, Status status, String location,
                                       String documentName) {
        for (AdministrativeTask task : administrativeTasks) {
            if (task.getId().equals(id)) {
                task.setTitle(title);
                task.setDescription(description);
                task.setDeadline(deadline);
                task.setPriority(priority);
                task.setStatus(status);
                task.setEstimatedHours(estimatedHours);
                task.setReminder(reminder);
                task.setLocation(location);
                task.setDocumentName(documentName);
            }
        }
        DataManager.saveAdministrativeTasks(administrativeTasks);
    }

    /**
     * Check if the given task is in any of the list and delete it
     * @param id id of the task
     * @return true if task deleted, false if not found.
     */
    public boolean deleteTask(String id) {
        if (homeworkTasks.removeIf(task -> task.getId().equals(id))) {
            DataManager.saveHomeworkTasks(homeworkTasks);
            return true;
        }
        if (projectTasks.removeIf(task -> task.getId().equals(id))) {
            DataManager.saveProjectTasks(projectTasks);
            return true;
        }
        if (examPreparationTasks.removeIf(task -> task.getId().equals(id))) {
            DataManager.saveExamPreparationTasks(examPreparationTasks);
            return true;
        }
        if (appointmentTasks.removeIf(task -> task.getId().equals(id))) {
            DataManager.saveAppointmentTasks(appointmentTasks);
            return true;
        }
        if (administrativeTasks.removeIf(task -> task.getId().equals(id))) {
            DataManager.saveAdministrativeTasks(administrativeTasks);
            return true;
        }
        return false;
    }

    /**
     * Saves all tasks
     */
    public void saveAllTasks() {
        DataManager.saveHomeworkTasks(homeworkTasks);
        DataManager.saveProjectTasks(projectTasks);
        DataManager.saveExamPreparationTasks(examPreparationTasks);
        DataManager.saveAppointmentTasks(appointmentTasks);
        DataManager.saveAdministrativeTasks(administrativeTasks);
    }

    /**
     * Changes the status of a task, from PENDING to EXPIRES SOON,
     * and finally DONE
     * The method searches every list to find if there is a task with the given id
     * @param id id of the task we want to change it status
     * @param status status we want to give to task
     * @return true if the task exists and change happens, else false
     */
    public boolean changeStatus(String id, Status status) {
        for (HomeworkTask task : homeworkTasks) {
            if (task.getId().equals(id)) {
                task.setStatus(status);
                DataManager.saveHomeworkTasks(homeworkTasks);
                return true;
            }
        }
        for (ProjectTask task : projectTasks) {
            if (task.getId().equals(id)) {
                task.setStatus(status);
                DataManager.saveProjectTasks(projectTasks);
                return true;
            }
        }
        for (ExamPreparationTask task : examPreparationTasks) {
            if (task.getId().equals(id)) {
                task.setStatus(status);
                DataManager.saveExamPreparationTasks(examPreparationTasks);
                return true;
            }
        }
        for (AppointmentTask task : appointmentTasks) {
            if (task.getId().equals(id)) {
                task.setStatus(status);
                DataManager.saveAppointmentTasks(appointmentTasks);
                return true;
            }
        }
        for (AdministrativeTask task : administrativeTasks) {
            if (task.getId().equals(id)) {
                task.setStatus(status);
                DataManager.saveAdministrativeTasks(administrativeTasks);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new course in the list of courses
     * @param course the new course we want to add
     */
    public void addCourse(Course course) {
        courses.add(course);
        DataManager.saveCourses(courses);
    }

    /**
     * Removes a course from the list of courses
     * @param course the course we want to delete
     */
    public void deleteCourse(Course course) {
        courses.remove(course);
        DataManager.saveCourses(courses);
    }
}