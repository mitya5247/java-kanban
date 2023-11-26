import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ManagerTask {
//    Task task;
    SubTask subTask;
    Epic epic;
    Scanner scanner = new Scanner(System.in);

    ArrayList<Task> taskArray = new ArrayList<>();
    ArrayList<Epic> epicArray = new ArrayList<>();




    void getListTasks() {}

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
            Task task = new Task(nameTask, taskDescription);
            taskArray.add(task);
            System.out.println("Задача создана!");
            System.out.println(task);
            System.out.println(taskArray);
        }
        if (command == 2) {
            System.out.println("Введите задачу");
            String name = scanner.nextLine();
            System.out.println("Введите описание задачи");
            String description = scanner.nextLine();
            epic = new Epic(name, description);
            epicArray.add(epic);
            System.out.println("Задача создана!");
            System.out.println(epic);
            System.out.println(epicArray);
        }
    }

    void updateTask(int id) {}

    void removeTask(int id) {}


}
