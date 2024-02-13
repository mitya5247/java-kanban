package ru.yandex.praktikum.project.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class HistoryManagerTest {

    static ManagerTask manager;
    static Managers managersControl;

    static InMemoryHistoryManager managerHistory;

    @BeforeEach
    public void createManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Managers managers = new Managers(taskManager);
        manager = managers.getDefault();
        managersControl = managers;

        managerHistory = taskManager.historyManager;
    }

    @Test
    public void shouldAddTaskToHistory() {

        Task task0 = new Task("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));
        SubTask subTask0 = new SubTask("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));
        SubTask subTask1 = new SubTask("1", "1", "NEW", 20, LocalDateTime.of(2024, 2, 1, 0, 0));

        Epic epic0 = new Epic("1", "1");


        manager.createTask(task0);
        manager.createSubTask(subTask0);
        manager.createSubTask(subTask1);
        manager.createEpic(epic0);

        Assertions.assertEquals(0, managerHistory.getHistory().size()); // пустая история вызовов задач


        manager.getTask(task0.getId());
        manager.getEpic(epic0.getId());
        manager.getSubTask(subTask0.getId());
        manager.getSubTask(subTask1.getId());


        Assertions.assertEquals(4, managerHistory.getHistory().size());

        // дублирование сабтаска
        manager.getSubTask(subTask0.getId());

        Assertions.assertEquals(4, managerHistory.getHistory().size());

        //удаление середины
        managerHistory.remove(epic0.getId());

        Assertions.assertEquals(3, managerHistory.getHistory().size()); // дублирование


        // удаление начала
        managerHistory.remove(task0.getId());

        Assertions.assertEquals(2, managerHistory.getHistory().size());


        // удаление конца списка
        managerHistory.remove(subTask0.getId());

        Assertions.assertEquals(1, managerHistory.getHistory().size());


    }

    @Test
    public void shouldGetHistory() {
        Task task0 = new Task("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));
        SubTask subTask0 = new SubTask("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));
        Epic epic0 = new Epic("1", "1");

        manager.createTask(task0);
        manager.createSubTask(subTask0);
        manager.createEpic(epic0);

        Assertions.assertEquals(0, managerHistory.getHistory().size()); // пустая история

        manager.getTask(task0.getId());
        manager.getEpic(epic0.getId());
        manager.getSubTask(subTask0.getId());

        ArrayList<Task> listTasks = new ArrayList<>();

        listTasks.add(task0);
        listTasks.add(epic0);
        listTasks.add(subTask0);

        Assertions.assertEquals(listTasks, managerHistory.getHistory()); // стандартная работа метода
    }

    @Test
    public void shouldRemoveTaskFromHistory() {
        Task task0 = new Task("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));
        SubTask subTask0 = new SubTask("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));
        SubTask subTask1 = new SubTask("1", "1", "NEW", 20, LocalDateTime.of(2024, 2, 1, 0, 0));

        Epic epic0 = new Epic("1", "1");

        manager.createTask(task0);
        manager.createSubTask(subTask0);
        manager.createSubTask(subTask1);
        manager.createEpic(epic0);

        Assertions.assertEquals(0, managerHistory.getHistory().size()); // пустая история

        manager.getTask(task0.getId());
        manager.getEpic(epic0.getId());
        manager.getSubTask(subTask0.getId());
        manager.getSubTask(subTask1.getId());

        // дубирование задачи
        manager.getSubTask(subTask1.getId());

        Assertions.assertEquals(4, managerHistory.getHistory().size()); // пустая история

        // удаление задачи
        managerHistory.remove(subTask1.getId());

        Assertions.assertEquals(3, managerHistory.getHistory().size()); // пустая история

        // попытка удалить несуществующую задачу
        boolean flag = managerHistory.getHistory().contains(subTask1);
        managerHistory.remove(subTask1.getId());

        Assertions.assertEquals(3, managerHistory.getHistory().size(), "Размер списка не изменился");
        Assertions.assertFalse(flag, "Задачи не существует");
    }
}
