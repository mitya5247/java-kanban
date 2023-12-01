package ru.yandex.praktikum.project;

import ru.yandex.praktikum.project.engine.*;
import ru.yandex.praktikum.project.store.*;


public class Main {

    public static void main(String[] args) {
        ManagerTask managerTask = new ManagerTask();

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


        System.out.println(managerTask.getListSubtasksOfEpic(epic));
        System.out.println(managerTask.getListSubtasksOfEpic(epic1));

        System.out.println("До обновления " + managerTask.getListEpics());

        subTask1.setStatus("DONE"); // меняем статусы
        subTask2.setStatus("NEW");
        managerTask.updateSubTask(subTask1);
        managerTask.updateSubTask(subTask2);
        System.out.println("После обновления " + managerTask.getListEpics());

        managerTask.removeSubTask(subTask1.getId()); // удаляем подзадачу
        managerTask.removeEpic(epic1.getId()); // и эпик


        System.out.println(managerTask.getListSubTasks());
        System.out.println(managerTask.getListEpics());

    }
}
