package ru.yandex.praktikum.project;

import ru.yandex.praktikum.project.engine.*;
import ru.yandex.praktikum.project.store.*;


public class Main {

    public static void main(String[] args) {
        ManagerTask managerTask = new ManagerTask();

        SubTask subTask = new SubTask("1", "1", "DONE");
        SubTask subTask1 = new SubTask("2", "2", "NEW");
        SubTask subTask2 = new SubTask("3", "3", "DONE");

        Epic epic = new Epic("Купить компьютер", "Компьютер");

        managerTask.createSubTask(subTask);
        managerTask.createSubTask(subTask1);
        managerTask.createSubTask(subTask2);

        managerTask.createEpic(epic);



        managerTask.putSubTaskToEpic(epic, subTask);
        managerTask.putSubTaskToEpic(epic, subTask1);
        managerTask.putSubTaskToEpic(epic, subTask2);

        System.out.println(epic);


        subTask1.setStatus("DONE");
        managerTask.updateSubTask(subTask1);


        SubTask subTask3 = new SubTask("4", "4", "NEW");
        managerTask.createSubTask(subTask3);


        managerTask.putSubTaskToEpic(epic, subTask3);


        System.out.println(epic); // статус IN_PROGRESS

/*        managerTask.removeSubTask(5);
        managerTask.removeSubTask(1);
        managerTask.removeSubTask(2);
        managerTask.removeSubTask(3);*/

        System.out.println("Лист "  + managerTask.getListSubtasksOfEpic(epic));



        System.out.println("После удаления subTask" + epic); // статус IN_PROGRESS



        System.out.println(managerTask.subTaskMap);
    }
}
