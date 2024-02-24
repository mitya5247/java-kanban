package ru.yandex.praktikum.project.engine;

import java.util.List;

public class Managers {
    InMemoryTaskManager managerTask;
    static FileBackedTasksManager fileManager;
    static InMemoryHistoryManager managerHistory;

    static HttpTaskManager httpManager;

    public Managers(InMemoryTaskManager managerTask) {
        this.managerTask = managerTask;
        managerHistory = managerTask.historyManager;
    }

    public ManagerTask getDefault() {
        return httpManager;
    }

    public static FileBackedTasksManager getDefaultFileBackedTaskManager() {
        return fileManager;
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return managerHistory;
    }

}
