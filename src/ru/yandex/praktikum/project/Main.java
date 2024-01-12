package ru.yandex.praktikum.project;

import ru.yandex.praktikum.project.engine.*;
import ru.yandex.praktikum.project.store.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager managerTask = new InMemoryTaskManager();


        Managers manager = new Managers(managerTask);

        SubTask subTask = new SubTask("1", "1", "DONE");
        SubTask subTask1 = new SubTask("2", "2", "NEW");
        SubTask subTask2 = new SubTask("3", "3", "NEW");

        Task task = new Task("4", "4", "NEW");
        Task task1 = new Task("5", "5", "NEW");

        Task task3 = new Task("5", "5", "NEW");
        Task task4 = new Task("5", "5", "NEW");
        Task task5 = new Task("5", "5", "NEW");
        Task task6 = new Task("5", "5", "NEW");
        Task task7 = new Task("5", "5", "NEW");






        Epic epic = new Epic("14", "14");
        Epic epic1 = new Epic("15", "15");


        managerTask.createSubTask(subTask);
        managerTask.createSubTask(subTask1);
        managerTask.createSubTask(subTask2);

        managerTask.createTask(task);
        managerTask.createTask(task1);
        managerTask.createTask(task3);
        managerTask.createTask(task4);
        managerTask.createTask(task5);
        managerTask.createTask(task6);
        managerTask.createTask(task7);







        managerTask.createEpic(epic);
        managerTask.createEpic(epic1);


        managerTask.putSubTaskToEpic(epic, subTask);
        managerTask.putSubTaskToEpic(epic, subTask1);
        managerTask.putSubTaskToEpic(epic, subTask2);


        managerTask.getSubTask(subTask.getId());
        managerTask.getSubTask(subTask1.getId());
        managerTask.getSubTask(subTask2.getId());
        managerTask.getTask(task.getId()); // id 4
        managerTask.getTask(task1.getId()); // удалился, так как больше 10 список в листе
        managerTask.getTask(task3.getId());
        managerTask.getTask(task4.getId());
        managerTask.getTask(task5.getId());
        managerTask.getTask(task6.getId());
        managerTask.getTask(task7.getId());
        managerTask.getEpic(epic.getId());
        managerTask.getEpic(epic1.getId());

        System.out.println(Managers.getDefaultHistory().size);

        System.out.println("До удаления " + Managers.getDefaultHistory().toStringNew());

        Managers.getDefaultHistory().remove(1);
        Managers.getDefaultHistory().remove(2);
        Managers.getDefaultHistory().remove(3);
        Managers.getDefaultHistory().remove(4);
        Managers.getDefaultHistory().remove(5);
        Managers.getDefaultHistory().remove(6);
        Managers.getDefaultHistory().remove(7);
        Managers.getDefaultHistory().remove(8);
        Managers.getDefaultHistory().remove(9);
        Managers.getDefaultHistory().remove(10);
        Managers.getDefaultHistory().remove(11);
   //     Managers.getDefaultHistory().remove(12);


        System.out.println("После удаления " + Managers.getDefaultHistory().toStringNew());




        System.out.println(Managers.getDefaultHistory());


        System.out.println(Managers.getDefaultHistory().size);

    }
}
