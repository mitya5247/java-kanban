package ru.yandex.praktikum.project.store;

import java.util.ArrayList;

public class Epic extends Task {


    protected String status = "NEW";


    protected ArrayList<Integer> subTasksId = new ArrayList<>();


    public Epic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ArrayList<Integer> getSubTasksId() {
        return subTasksId;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        if (!(subTasksId.isEmpty())) {
            return "Epic{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", subTasksId=" + subTasksId + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        } else {
            return "Epic{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

}
