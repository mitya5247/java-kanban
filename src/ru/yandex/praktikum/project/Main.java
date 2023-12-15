package ru.yandex.praktikum.project;

import ru.yandex.praktikum.project.engine.*;
import ru.yandex.praktikum.project.store.*;


public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager managerTask = new InMemoryTaskManager();

        Managers manager = new Managers(managerTask);

        SubTask subTask = new SubTask("1", "1", "DONE");
        SubTask subTask1 = new SubTask("2", "2", "NEW");
        SubTask subTask2 = new SubTask("3", "3", "DONE");
        SubTask subTask3 = new SubTask("4", "4", "DONE");
        SubTask subTask4 = new SubTask("5", "5", "DONE");
        SubTask subTask5 = new SubTask("6", "6", "DONE");
        SubTask subTask6 = new SubTask("7", "7", "DONE");
        SubTask subTask7 = new SubTask("8", "8", "DONE");
        SubTask subTask8 = new SubTask("9", "9", "DONE");
        SubTask subTask9 = new SubTask("10", "10", "DONE");
        SubTask subTask10 = new SubTask("11", "11", "DONE");
        SubTask subTask11 = new SubTask("12", "12", "DONE");
        SubTask subTask12 = new SubTask("13", "13", "DONE");
        SubTask subTask13 = new SubTask("14", "15", "DONE");
        SubTask subTask14 = new SubTask("14", "14", "DONE");
        SubTask subTask15 = new SubTask("15", "15", "DONE");
        SubTask subTask16 = new SubTask("16", "16", "DONE");
        SubTask subTask17 = new SubTask("16", "16", "DONE");
        SubTask subTask18 = new SubTask("16", "16", "DONE");
        Task task = new Task("17", "17", "NEW");


        Epic epic = new Epic("14", "14");
        Epic epic1 = new Epic("15", "15");


        managerTask.createSubTask(subTask);
        managerTask.createSubTask(subTask1);
        managerTask.createSubTask(subTask2);
        managerTask.createSubTask(subTask3);
        managerTask.createSubTask(subTask4);
        managerTask.createSubTask(subTask5);
        managerTask.createSubTask(subTask6);
        managerTask.createSubTask(subTask7);
        managerTask.createSubTask(subTask8);
        managerTask.createSubTask(subTask9);
        managerTask.createSubTask(subTask10);
        managerTask.createSubTask(subTask11);
        managerTask.createSubTask(subTask12);
        managerTask.createSubTask(subTask13);
        managerTask.createSubTask(subTask14);
        managerTask.createSubTask(subTask15);
        managerTask.createSubTask(subTask16);
        managerTask.createSubTask(subTask17);
        managerTask.createSubTask(subTask18);
        managerTask.createTask(task);


        managerTask.createEpic(epic);
        managerTask.createEpic(epic1);


        managerTask.putSubTaskToEpic(epic, subTask);
        managerTask.putSubTaskToEpic(epic, subTask1);
        managerTask.putSubTaskToEpic(epic1, subTask2);

        managerTask.getSubTask(2);
        managerTask.getSubTask(3);
        managerTask.getSubTask(4);
        managerTask.getSubTask(5);
        managerTask.getSubTask(6);
        managerTask.getSubTask(7);
        managerTask.getSubTask(8);
        managerTask.getSubTask(9);
        managerTask.getSubTask(10);
        managerTask.getSubTask(11);
        managerTask.getSubTask(12);
        managerTask.getSubTask(13);
        managerTask.getSubTask(14);
        managerTask.getSubTask(15);
        managerTask.getSubTask(16);
        managerTask.getSubTask(17);
        managerTask.getSubTask(18);
        managerTask.getEpic(epic.getId());
        managerTask.getTask(task.getId());

        System.out.println("История последних 10 задач: " + Managers.getDefaultHistory());
    }
}
