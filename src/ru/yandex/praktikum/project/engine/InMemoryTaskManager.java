package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.Status;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.util.*;


public class InMemoryTaskManager implements ManagerTask {

    int nextID = 1;
    public HashMap<Integer, Task> taskMap = new HashMap<>();
    public HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    public HashMap<Integer, Epic> epicMap = new HashMap<>(); // хранение эпиков как объектов по id после создания

    private int taskCounter = 0;
    private int subTaskCounter = 0;
    private int epicCounter = 0;

    List<Task> tasksListHistory = new ArrayList<>();



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
        for (int idItem : epic.getSubTasksID()) {
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
            epic.getSubTasksID().clear();
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
        taskCounter += 1;
        tasksListHistory.add(taskMap.get(id));
        return taskMap.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        subTaskCounter += 1;
        tasksListHistory.add(subTaskMap.get(id));
        return subTaskMap.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        epicCounter += 1;
        tasksListHistory.add(epicMap.get(id));
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

    @Override
    public void removeEpic(int id) {
        for (int subTaskID : epicMap.get(id).getSubTasksID()) {
            subTaskMap.remove(subTaskID);
        }
        epicMap.remove(id);
    }

    @Override
    public int createTask(Task task) {
        task.setId(nextID);
        nextID++;
        taskMap.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public int createSubTask(SubTask subTask) {
        subTask.setId(nextID);
        nextID++;
        subTaskMap.put(subTask.getId(), subTask);
        return subTask.getId();
    }


    @Override
    public int createEpic(Epic epic) {
        epic.setId(nextID);
        nextID++;
        epicMap.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public void putSubTaskToEpic(Epic epic, SubTask subTask) {
        epic.getSubTasksID().add(subTask.getId());
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


        if (!epic.getSubTasksID().isEmpty()) {
            for (int id : epic.getSubTasksID()) {
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

    @Override
    public List<Task> getHistory() {

        if (!tasksListHistory.isEmpty()) {
            System.out.println(tasksListHistory.size());
            if (tasksListHistory.size() > 10) {
                ArrayList<Integer> idArray = new ArrayList<>();
                for (int i = 0; i < tasksListHistory.size() - 10; i++) {
                    idArray.add(tasksListHistory.get(i).getId());
                    if (tasksListHistory.size() == 10) {
                       break;
                    }
                   // tasksListHistory.remove(i);
               }
                System.out.println(idArray);
                for (int i = 0; i < idArray.size(); i++) {
                    for (int j = 0; i < tasksListHistory.size(); i++) {
                        if (tasksListHistory.get(j).getId() == idArray.get(i)) {
                            tasksListHistory.remove(j);
                        }
                        break;
                    }
                }

            }
            System.out.println(tasksListHistory);
            System.out.println(tasksListHistory.size());
        } else {
            System.out.println("История пуста");
        }
        return tasksListHistory;
    }


}
