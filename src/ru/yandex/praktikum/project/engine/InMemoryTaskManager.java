package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.Status;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.time.LocalDateTime;

import java.util.*;


public class InMemoryTaskManager implements ManagerTask {

    int nextId = 1;


    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    private HashMap<Integer, Epic> epicMap = new HashMap<>();

    public InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    public HashMap<Integer, Task> getTaskMap() {
        return taskMap;
    }

    public HashMap<Integer, SubTask> getSubTaskMap() {
        return subTaskMap;
    }

    public HashMap<Integer, Epic> getEpicMap() {
        return epicMap;
    }


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
        historyManager.remove(id);


    }

    @Override
    public void removeEpic(int id) {
        if (!epicMap.get(id).getSubTasksId().isEmpty()) {
            for (int subTaskID : epicMap.get(id).getSubTasksId()) {
                subTaskMap.remove(subTaskID);
            }
        }
        epicMap.remove(id);
        historyManager.remove(id);
    }

    @Override
    public int createTask(Task task) {
        task.setId(nextId);
        nextId++;
        if (this.validationOfTasks(task)) {
            taskMap.put(task.getId(), task);
            return task.getId();
        } else {
            return 0;
        }

    }

    @Override
    public int createSubTask(SubTask subTask) {
        // subTaskMap.put(subTask.getId(), subTask);
        subTask.setId(nextId);
        nextId++;
        if (this.validationOfTasks(subTask)) {
            subTaskMap.put(subTask.getId(), subTask);
            return subTask.getId();
        } else {
            return 0;
        }
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
        //   epic.getEndTime(subTask.getDuration());
        subTask.setIdEpic(epic.getId());
        this.updateEpic(epic);
        this.startTimeEpic(epic);
        this.endTimeEpic(epic);
    }

    @Override
    public void updateTask(Task task) {
        boolean isValidationSuccess = this.validationOfTasks(task);
        if (isValidationSuccess && taskMap.containsKey(task.getId())) {
            taskMap.put(task.getId(), task);
        } else {
            System.out.println("Task не прошел валидацию : проверьте параметры или Id");
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        boolean isValidationSuccess = this.validationOfTasks(subTask);

        if (isValidationSuccess && subTaskMap.containsKey(subTask.getId())) {
            subTaskMap.put(subTask.getId(), subTask);
            if (subTaskMap.get(subTask.getId()).getIdEpic() != 0) {
                this.updateEpic(epicMap.get(subTask.getIdEpic()));
            }
        } else {
            System.out.println("SubTask не прошел валидацию: проверьте параметры или Id");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epicMap.containsKey(epic.getId())) {
            epicMap.put(epic.getId(), epic);
            this.updateStatusEpic(epic);
        } else {
            System.out.println("Нет эпика с данным Id");
        }

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

    private LocalDateTime startTimeEpic(Epic epic) {
        LocalDateTime earlyTime = LocalDateTime.MAX;
        ;
        for (Integer id : epic.getSubTasksId()) {
            LocalDateTime subTaskTime = subTaskMap.get(id).getStartTime();
            if (subTaskTime.isBefore(earlyTime)) {
                earlyTime = subTaskTime;
            }
        }
        return epic.getStartTime(earlyTime);
    }

    private void endTimeEpic(Epic epic) {
        int durationSum = 0;
        LocalDateTime endTime = LocalDateTime.MIN;
        for (Integer id : epic.getSubTasksId()) {
            durationSum += subTaskMap.get(id).getDuration();
            LocalDateTime subTaskTime = subTaskMap.get(id).getEndTime();
            if (subTaskTime.isAfter(endTime)) {
                endTime = subTaskTime;
            }
        }
        epic.getEndTime(endTime);
        epic.setDuration(durationSum);
    }

    public List<Task> getPrioritizedTasks() {
        TreeSet<Task> treeSetTasks = new TreeSet<>((Task task, Task t1) ->
                task.getStartTime().isBefore(t1.getStartTime()) ? -1 : 10);

        treeSetTasks.addAll(subTaskMap.values());
        treeSetTasks.addAll(taskMap.values());


        return new ArrayList<>(treeSetTasks);
    }

    public boolean validationOfTasks(Task newTask) {
        if (newTask instanceof SubTask) {
            return this.validationOfSubTasksNew((SubTask) newTask);
        } else {
            return this.validationOfTasksNew(newTask);
        }
    }

    public boolean validationOfTasksNew(Task taskNew) {
        if (!taskMap.isEmpty()) {
            for (Task task : taskMap.values()) {
                if (task.getStartTime().equals(taskNew.getStartTime())) {
                    System.out.println("Task со временем " + task.getStartTime() + " уже существует");
                    nextId--;
                    return false;
                }
            }

        }
        return true;

    }

    public boolean validationOfSubTasksNew(SubTask subTaskNew) {
        if (!subTaskMap.isEmpty()) {
            for (SubTask task : subTaskMap.values()) {
                if (task.getStartTime().equals(subTaskNew.getStartTime())) {
                    System.out.println("SubTask со временем " + task.getStartTime() + " уже существует");
                    nextId--;
                    return false;
                }
            }
        }
        return true;

    }

}




