package ru.yandex.praktikum.project.tests;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.project.engine.HttpTaskManager;
import ru.yandex.praktikum.project.server.KVServer;
import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class HttpTaskManagerTest {
    static HttpTaskManager manager;
    static File file = new File("testNew.csv");

    static KVServer server;

    Gson gson = new Gson();

    @BeforeAll
    public static void createServer() {
        try {
            server = new KVServer();
            server.start();
        } catch (IOException e) {
            System.out.println("Ошибка запуска сервера. Проверьте класс KVServer");
        }
        manager = new HttpTaskManager(file, "http://localhost:8080");

    }

    @Test
    public void shouldSaveTasks() {
        Task task = new Task("1", "1", "NEW", 12, LocalDateTime.of(2023, 2, 12, 14, 3));
        SubTask subTask1 = new SubTask("2", "1", "NEW", 12, LocalDateTime.of(2023, 3, 12, 14, 3));
        Epic epic = new Epic("name", "fsd");


        manager.createTask(task);
        manager.createSubTask(subTask1);
        manager.createEpic(epic);

        manager.save();

        String tasksString = gson.toJson(manager.getTaskMap().get(task.getId())) + ", ";
        String subTasks = gson.toJson(manager.getSubTaskMap().get(subTask1.getId())) + ", ";
        String epics = gson.toJson(manager.getEpicMap().get(epic.getId())) + ", ";

        String tasks = String.join("; ", tasksString, subTasks, epics);

        Assertions.assertEquals(tasks, server.getData().get(manager.getKey()));
    }

    @Test
    public void shouldLoadFromServer() {
        HttpTaskManager newManager = new HttpTaskManager(file, "http://localhost:8080");
        newManager.loadFromServer();
        Assertions.assertEquals(manager.getTaskMap().get(newManager.getKey()), newManager.getTaskMap().get(newManager.getKey()));
        Assertions.assertEquals(manager.getSubTaskMap().get(newManager.getKey()), newManager.getSubTaskMap().get(newManager.getKey()));
        Assertions.assertEquals(manager.getEpicMap().get(newManager.getKey()), newManager.getEpicMap().get(newManager.getKey()));

    }

}
