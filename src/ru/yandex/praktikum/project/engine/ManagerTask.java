package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ManagerTask {

    int nextID = 1;
    public HashMap<Integer, Task> taskMap = new HashMap<>();
    public HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    public HashMap<Integer, Epic> epicMap = new HashMap<>(); // хранение эпиков как объектов по id после создания


    public List getListTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskMap.values()) {
            tasks.add(task);
        }
        return tasks;
    }

    public List getListSubTasks() {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (SubTask subTask : subTaskMap.values()) {
            subTasks.add(subTask);
        }
        return subTasks;
    }

    public List getListEpics() {
        ArrayList<Epic> epics = new ArrayList<>();
        for (Epic epic : epicMap.values()) {
            epics.add(epic);
        }
        return epics;
    }

    public List getListSubtasksOfEpic(Epic epic) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (int idItem : epic.getSubTasksID()) {
            subTasks.add(subTaskMap.get(idItem));
        }
        return subTasks;
    }

    public void removeTasksAll() {
        taskMap.clear();
    }

    public void removeSubTasksAll() {
        for (Epic epic : epicMap.values()) {
            epic.getSubTasksID().clear();
            this.updateEpic(epic);
        }
        subTaskMap.clear();
    }

    public void removeEpicsAll() {
        epicMap.clear();
        this.removeSubTasksAll();
    }

    public Task getTask(int id) {
        return taskMap.get(id);
    }

    public SubTask getSubTask(int id) {
        return subTaskMap.get(id);
    }

    public Epic getEpic(int id) {
        return epicMap.get(id);
    }

    public void removeTask(int id) {
        taskMap.remove(id);
    }

    public void removeSubTask(int id) {
        int index = 0;
        if (subTaskMap.get(id).getIdEpic() != 0) { // если subTask принадлежит какому-либо эпику
            for (int i = 0; i < epicMap.get(subTaskMap.get(id).getIdEpic()).getSubTasksID().size(); i++) { // удаляем id SubTask из списка id внутри эпика
                if (epicMap.get(subTaskMap.get(id).getIdEpic()).getSubTasksID().get(i) == id) {
                    index = i;
                    epicMap.get(subTaskMap.get(id).getIdEpic()).getSubTasksID().remove(index);
                    break;
                }
            }
            this.updateEpic(epicMap.get(subTaskMap.get(id).getIdEpic())); // обновляем нужный эпик
        }
        subTaskMap.remove(id);


    }

    public void removeEpic(int id) {
        for (int subTaskID : epicMap.get(id).getSubTasksID()) {
            subTaskMap.remove(subTaskID);
        }
        epicMap.remove(id);
    }

    public int createTask(Task task) {
        task.setId(nextID);
        nextID++;
        taskMap.put(task.getId(), task);
        return task.getId();
    }

    public int createSubTask(SubTask subTask) {
        subTask.setId(nextID);
        nextID++;
        subTaskMap.put(subTask.getId(), subTask);
        return subTask.getId();
    }


    public int createEpic(Epic epic) {
        epic.setId(nextID);
        nextID++;
        epicMap.put(epic.getId(), epic);
        return epic.getId();
    }

    public void putSubTaskToEpic(Epic epic, SubTask subTask) {
        epic.getSubTasksID().add(subTask.getId());
        subTask.setIdEpic(epic.getId());
        this.updateEpic(epic);
    }

    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subTask) {
        subTaskMap.put(subTask.getId(), subTask);
        if (subTaskMap.get(subTask.getId()).getIdEpic() != 0) {
            this.updateEpic(epicMap.get(subTask.getIdEpic()));
        }
    }

    public void updateEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
        this.updateStatusEpic(epic);

    }

    public void updateStatusEpic(Epic epic) {
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


}
