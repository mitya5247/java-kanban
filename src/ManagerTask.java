import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ManagerTask {
    Task task;
    SubTask subTask;
    Epic epic;
    Scanner scanner = new Scanner(System.in);

    HashMap<Integer, String> taskMap = new HashMap<>();
    HashMap<Integer, HashMap> epicMap = new HashMap<>();

//    ArrayList<Task> taskArray = new ArrayList<>();
//    ArrayList<Epic> epicArray = new ArrayList<>();
    ArrayList<HashMap> taskArrayAll = new ArrayList<>();

    public ManagerTask() {
        taskArrayAll.add(taskMap);
        taskArrayAll.add(epicMap);
    }




    void getListTasks() {

        for (HashMap hashMap : taskArrayAll) {
            System.out.println(hashMap);
        }


    }

    void removeAllTasks() {}

//    Task getTask(int id) {
//        return false;
//    }

    void createTask() {
        System.out.println("Выберите тип задачи: 1 - простая задача, 2 - эпик");
        int command = scanner.nextInt();
        if (command == 1) {
            System.out.println("Введите задачу");
            String nameTask = scanner.next();
            System.out.println("Введите описание задачи");
            String taskDescription = scanner.next();
            task = new Task(nameTask, taskDescription);
            task.id = taskMap.size() + epicMap.size() + 1;
            taskMap.put(task.id, nameTask);
            System.out.println("Задача создана!");
            System.out.println(task);
//            System.out.println(taskArray);
            }

        if (command == 2) {
            System.out.println("Введите эпик");
            String name = scanner.next();
            System.out.println("Введите описание задачи");
            String description = scanner.next();
            epic = new Epic(name, description);
            epic.id = taskMap.size() + epicMap.size() + 1;
            epic.epicSubTaskMap.put(epic.name, epic.subTasks);
            epicMap.put(epic.id, epic.epicSubTaskMap);
            System.out.println("Эпик создан!");
            System.out.println(epic);
//            System.out.println(epicArray);
        }
    }




    void updateTask(int id) {
        for (HashMap hashMap : taskArrayAll) {
            if (hashMap.containsKey(id)) {
                hashMap.get(id);
                System.out.println("Введите что хотите обновить");
                String newName = scanner.next();
                hashMap.put(id, newName);
            } else {
                System.out.println("Задачей с таким id не найдено");
            }
        }
        System.out.println("После изменения нужного id");
        this.getListTasks();
    }


    void removeTask(int id) {
        for (HashMap hashMap : taskArrayAll) {
            if (hashMap.containsKey(id)) {
                hashMap.remove(id);
            } else {
                System.out.println("Задачей с таким id не найдено");
            }
        }
        System.out.println("После удаления нужного id");
        this.getListTasks();
    }


}
