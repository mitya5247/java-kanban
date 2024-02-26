package ru.yandex.praktikum.project.main.engine;

import ru.yandex.praktikum.project.main.store.Task;

import java.util.List;

public interface HistoryManager {
    public void add(Task task);

    public List<Task> getHistory();

    public void remove(int id);
}
