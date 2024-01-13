package ru.yandex.praktikum.project;

import ru.yandex.praktikum.project.engine.*;
import ru.yandex.praktikum.project.store.*;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager managerTask = new InMemoryTaskManager();


        Managers manager = new Managers(managerTask);

        SubTask subTask = new SubTask("1", "1", "DONE");
        SubTask subTask1 = new SubTask("2", "2", "NEW");
        SubTask subTask2 = new SubTask("3", "3", "NEW");

        Task task = new Task("4", "4", "NEW");
        Task task1 = new Task("5", "5", "NEW");

        Epic epic = new Epic("14", "14");
        Epic epic1 = new Epic("15", "15");


        managerTask.createSubTask(subTask);
        managerTask.createSubTask(subTask1);
        managerTask.createSubTask(subTask2);

        managerTask.createTask(task);
        managerTask.createTask(task1);


        managerTask.createEpic(epic);
        managerTask.createEpic(epic1);

        managerTask.putSubTaskToEpic(epic, subTask);
        managerTask.putSubTaskToEpic(epic, subTask1);
        managerTask.putSubTaskToEpic(epic, subTask2);

        managerTask.getSubTask(subTask.getId());
        managerTask.getSubTask(subTask1.getId());
        managerTask.getSubTask(subTask2.getId());
        managerTask.getTask(task.getId());
        managerTask.getTask(task1.getId());
        managerTask.getEpic(epic.getId());
        managerTask.getEpic(epic1.getId());
        managerTask.getSubTask(subTask.getId());
        managerTask.getEpic(epic.getId());


        Managers.getDefaultHistory().remove(task.getId());
        Managers.getDefaultHistory().remove(epic.getId());

        System.out.println(Managers.getDefaultHistory());

    }
}
