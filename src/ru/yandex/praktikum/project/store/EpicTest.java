package ru.yandex.praktikum.project.store;

import ru.yandex.praktikum.project.engine.InMemoryTaskManager;
import ru.yandex.praktikum.project.engine.ManagerTask;
import ru.yandex.praktikum.project.engine.Managers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

class EpicTest {

    @Test
    public void shouldBeEmpty() {
        Epic epic = new Epic("Купить молоко", "Пойти в магазин");

        InMemoryTaskManager managerTask = new InMemoryTaskManager();
        Managers managers = new Managers(managerTask);

        managers.getDefault().createEpic(epic);

        ArrayList<Integer> subTasks = new ArrayList<>();
        Assertions.assertEquals(subTasks, epic.getSubTasksId());
    }

    @Test
    public void statusShouldBeNew() { // проверка статуса эпика, что он NEW
        Epic epic = new Epic("Купить молоко", "Пойти в магазин");

        SubTask subTask0 = new SubTask("1", "1", "NEW", 20, "15-00");
        SubTask subTask1 = new SubTask("2", "2", "NEW", 20, "15-00");

        InMemoryTaskManager managerTask = new InMemoryTaskManager();
        Managers managers = new Managers(managerTask);

        managers.getDefault().createSubTask(subTask0);
        managers.getDefault().createSubTask(subTask1);
        managers.getDefault().createEpic(epic);

        managers.getDefault().putSubTaskToEpic(epic, subTask0);
        managers.getDefault().putSubTaskToEpic(epic, subTask1);

        String expected = "NEW";

        Assertions.assertEquals(expected, epic.getStatus());

    }

    @Test
    public void statusShouldBeDone() { // проверка статуса эпика при подзадач со статусом DONE
        Epic epic = new Epic("Купить молоко", "Пойти в магазин");

        SubTask subTask0 = new SubTask("1", "1", "DONE", 20, "15-00");
        SubTask subTask1 = new SubTask("2", "2", "DONE", 20, "15-00");

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
    public void statusShouldBeInProgress() { // проверка статуса эпика при подзадач со статусом DONE, NEW
        Epic epic = new Epic("Купить молоко", "Пойти в магазин");

        SubTask subTask0 = new SubTask("1", "1", "DONE", 20, "15-00");
        SubTask subTask1 = new SubTask("2", "2", "NEW", 20, "15-00");

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
    public void statusShouldBeInProgressWithSubtasksInProgress() { // проверка статуса эпика при подзадач со статусом IN_PROGRESS
        Epic epic = new Epic("Купить молоко", "Пойти в магазин");

        SubTask subTask0 = new SubTask("1", "1", "IN_PROGRESS", 20, "15-00");
        SubTask subTask1 = new SubTask("2", "2", "IN_PROGRESS", 20, "15-00");

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