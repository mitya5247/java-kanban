package ru.yandex.praktikum.project;

import ru.yandex.praktikum.project.engine.*;
import ru.yandex.praktikum.project.store.*;


public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager managerTask = new InMemoryTaskManager();

        SubTask subTask = new SubTask("1", "1", "DONE");
        SubTask subTask1 = new SubTask("2", "2", "NEW");
        SubTask subTask2 = new SubTask("3", "3", "DONE");

        Epic epic = new Epic("4", "4");
        Epic epic1 = new Epic("5", "5");


        managerTask.createSubTask(subTask);
        managerTask.createSubTask(subTask1);
        managerTask.createSubTask(subTask2);

        managerTask.createEpic(epic);
        managerTask.createEpic(epic1);


        managerTask.putSubTaskToEpic(epic, subTask);
        managerTask.putSubTaskToEpic(epic, subTask1);
        managerTask.putSubTaskToEpic(epic1, subTask2);

        managerTask.getSubTask(2);
        managerTask.getSubTask(3);
        managerTask.getEpic(4);

        managerTask.getHistory();

    }
}
