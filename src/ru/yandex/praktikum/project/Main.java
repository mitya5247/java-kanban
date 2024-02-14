package ru.yandex.praktikum.project;

import ru.yandex.praktikum.project.engine.*;
import ru.yandex.praktikum.project.exceptions.SaveException;
import ru.yandex.praktikum.project.store.*;

import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager oldManager = new InMemoryTaskManager();

        Task task0 = new Task("4", "4", "NEW", 15, LocalDateTime.of(2024, 1, 1, 00, 00));
        Task task1 = new Task("5", "5", "NEW", 20, LocalDateTime.of(2024, 2, 1, 00, 00));

        SubTask subTask1 = new SubTask("2", "2", "IN_PROGRESS", 15, LocalDateTime.of(2024, 1, 1, 00, 00));
        SubTask subTask2 = new SubTask("3", "3", "DONE", 20, LocalDateTime.of(2024, 2, 1, 00, 00));
        SubTask subTask3 = new SubTask("3", "3", "DONE", 20, LocalDateTime.of(2024, 3, 1, 00, 00));

        Epic epic = new Epic("14", "14");
        Epic epic1 = new Epic("15", "15");


        oldManager.createTask(task0);
        oldManager.createTask(task1);

        oldManager.createSubTask(subTask1);
        oldManager.createSubTask(subTask2);
        oldManager.createSubTask(subTask3);

        oldManager.createEpic(epic);
        oldManager.createEpic(epic1);

        oldManager.putSubTaskToEpic(epic, subTask1);
        oldManager.putSubTaskToEpic(epic, subTask2);
        oldManager.putSubTaskToEpic(epic, subTask3);



        System.out.println(oldManager.getPrioritizedTasks());

    }
}
