package ru.yandex.praktikum.project.engine;

import com.google.gson.Gson;
import ru.yandex.praktikum.project.client.KVTaskClient;
import ru.yandex.praktikum.project.exceptions.SaveException;
import ru.yandex.praktikum.project.server.KVServer;
import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

public class HttpTaskManager extends FileBackedTasksManager{

    KVTaskClient kvTaskClient;
    String key = "tasks";
    Gson gson = new Gson();
    public HttpTaskManager(File file, String url) {
        super(file);
        try {
            kvTaskClient = new KVTaskClient(url);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка создания клиента");
        }
    }

    public static void main(String[] args) throws SaveException {
        //  HttpTaskServer httpTaskServer = new HttpTaskServer();
        //  httpTaskServer.createServer();

        File file = new File("testNew.csv");

        try {
            KVServer kvServer = new KVServer();
            kvServer.start();
        } catch (IOException e) {
            System.out.println("Ошибка запуска сервера. Проверьте класс KVServer");
        }

        Task task = new Task("1", "1", "NEW", 12, LocalDateTime.of(2023, 2, 12, 14, 3));
        Task task1 = new Task("2", "1", "NEW", 12, LocalDateTime.of(2023, 3, 12, 14, 3));
        Epic epic = new Epic("name", "fsd");

        HttpTaskManager httpTaskManager = new HttpTaskManager(file, "http://localhost:8080");

        httpTaskManager.createTask(task);
        httpTaskManager.createTask(task1);
        httpTaskManager.createEpic(epic);

        httpTaskManager.removeTask(task.getId());

        httpTaskManager.loadFromServer();

    }

    public String getKey() {
        return key;
    }

    @Override
    public void save() throws SaveException {
        String tasksString = this.convertTaskToJson();
        String subTasks = this.convertSubTaskToJson();
        String epics = this.convertEpicToJson();

        String tasks = String.join("; ", tasksString, subTasks, epics);

        try {
            kvTaskClient.put(key, tasks);
        } catch (IOException | InterruptedException e) {
            throw new SaveException("Прервано соединение");
        }
    }

    private String convertTaskToJson() {
        StringBuilder sb = new StringBuilder();
        String taskJson = "";
        for (Task task : this.getTaskMap().values()) {
            sb.append(gson.toJson(task) + ", ");
        }
        taskJson = sb.toString();
        return taskJson;
    }
    private String convertSubTaskToJson() {
        StringBuilder sb = new StringBuilder();
        String subTaskJson = "";
        for (SubTask subTask : this.getSubTaskMap().values()) {
            sb.append(gson.toJson(subTask) + ", ");
        }
        subTaskJson = sb.toString();
        return subTaskJson;
    }
    private String convertEpicToJson() {
        StringBuilder sb = new StringBuilder();
        String epicJson = "";
        for (Epic epic : this.getEpicMap().values()) {
            sb.append(gson.toJson(epic) + ", ");
        }
        epicJson = sb.toString();
        return epicJson;
    }

    public void loadFromServer() {

        String response = "";

        try {
            response = kvTaskClient.load(key);
        } catch (IOException | InterruptedException exception) {
            System.out.println("Ошибка клиента при попытке загрузить задачи. Проверьте метод load в классе" +
                    "KVServerClient");
        }
        String[] tasksString = response.split("; ");
        if (!tasksString[0].isBlank()) {
            String[] tasks = tasksString[0].split(", ");
            int i = 0;
            while (!tasks[i].isBlank()) {
                Task task = gson.fromJson(tasks[i], Task.class);
                this.getTaskMap().put(task.getId(), task);
                if (tasks.length == 1) {
                    break;
                }
                i += 1;
            }
        }
        if (!tasksString[1].isBlank()) {
            String[] subTasks = tasksString[1].split(", ");
            int i = 0;
            while (!subTasks[i].isBlank()) {
                SubTask task = gson.fromJson(subTasks[i], SubTask.class);
                this.getSubTaskMap().put(task.getId(), task);
                if (subTasks.length == 1) {
                    break;
                }
                i += 1;
            }
        }
        if (!tasksString[2].isBlank()) {
            String[] epics = tasksString[2].split(", ");
            int i = 0;
            while (!epics[i].isBlank()) {
                Epic epic = gson.fromJson(epics[i], Epic.class);
                this.getEpicMap().put(epic.getId(), epic);
                if (epics.length == 1) {
                    break;
                }
                i += 1;
            }
        }
    }


}
