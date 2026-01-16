package entities;

import java.time.LocalDateTime;


public class FitnessClass {
    private int id;
    private String title;
    private String instructorName;
    private LocalDateTime scheduleTime;
    private int capacity;


    // Construct with parametrs
    public FitnessClass(int id, String title, String instructorName, LocalDateTime scheduleTime, int capacity) {
        this.id = id;
        this.title = title;
        this.instructorName = instructorName;
        this.scheduleTime = scheduleTime;
        this.capacity = capacity;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public LocalDateTime getScheduleTime() { return scheduleTime; }
    public void setScheduleTime(LocalDateTime scheduleTime) { this.scheduleTime = scheduleTime; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    @Override
    public String toString() {
        return id + ". " + title + " with " + instructorName + " at " + scheduleTime;
    }
}

