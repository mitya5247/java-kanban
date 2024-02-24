package ru.yandex.praktikum.project.server;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.praktikum.project.engine.FileBackedTasksManager;
import ru.yandex.praktikum.project.client.KVTaskClient;
import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.MissingFormatArgumentException;

public class HttpTaskServer {
    static final int PORT = 8080;

    static Gson gson = new Gson();

    static File file = new File("testNew.csv");

    public static FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

    public HttpTaskServer() {
    }


    public void createServer() throws IOException {
        try {
            HttpServer httpServer = HttpServer.create();
            httpServer.bind(new InetSocketAddress(PORT), 0);
            httpServer.createContext("/tasks/task", new GetTaskHandler());
            httpServer.createContext("/tasks/task/1", new GetTaskHandler());
            httpServer.createContext("/tasks/epic/1", new GetTaskHandler());
            httpServer.createContext("/tasks/subtask/1", new GetTaskHandler());


            httpServer.createContext("/tasks/subtask", new GetTaskHandler());
            httpServer.createContext("/tasks/epic", new GetTaskHandler());


            httpServer.start();
        } catch (IOException e) {
            throw new IOException("Проблема с запуском сервера");
        }
    }

    public FileBackedTasksManager getFileBackedTaskManager() {
        return fileBackedTasksManager;
    }

    static class GetTaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {


            String method = httpExchange.getRequestMethod();
            String typeTask = this.getPath(httpExchange);

            switch (method) {
                case "GET": {
                    System.out.println("Перед handleGETAction");
                    this.handleGETAction(httpExchange);
                    this.writeResponse(httpExchange, "GET - запрос успешно обработан", 200);
                    break;
                }
                case "POST": {
                    this.handlePOSTAction(httpExchange);
                    break;
                }
                case "DELETE": {
                    this.handleDELETEAction(httpExchange);
                    this.writeResponse(httpExchange, "DELETE - запрос успешно обработан", 200);
                    break;

                }
                default:
                    System.out.println("Некорректно выбран метод");
            }

            this.writeResponse(httpExchange, "Запрос успешен", 200);
        }

        private void writeResponse(HttpExchange exchange, String message, int code) throws IOException {
            byte[] response = message.getBytes(Charset.defaultCharset());
            exchange.sendResponseHeaders(code, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private String getPath(HttpExchange httpExchange) {
            String path = httpExchange.getRequestURI().getPath();
            String[] pathArray = path.split("/");
            return pathArray[2];
        }

        private void handleGETAction(HttpExchange httpExchange) throws IOException {
            System.out.println("Обрабатываем запрос на получение задачи /tasks/task");

            String[] pathArray = httpExchange.getRequestURI().getPath().split("/");
            System.out.println(pathArray[2]);
            System.out.println(pathArray.length);
            switch (pathArray[2]) {
                case "task": {
                    if (pathArray.length == 4 && fileBackedTasksManager.getTaskMap().containsKey(Integer.parseInt(pathArray[3]))) {
                        Task task = fileBackedTasksManager.getTask(Integer.parseInt(pathArray[3]));
                        String jsonList = gson.toJson(task);
                        this.writeResponse(httpExchange, jsonList, 200);

                    } else if (pathArray.length == 3) {
                        String jsonList = gson.toJson(fileBackedTasksManager.getListTasks());
                        this.writeResponse(httpExchange, jsonList, 200);
                        System.out.println("Лист пустой");
                    } else {
                        System.out.println("Task не найден");
                    }
                    break;

                }
                case "subtask": {
                    if (pathArray.length == 4 && fileBackedTasksManager.getSubTaskMap().containsKey(Integer.parseInt(pathArray[3]))) {
                        SubTask subTask = fileBackedTasksManager.getSubTask(Integer.parseInt(pathArray[3]));
                        String jsonList = gson.toJson(subTask);
                        this.writeResponse(httpExchange, jsonList, 200);
                    } else if (pathArray.length == 3) {
                        String jsonList = gson.toJson(fileBackedTasksManager.getListSubTasks());
                        this.writeResponse(httpExchange, jsonList, 200);
                        System.out.println("Лист пустой");
                    } else {
                        System.out.println("SubTask не найден");
                    }
                    break;
                }
                case "epic": {
                    if (pathArray.length == 4 && fileBackedTasksManager.getEpicMap().containsKey(Integer.parseInt(pathArray[3]))) {
                        Epic epic = fileBackedTasksManager.getEpic(Integer.parseInt(pathArray[3]));
                        String jsonList = gson.toJson(epic);
                        this.writeResponse(httpExchange, jsonList, 200);
                    } else if (pathArray.length == 3) {
                        String jsonList = gson.toJson(fileBackedTasksManager.getListEpics());
                        this.writeResponse(httpExchange, jsonList, 200);
                    } else {
                        System.out.println("Epic не найден");
                    }
                    break;

                }
                default:
                    System.out.println("Неверно выбран тип задачи");
                    break;

            }
        }

        private void handlePOSTAction(HttpExchange httpExchange) throws IOException {

            String[] pathArray = httpExchange.getRequestURI().getPath().split("/");
            System.out.println(pathArray[2]);
            switch (pathArray[2]) {
                case "task": {
                    String taskString = new String(httpExchange.getRequestBody().readAllBytes(), Charset.defaultCharset());
                    try {
                        Task taskNew = gson.fromJson(taskString, Task.class);
                        fileBackedTasksManager.createTask(taskNew);
                        System.out.println(fileBackedTasksManager.getTaskMap());
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                }
                case "subtask": {

                    String subTaskString = new String(httpExchange.getRequestBody().readAllBytes(), Charset.defaultCharset());

                    System.out.println(subTaskString);
                    try {
                        SubTask taskNew = gson.fromJson(subTaskString, SubTask.class);
                        fileBackedTasksManager.createSubTask(taskNew);
                        System.out.println(fileBackedTasksManager.getSubTaskMap());
                    } catch (MissingFormatArgumentException | InputMismatchException | NullPointerException e) {
                        System.out.println("Ошибка преобразования json");
                    }
                }
                break;
                case "epic": {

                    String epicString = new String(httpExchange.getRequestBody().readAllBytes(), Charset.defaultCharset());
                    try {
                        System.out.println("Получаем эпик");
                        Epic epic = gson.fromJson(epicString, Epic.class);
                        fileBackedTasksManager.createEpic(epic);
                        System.out.println(fileBackedTasksManager.getEpicMap());

                    } catch (IllegalArgumentException | NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
                default:
                    System.out.println("Неверно выбран тип задачи");
                    break;
            }
        }

        private void handleDELETEAction(HttpExchange httpExchange) throws IOException {
            System.out.println("Обрабатываем запрос на удаление задачи /tasks/task");

            String[] pathArray = httpExchange.getRequestURI().getPath().split("/");
            switch (pathArray[2]) {
                case "task": {
                    if (pathArray.length == 4 && fileBackedTasksManager.getTaskMap().containsKey(Integer.parseInt(pathArray[3]))) {
                        fileBackedTasksManager.removeTask(Integer.parseInt(pathArray[3]));
                        Task task = fileBackedTasksManager.getTask(Integer.parseInt(pathArray[3]));

                        String jsonList = gson.toJson(task);
                        this.writeResponse(httpExchange, jsonList, 200);

                    } else if (pathArray.length == 3) {
                        fileBackedTasksManager.removeTasksAll();
                        String jsonList = gson.toJson(fileBackedTasksManager.getTaskMap());
                        this.writeResponse(httpExchange, jsonList, 200);
                        System.out.println("Лист пустой");
                    } else {
                        System.out.println("Task не найден");
                    }
                    break;

                }
                case "subtask": {
                    if (pathArray.length == 4 && fileBackedTasksManager.getSubTaskMap().containsKey(Integer.parseInt(pathArray[3]))) {
                        fileBackedTasksManager.removeSubTask(Integer.parseInt(pathArray[3]));
                        SubTask subTask = fileBackedTasksManager.getSubTask(Integer.parseInt(pathArray[3]));
                        String jsonList = gson.toJson(subTask);
                        this.writeResponse(httpExchange, jsonList, 200);
                    } else if (pathArray.length == 3) {
                        fileBackedTasksManager.removeSubTasksAll();
                        String jsonList = gson.toJson(fileBackedTasksManager.getSubTaskMap());
                        this.writeResponse(httpExchange, jsonList, 200);
                        System.out.println("Лист пустой");
                    } else {
                        System.out.println("SubTask не найден");
                    }
                    break;
                }
                case "epic": {
                    if (pathArray.length == 4 && fileBackedTasksManager.getEpicMap().containsKey(Integer.parseInt(pathArray[3]))) {
                        fileBackedTasksManager.removeEpic(Integer.parseInt(pathArray[3]));
                        Epic epic = fileBackedTasksManager.getEpic(Integer.parseInt(pathArray[3]));
                        String jsonList = gson.toJson(fileBackedTasksManager.getEpicMap());
                        this.writeResponse(httpExchange, jsonList, 200);
                    } else if (pathArray.length == 3) {
                        fileBackedTasksManager.removeEpicsAll();
                        String jsonList = gson.toJson(fileBackedTasksManager.getEpicMap());
                        this.writeResponse(httpExchange, jsonList, 200);
                    } else {
                        System.out.println("Epic не найден");
                    }
                    break;

                }
                default:
                    System.out.println("Неверно выбран тип задачи");
                    break;
            }

        }
    }


}
