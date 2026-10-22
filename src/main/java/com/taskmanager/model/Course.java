package com.taskmanager.model;

public class Course {
    private String name;

    /**
     * Constructs a course
     * @param name name of the course
     */
    public Course(String name) {
        this.name = name;
    }

    /**
     * Sets the name of the course
     * @param name name of the course
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the course
     * @return the name of the course
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // Checks if obj is null or if we compare two objects of a different class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Course course = (Course) obj;
        return name.equals(course.name);
    }

    @Override
    public String toString() {
        return name;
    }
}