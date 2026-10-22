package com.taskmanager.storage;

import com.google.gson.*;
import com.taskmanager.model.*;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String DATA_DIR = "src/main/java/com/taskmanager/storage/";

    private static final String HOMEWORK_FILE = DATA_DIR + "homework_tasks.json";
    private static final String PROJECT_FILE = DATA_DIR + "project_tasks.json";
    private static final String EXAM_FILE = DATA_DIR + "exam_tasks.json";
    private static final String APPOINTMENT_FILE = DATA_DIR + "appointment_tasks.json";
    private static final String ADMIN_FILE = DATA_DIR + "administrative_tasks.json";
    private static final String COURSE_FILE = DATA_DIR + "courses.json";

    /**
     * Saves a list of homework tasks in JSON file
     * @param tasks list of homework tasks
     */
    public static void saveHomeworkTasks(List<HomeworkTask> tasks) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();

        try (FileWriter writer = new FileWriter(HOMEWORK_FILE)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error during save!");
        }

    }

    /**
     * Saves a list of project tasks in JSON file
     * @param tasks list of project tasks
     */
    public static void saveProjectTasks(List<ProjectTask> tasks) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();

        try (FileWriter writer = new FileWriter(PROJECT_FILE)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error during save!");
        }
    }

    /**
     * Saves a list of exam preparation tasks
     * @param tasks a list of exam preparation tasks
     */
    public static void saveExamPreparationTasks(List<ExamPreparationTask> tasks) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();

        try (FileWriter writer = new FileWriter(EXAM_FILE)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error during save!");
        }
    }

    /**
     * Saves a list of appointment tasks
     * @param tasks list of appointment tasks
     */
    public static void saveAppointmentTasks(List<AppointmentTask> tasks) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();

        try (FileWriter writer = new FileWriter(APPOINTMENT_FILE)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error during save!");
        }
    }

    /**
     * Saves a list of administrative tasks
     * @param tasks list of administrative tasks
     */
    public static void saveAdministrativeTasks(List<AdministrativeTask> tasks) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();

        try (FileWriter writer = new FileWriter(ADMIN_FILE)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error during save!");
        }
    }

    /**
     * Saves a list of courses in JSON file
     * @param courses the list of courses
     */
    public static void saveCourses(List<Course> courses) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(COURSE_FILE)) {
            gson.toJson(courses, writer);
        } catch (IOException e) {
            System.out.println("Error during save!");
        }
    }

    /**
     * Loads a list of homework tasks that is read from a JSON file
     * @return list of homework tasks
     */
    public static List<HomeworkTask> loadHomeworkTasks() {
        File file = new File(HOMEWORK_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<HomeworkTask>>(){}.getType();
            List<HomeworkTask> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>(); // return empty list to avoid null pointer exception
        } catch (IOException e) {
            System.out.println("Error during loading!");
            return new ArrayList<>();
        }
    }

    /**
     * Loads a list of project tasks that is read from a JSON file
     * @return list of project tasks
     */
    public static List<ProjectTask> loadProjectTasks() {
        File file = new File(PROJECT_FILE);
        if(!file.exists()) {
            return new ArrayList<>();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<ProjectTask>>(){}.getType();
            List<ProjectTask> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error during loading!");
            return new ArrayList<>();
        }
    }

    /**
     * Loads a list of exam preparation tasks that is read from a JSON file
     * @return list of exam preparation tasks
     */
    public static List<ExamPreparationTask> loadExamPrepTasks() {
        File file = new File(EXAM_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();

        try (FileReader reader = new FileReader(EXAM_FILE)) {
            Type listType = new TypeToken<List<ExamPreparationTask>>(){}.getType();
            List<ExamPreparationTask> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error during loading!");
            return new ArrayList<>();
        }
    }

    /**
     * Loads a list of appointment tasks that is read from a JSON file
     * @return list of appointment tasks
     */
    public static List<AppointmentTask> loadAppointmentTasks() {
        File file = new File(APPOINTMENT_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();

        try (FileReader reader = new FileReader(APPOINTMENT_FILE)) {
            Type listType = new TypeToken<List<AppointmentTask>>(){}.getType();
            List<AppointmentTask> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error during loading!");
            return new ArrayList<>();
        }
    }

    /**
     * Loads a list of administrative tasks that is read from a JSON file
     * @return list of administrative tasks
     */
    public static List<AdministrativeTask> loadAdminTasks() {
        File file = new File(ADMIN_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();

        try (FileReader reader = new FileReader(ADMIN_FILE)) {
            Type listType = new TypeToken<List<AdministrativeTask>>(){}.getType();
            List<AdministrativeTask> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error during loading!");
            return new ArrayList<>();
        }
    }

    /**
     * Loads a list of courses that is read from a JSON file
     * @return list of courses
     */
    public static List<Course> loadCourses() {
        File file = new File(COURSE_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader(COURSE_FILE)) {
            Type listType = new TypeToken<List<Course>>(){}.getType();
            List<Course> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error during loading!");
            return new ArrayList<>();
        }
    }
}
