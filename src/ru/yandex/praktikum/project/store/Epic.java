package ru.yandex.praktikum.project.store;

import java.util.ArrayList;

public class Epic extends Task {


    protected String status = "NEW";


    protected ArrayList<Integer> subTasksID = new ArrayList<>();


    public Epic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ArrayList<Integer> getSubTasksID() {
        return subTasksID;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        if (!(subTasksID.isEmpty())) {
            return "Epic{" +
                    "id='" + id + '\'' +
                    "name='" + name + '\'' +
                    "subTasksID=" + subTasksID + '\'' +
                    "status='" + status + '\'' +
                    '}';
        } else {
            return "Epic{" +
                    "id='" + id + '\'' +
                    "name='" + name + '\'' +
                    "status='" + status + '\'' +
                    '}';
        }
    }

}
