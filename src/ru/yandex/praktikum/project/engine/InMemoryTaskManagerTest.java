package ru.yandex.praktikum.project.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

public class InMemoryTaskManagerTest extends TaskManagerTest {

    InMemoryTaskManager managerTask = new InMemoryTaskManager();

    @Override
    @Test
    public void shouldCreateTask() {
        super.shouldCreateTask();
    }
    @Override
    @Test
    public void shouldCreateSubTask() {
        super.shouldCreateSubTask();
    }

    @Override
    @Test
    public void shouldCreateEpic() {
        super.shouldCreateEpic();
    }

}
