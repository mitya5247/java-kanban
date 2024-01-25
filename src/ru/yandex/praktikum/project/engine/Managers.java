package ru.yandex.praktikum.project.engine;

import java.util.List;

public class Managers {
    InMemoryTaskManager managerTask;
    static InMemoryHistoryManager managerHistory;

    public Managers(InMemoryTaskManager managerTask) {
        this.managerTask = managerTask;
        managerHistory = managerTask.historyManager;
    }

    public ManagerTask getDefault() {
        return managerTask;
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return managerHistory;
    }

}
