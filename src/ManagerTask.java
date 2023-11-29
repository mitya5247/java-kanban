import java.util.HashMap;


public class ManagerTask {

    int nextID = 1;
    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    HashMap<Integer, Epic> epicMap = new HashMap<>(); // хранение эпиков как объектов по id после создания


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

    void removeSubTask(int id) {
        subTaskMap.remove(id);
    }

    void removeEpic(int id) {
        epicMap.remove(id);
    }

    int createTask(Task task) {
        task.id = nextID;
        nextID++;
        taskMap.put(task.id, task);
        return task.id;
    }

    int createSubTask(SubTask subTask) {
        subTask.id = nextID;
        nextID++;
        subTaskMap.put(subTask.id, subTask);
        return subTask.id;
    }


    int createEpic(Epic epic) {
        epic.id = nextID;
        nextID++;
        epicMap.put(epic.id, epic);
        return epic.id;
    }

    void putSubTaskToEpic(Epic epic, SubTask subTask) {
        epic.subTasksID.add(subTask.id);
        subTask.idEpic = epic.id;
    }

    void updateTask(Task task) {
        taskMap.put(task.id, task);
    }

    void updateSubTask(SubTask subTask) {
        subTaskMap.put(subTask.id, subTask);
    }

    void updateEpic(Epic epic) {
        epicMap.put(epic.id, epic);
        boolean isDone = false;
        boolean isInProgress = false;
        boolean isNew = false;


        if (!epic.subTasksID.isEmpty()) {
            for (int id : epic.subTasksID) {
                if (subTaskMap.get(id).status.equals("DONE")) {
                    isDone = true;
                } else if (subTaskMap.get(id).status.equals("NEW")) {
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
        for (int idItem : epic.subTasksID) {
            System.out.println(subTaskMap.get(idItem));
        }
    }


}
