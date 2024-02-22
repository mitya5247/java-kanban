package ru.yandex.praktikum.project.store;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {


    protected int id;
    protected String status = "NEW";


    protected String name;
    protected String description;

    protected int duration;

    protected LocalDateTime startTime;

    protected LocalDateTime endTime;

    public Task(String name, String description, String status, int duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;


        this.startTime = startTime;

        endTime = this.startTime.plusMinutes(duration);
    }

    public Task() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTimeInString() {
        return endTime;
    }

    public LocalDateTime getStartTimeInString() {
        return startTime;
    }
    public int getDuration() {
        return duration;
    }


    @Override
    public String toString() {
        return "Task='{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", duration ='" + duration + '\'' +
                ", startTime = '" + startTime + '\'' +
                ", endTime = '" + endTime + '}';

    }
}
