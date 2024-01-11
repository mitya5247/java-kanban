package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.store.Task;

import java.util.List;

public interface HistoryManager {
    public void add(Task task);

    public List<Task> getHistory();

    public void remove(int id);
}
