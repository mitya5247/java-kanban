package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.Status;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.util.*;


public class InMemoryTaskManager implements ManagerTask {

    int nextId = 1;
    public HashMap<Integer, Task> taskMap = new HashMap<>();
    public HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    public HashMap<Integer, Epic> epicMap = new HashMap<>();

    public InMemoryHistoryManager historyManager = new InMemoryHistoryManager();


    @Override
    public List getListTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskMap.values()) {
            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public List getListSubTasks() {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (SubTask subTask : subTaskMap.values()) {
            subTasks.add(subTask);
        }
        return subTasks;
    }

    @Override
    public List getListEpics() {
        ArrayList<Epic> epics = new ArrayList<>();
        for (Epic epic : epicMap.values()) {
            epics.add(epic);
        }
        return epics;
    }

    @Override
    public List getListSubtasksOfEpic(Epic epic) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (int idItem : epic.getSubTasksId()) {
            subTasks.add(subTaskMap.get(idItem));
        }
        return subTasks;
    }

    @Override
    public void removeTasksAll() {
        taskMap.clear();
    }

    @Override
    public void removeSubTasksAll() {
        for (Epic epic : epicMap.values()) {
            epic.getSubTasksId().clear();
            this.updateEpic(epic);
        }
        subTaskMap.clear();
    }

    @Override
    public void removeEpicsAll() {
        epicMap.clear();
        this.removeSubTasksAll();
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(taskMap.get(id));
        return taskMap.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        historyManager.add(subTaskMap.get(id));
        return subTaskMap.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(epicMap.get(id));
        return epicMap.get(id);
    }

    @Override
    public void removeTask(int id) {
        taskMap.remove(id);
    }

    @Override
    public void removeSubTask(int id) {
        int index = 0;
        if (subTaskMap.get(id).getIdEpic() != 0) { // если subTask принадлежит какому-либо эпику
            for (int i = 0; i < epicMap.get(subTaskMap.get(id).getIdEpic()).getSubTasksId().size(); i++) { // удаляем id SubTask из списка id внутри эпика
                if (epicMap.get(subTaskMap.get(id).getIdEpic()).getSubTasksId().get(i) == id) {
                    index = i;
                    epicMap.get(subTaskMap.get(id).getIdEpic()).getSubTasksId().remove(index);
                    break;
                }
            }
            this.updateEpic(epicMap.get(subTaskMap.get(id).getIdEpic())); // обновляем нужный эпик
        }
        subTaskMap.remove(id);


    }

    @Override
    public void removeEpic(int id) {
        for (int subTaskID : epicMap.get(id).getSubTasksId()) {
            subTaskMap.remove(subTaskID);
        }
        epicMap.remove(id);
    }

    @Override
    public int createTask(Task task) {
        task.setId(nextId);
        nextId++;
        taskMap.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public int createSubTask(SubTask subTask) {
        subTask.setId(nextId);
        nextId++;
        subTaskMap.put(subTask.getId(), subTask);
        return subTask.getId();
    }


    @Override
    public int createEpic(Epic epic) {
        epic.setId(nextId);
        nextId++;
        epicMap.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public void putSubTaskToEpic(Epic epic, SubTask subTask) {
        epic.getSubTasksId().add(subTask.getId());
        subTask.setIdEpic(epic.getId());
        this.updateEpic(epic);
    }

    @Override
    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTaskMap.put(subTask.getId(), subTask);
        if (subTaskMap.get(subTask.getId()).getIdEpic() != 0) {
            this.updateEpic(epicMap.get(subTask.getIdEpic()));
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
        this.updateStatusEpic(epic);

    }

    @Override
    public void updateStatusEpic(Epic epic) {
        boolean isDone = false;
        boolean isInProgress = false;
        boolean isNew = false;


        if (!epic.getSubTasksId().isEmpty()) {
            for (int id : epic.getSubTasksId()) {
                if (subTaskMap.get(id).getStatus().equals(Status.DONE.name())) {
                    isDone = true;
                } else if (subTaskMap.get(id).getStatus().equals(Status.NEW.name())) {
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
            epic.setStatus(Status.DONE.name());
        } else if (isNew && !isInProgress && !isDone) {
            epic.setStatus(Status.NEW.name());
        } else {
            epic.setStatus(Status.IN_PROGRESS.name());
        }

    }

}




