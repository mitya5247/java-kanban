package ru.yandex.praktikum.project;

import ru.yandex.praktikum.project.engine.*;
import ru.yandex.praktikum.project.store.*;


public class Main {

    public static void main(String[] args) {
        ManagerTask managerTask = new ManagerTask();
        Epic epic = new Epic("Сделать ремонт в кваритире", "Проведение ремонта в квартире");

        SubTask subTask = new SubTask("Переклеить обои", "Выбрать цвет для обоев в комнате", "DONE");
        SubTask subTask1 = new SubTask("Повесить светильник", "Светильник над кроватью", "NEW");


        Epic epic1 = new Epic("Купить квартиру", "Квартира, чтобы сдавать");

        SubTask subTask2 = new SubTask("Начать копить денежку", "Чтобы взять ипотеку", "IN_PROGRESS");

        System.out.println("Эпик1 id " + managerTask.createEpic(epic));
        System.out.println("Подзадача 1, Эпик 1, id" + managerTask.createSubTask(subTask));
        System.out.println("Подзадача 2, Эпик 1, id" + managerTask.createSubTask(subTask1));


        System.out.println("Эпик2 id " + managerTask.createEpic(epic1));
        System.out.println("Подзадача 1, Эпик 2, id" + managerTask.createSubTask(subTask2));

        System.out.println("Эпик 1 - статус до update - " + epic);
        System.out.println("Эпик 2 - статус до update  - " + epic1);


        managerTask.putSubTaskToEpic(epic, subTask);
        managerTask.updateEpic(epic);
        managerTask.putSubTaskToEpic(epic, subTask1);
        managerTask.updateEpic(epic);

        managerTask.putSubTaskToEpic(epic1, subTask2);
        managerTask.updateEpic(epic1);


        System.out.println("Эпик 1 - статус после update - " + epic);
        System.out.println("Эпик 2 - статус после update  - " + epic1);


        System.out.println("HashMap epicMap до удаления " + managerTask.epicMap);
        managerTask.removeEpic(1);
        System.out.println("HashMap epicMap после удаления " + managerTask.epicMap);

        System.out.println("HashMap subTaskMap до удаления " + managerTask.subTaskMap);
        managerTask.removeSubTask(2);
        System.out.println("HashMap subTaskMap после удаления " + managerTask.subTaskMap);


    }
}
