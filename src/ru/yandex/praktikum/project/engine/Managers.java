package ru.yandex.praktikum.project.engine;

public class Managers {
    InMemoryTaskManager managerTask;

    public Managers() {
        managerTask = new InMemoryTaskManager();
    }

    public ManagerTask getDefault() {
        return managerTask;
    }
}
