package ru.yandex.praktikum.project.engine;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.io.File;

public abstract class TaskManagerTest<T extends ManagerTask> {

    static InMemoryTaskManager memoryTaskManager;
    static FileBackedTasksManager fileTaskManager;

    @BeforeAll
    public static void createManagers() {
        InMemoryTaskManager managerTask = new InMemoryTaskManager();

        File file = new File("text.csv");
        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);

        memoryTaskManager = managerTask;
        fileTaskManager = fileManager;
    }

    @Test
    public void shouldCreateTask() {
        Task task = new Task("1", "1", "NEW");
        memoryTaskManager.createTask(task);
        fileTaskManager.createTask(task);

        Assertions.assertEquals(task, memoryTaskManager.getTaskMap().get(task.getId()));
        Assertions.assertEquals(task, fileTaskManager.getTaskMap().get(task.getId()));

    }

    @Test
    public void shouldCreateSubTask() {
        SubTask subTask = new SubTask("1", "1", "NEW");
        memoryTaskManager.createSubTask(subTask);
        fileTaskManager.createSubTask(subTask);

        Assertions.assertEquals(subTask, memoryTaskManager.getSubTaskMap().get(subTask.getId()));
        Assertions.assertEquals(subTask, fileTaskManager.getSubTaskMap().get(subTask.getId()));
    }

    @Test
    public void shouldCreateEpic() {
        Epic epic = new Epic("1", "1");
        memoryTaskManager.createEpic(epic);
        fileTaskManager.createEpic(epic);

        Assertions.assertEquals(epic, memoryTaskManager.getEpicMap().get(epic.getId()));
        Assertions.assertEquals(epic, fileTaskManager.getEpicMap().get(epic.getId()));
    }

    public void shouldGetTask() {
    }

    public void shouldGetSubTask() {
    }

    public void shouldGetEpic() {
    }


    public void shouldUpdateTask() {
    }

    public void shouldUpdateSubTask() {
    }

    public void shouldUpdateEpic() {
    }

    public void shouldHaveEpicInSubTask() {
    }

    public void shouldUpdateStatusOfEpic() {
    }

    public void shouldRemoveTask() {
    }

    public void shouldRemoveSubTask() {
    }

    public void shouldRemoveEpic() {
    }


}
