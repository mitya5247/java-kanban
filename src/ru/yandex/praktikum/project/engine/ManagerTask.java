package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.util.HashMap;


public class ManagerTask {

    int nextID = 1;
    HashMap<Integer, Task> taskMap = new HashMap<>();
    public HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    public HashMap<Integer, Epic> epicMap = new HashMap<>(); // хранение эпиков как объектов по id после создания


    void getListTasks() {
        for (Task task : taskMap.values()) {
            System.out.println(task);
        }
    }

    void getListSubTasks() {
        for (SubTask subTask : subTaskMap.values()) {
            System.out.println(subTask);
        }
    }

    void getListEpics() {
        for (Epic epic : epicMap.values()) {
            System.out.println(epic);
        }
    }

    void removeTasksAll() {
        taskMap.clear();
    }

    void removeSubTasksAll() {
        subTaskMap.clear();
    }

    void removeEpicsAll() {
        epicMap.clear();
    }

    Task getTask(int id) {
        return taskMap.get(id);
    }

    SubTask getSubTask(int id) {
        return subTaskMap.get(id);
    }

    Epic getEpic(int id) {
        return epicMap.get(id);
    }

    void removeTask(int id) {
        taskMap.remove(id);
    }

    public void removeSubTask(int id) {
        subTaskMap.remove(id);
    }

    public void removeEpic(int id) {
        epicMap.remove(id);
    }

    int createTask(Task task) {
        task.id = nextID;
        nextID++;
        taskMap.put(task.id, task);
        return task.id;
    }

    public int createSubTask(SubTask subTask) {
        subTask.id = nextID;
        nextID++;
        subTaskMap.put(subTask.id, subTask);
        return subTask.id;
    }


    public int createEpic(Epic epic) {
        epic.id = nextID;
        nextID++;
        epicMap.put(epic.getId(), epic);
        return epic.getId();
    }

    public void putSubTaskToEpic(Epic epic, SubTask subTask) {
        epic.getSubTasksID().add(subTask.getId());
        subTask.setIdEpic(epic.getId());
    }

    void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    void updateSubTask(SubTask subTask) {
        subTaskMap.put(subTask.getId(), subTask);
    }

    public void updateEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
        boolean isDone = false;
        boolean isInProgress = false;
        boolean isNew = false;


        if (!epic.getSubTasksID().isEmpty()) {
            for (int id : epic.getSubTasksID()) {
                if (subTaskMap.get(id).getStatus().equals("DONE")) {
                    isDone = true;
                } else if (subTaskMap.get(id).getStatus().equals("NEW")) {
                    isNew = true;
                } else {
                    isInProgress = true;
                    break;
                }
            }
        } else {
            isNew = true;
        }

        if (isDone && !isNew && !isInProgress) {
            epic.setStatus("DONE");
        } else if (isNew && !isInProgress && !isDone) {
            epic.setStatus("NEW");
        } else {
            epic.setStatus("IN_PROGRESS");
        }

    }

    void getListSubtasksOfEpic(Epic epic) {
        for (int idItem : epic.getSubTasksID()) {
            System.out.println(subTaskMap.get(idItem));
        }
    }


}
