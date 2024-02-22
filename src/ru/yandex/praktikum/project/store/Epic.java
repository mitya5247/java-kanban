package ru.yandex.praktikum.project.store;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Epic extends Task {

    protected transient String status = "NEW";

    protected ArrayList<Integer> subTasksId = new ArrayList<>();

    private transient LocalDateTime endTime;


    public Epic(String name, String description) {
        this.name = name;
        this.description = description;
        this.duration = 0;
        this.startTime = LocalDateTime.MIN;
    }


    public ArrayList<Integer> getSubTasksId() {
        return subTasksId;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public LocalDateTime getStartTime(LocalDateTime time) {
        startTime = time;
        return startTime;
    }

    public LocalDateTime getEndTime(LocalDateTime time) {
        endTime = time;
        return endTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setDuration(int dur) {
        duration = dur;
    }




    @Override
    public String toString() {
        if (!(subTasksId.isEmpty())) {
            return "Epic{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", subTasksId=" + subTasksId + '\'' +
                    ", status='" + status + '\'' +
                    ", duration ='" + duration + '\'' +
                    ", startTime = '" + startTime + '\'' +
                    ", endTime = '" + endTime +
                    '}';
        } else {
            return "Epic{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", status='" + status + '\'' +
                    ", duration ='" + duration + '\'' +
                    ", startTime = '" + startTime + '\'' +
                    ", endTime = '" + endTime +
                    '}';
        }
    }

}
