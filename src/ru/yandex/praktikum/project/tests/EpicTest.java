package ru.yandex.praktikum.project.tests;

import ru.yandex.praktikum.project.main.engine.InMemoryTaskManager;
import ru.yandex.praktikum.project.main.engine.Managers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.project.main.store.Epic;
import ru.yandex.praktikum.project.main.store.Status;
import ru.yandex.praktikum.project.main.store.SubTask;


import java.time.LocalDateTime;

class EpicTest {

    @Test
    public void shouldBeEmpty() {
        Epic epic = new Epic("Купить молоко", "Пойти в магазин");

        InMemoryTaskManager managerTask = new InMemoryTaskManager();

        managerTask.createEpic(epic);

        Assertions.assertEquals(0, epic.getSubTasksId().size());
        Assertions.assertEquals(String.valueOf(Status.NEW), epic.getStatus());
    }

    @Test
    public void statusShouldBeNew() { // проверка статуса эпика, что он NEW при добавлении подзадач с NEW
        Epic epic = new Epic("Купить молоко", "Пойти в магазин");

        SubTask subTask0 = new SubTask("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 00, 00));
        SubTask subTask1 = new SubTask("2", "2", "NEW", 20, LocalDateTime.of(2024, 2, 1, 00, 00));

        InMemoryTaskManager managerTask = new InMemoryTaskManager();

        managerTask.createSubTask(subTask0);
        managerTask.createSubTask(subTask1);
        managerTask.createEpic(epic);

        managerTask.putSubTaskToEpic(epic, subTask0);
        managerTask.putSubTaskToEpic(epic, subTask1);

        String expected = "NEW";

        Assertions.assertEquals(expected, epic.getStatus());

    }

    @Test
    public void statusShouldBeDone() { // проверка статуса эпика DONE при подзадачах со статусом DONE
        Epic epic = new Epic("Купить молоко", "Пойти в магазин");

        SubTask subTask0 = new SubTask("1", "1", "DONE", 20, LocalDateTime.of(2024, 1, 1, 00, 00));
        SubTask subTask1 = new SubTask("2", "2", "DONE", 20, LocalDateTime.of(2024, 3, 1, 00, 00));

        InMemoryTaskManager managerTask = new InMemoryTaskManager();
        Managers managers = new Managers(managerTask);

        managers.getDefault().createSubTask(subTask0);
        managers.getDefault().createSubTask(subTask1);
        managers.getDefault().createEpic(epic);

        managers.getDefault().putSubTaskToEpic(epic, subTask0);
        managers.getDefault().putSubTaskToEpic(epic, subTask1);

        String expectedDone = "DONE";

        Assertions.assertEquals(expectedDone, epic.getStatus());

    }

    @Test
    public void statusShouldBeInProgress() { // проверка статуса эпика IN_PROGRESS при подзадачах со статусом DONE, NEW
        Epic epic = new Epic("Купить молоко", "Пойти в магазин");

        SubTask subTask0 = new SubTask("1", "1", "DONE", 20, LocalDateTime.of(2024, 1, 1, 00, 00));
        SubTask subTask1 = new SubTask("2", "2", "NEW", 20, LocalDateTime.of(2024, 2, 1, 00, 00));

        InMemoryTaskManager managerTask = new InMemoryTaskManager();
        Managers managers = new Managers(managerTask);

        managers.getDefault().createSubTask(subTask0);
        managers.getDefault().createSubTask(subTask1);
        managers.getDefault().createEpic(epic);

        managers.getDefault().putSubTaskToEpic(epic, subTask0);
        managers.getDefault().putSubTaskToEpic(epic, subTask1);

        String expectedDone = "IN_PROGRESS";

        Assertions.assertEquals(expectedDone, epic.getStatus());

    }

    @Test
    public void statusShouldBeInProgressWithSubtasksInProgress() { // проверка статуса эпика при подзадачах со статусом IN_PROGRESS
        Epic epic = new Epic("Купить молоко", "Пойти в магазин");

        SubTask subTask0 = new SubTask("1", "1", "IN_PROGRESS", 20, LocalDateTime.of(2024, 1, 1, 00, 00));
        SubTask subTask1 = new SubTask("2", "2", "IN_PROGRESS", 20, LocalDateTime.of(2024, 1, 2, 00, 00));

        InMemoryTaskManager managerTask = new InMemoryTaskManager();
        Managers managers = new Managers(managerTask);

        managers.getDefault().createSubTask(subTask0);
        managers.getDefault().createSubTask(subTask1);
        managers.getDefault().createEpic(epic);

        managers.getDefault().putSubTaskToEpic(epic, subTask0);
        managers.getDefault().putSubTaskToEpic(epic, subTask1);

        String expectedDone = "IN_PROGRESS";

        Assertions.assertEquals(expectedDone, epic.getStatus());

    }

}