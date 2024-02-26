package ru.yandex.praktikum.project.main.engine;

import ru.yandex.praktikum.project.main.store.Epic;
import ru.yandex.praktikum.project.main.store.SubTask;
import ru.yandex.praktikum.project.main.store.Task;

import java.util.List;


public interface ManagerTask {



    public List getListTasks();

    public List getListSubTasks();

    public List getListEpics();

    public List getListSubtasksOfEpic(Epic epic);
    public void removeTasksAll();

    public void removeSubTasksAll();

    public void removeEpicsAll();

    public Task getTask(int id);

    public SubTask getSubTask(int id);

    public Epic getEpic(int id);

    public void removeTask(int id);

    public void removeSubTask(int id);

    public void removeEpic(int id);

    public int createTask(Task task);

    public int createSubTask(SubTask subTask);


    public int createEpic(Epic epic);
    public void putSubTaskToEpic(Epic epic, SubTask subTask);
    public void updateTask(Task task);

    public void updateSubTask(SubTask subTask);

    public void updateEpic(Epic epic);

    public void updateStatusEpic(Epic epic);

}
