package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.store.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    ArrayList<Task> tasksListHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        tasksListHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        if (!tasksListHistory.isEmpty()) {
            if (tasksListHistory.size() > 10) {
                ArrayList<Integer> idArray = new ArrayList<>();
                for (int i = 0; i < tasksListHistory.size() - 10; i++) {
                    idArray.add(tasksListHistory.get(i).getId());
                    if (tasksListHistory.size() == 10) {
                        break;
                    }
                }
                for (int i = 0; i < idArray.size(); i++) {
                    tasksListHistory.remove(0);
                }
            }
        } else {
            System.out.println("История пуста");
        }
        return tasksListHistory;
    }

    @Override
    public String toString() {
        return this.getHistory().toString();
    }
}
