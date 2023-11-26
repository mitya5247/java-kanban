import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ManagerTask {
    Task task;
    SubTask subTask;
    Epic epic;
    Scanner scanner = new Scanner(System.in);


    ArrayList<Task> taskArray = new ArrayList<>();
    ArrayList<Epic> epicArray = new ArrayList<>();
    ArrayList<ArrayList> taskArrayAll = new ArrayList<>();




    void getListTasks() {
        taskArrayAll.add(taskArray);
        taskArrayAll.add(epicArray);
        for (ArrayList tasks : taskArrayAll) {
            for (Object task : tasks) {
                System.out.println(task);
            }
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
            task.id = epicArray.size() + taskArray.size() + 1;
            task.taskMap.put(task.id, nameTask);
            taskArray.add(task);
            System.out.println("Задача создана!");
            System.out.println(task);
            System.out.println(taskArray);
        }
        if (command == 2) {
            System.out.println("Введите эпик");
            String name = scanner.next();
            System.out.println("Введите описание задачи");
            String description = scanner.next();
            epic = new Epic(name, description);
            epic.id = epicArray.size() + taskArray.size() + 1;
            epic.epicSubTaskMap.put(epic.name, epic.subTasks);
            epic.epicMap.put(epic.id, epic.epicSubTaskMap);
            epicArray.add(epic);
            System.out.println("Эпик создан!");
            System.out.println(epic);
            System.out.println(epicArray);
        }
    }

    void updateTask(int id) {

    }

    void removeTask(int id) {

    }


}
