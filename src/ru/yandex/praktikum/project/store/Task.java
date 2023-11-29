package ru.yandex.praktikum.project.store;

public class Task {

    public int id;
    protected String status = "NEW";


    protected String name;
    protected String description;

    protected Task(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    protected Task() {
    }

    public int getId() {
        return id;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Task='{" +
                "status='" + status + '\'' +
                "name='" + name + '\'' +
                "description='" + description + '\'' + '}';

    }
}
