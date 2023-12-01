package ru.yandex.praktikum.project;

import ru.yandex.praktikum.project.engine.*;
import ru.yandex.praktikum.project.store.*;


public class Main {

    public static void main(String[] args) {
        ManagerTask managerTask = new ManagerTask();

        Task task = new Task("4", "4", "NEW");

        managerTask.createTask(task);

        SubTask subTask = new SubTask("1", "1", "DONE");
        SubTask subTask1 = new SubTask("2", "2", "NEW");
        SubTask subTask2 = new SubTask("3", "3", "DONE");

        managerTask.createSubTask(subTask);
        managerTask.createSubTask(subTask1);
        managerTask.createSubTask(subTask2);


        Epic epic = new Epic("Купить компьютер", "Компьютер");

        managerTask.createEpic(epic);

        managerTask.putSubTaskToEpic(epic, subTask);
        managerTask.putSubTaskToEpic(epic, subTask1);
      //  managerTask.putSubTaskToEpic(epic, subTask2);
        subTask1.setStatus("DONE");

        managerTask.updateSubTask(subTask1);

        System.out.println(epic);
        managerTask.removeEpic(epic.getId());
        System.out.println(managerTask.getListSubTasks());



    }
}
