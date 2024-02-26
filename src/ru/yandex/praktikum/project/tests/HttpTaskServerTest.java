package ru.yandex.praktikum.project.tests;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.project.main.server.HttpTaskServer;
import ru.yandex.praktikum.project.main.store.Epic;
import ru.yandex.praktikum.project.main.store.SubTask;
import ru.yandex.praktikum.project.main.store.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class HttpTaskServerTest {

    public static HttpTaskServer taskServer;
    Gson gson = new Gson();

    HttpClient client = HttpClient.newHttpClient();

    @BeforeAll
    public static void createHttpTaskServer() throws IOException {
        taskServer = new HttpTaskServer();
        taskServer.createServer();
    }

    @Test
    public void shouldCreateTask() throws IOException, InterruptedException {
        Task task = new Task("1", "1", "NEW", 12, LocalDateTime.of(2023, 2, 12, 14, 3));

        String taskString = gson.toJson(task);

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/task";
        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskString))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());

    }
    @Test
    public void shouldCreateSubTask() throws IOException, InterruptedException {
        SubTask subTask = new SubTask("1", "1", "NEW", 12, LocalDateTime.of(2023, 2, 12, 14, 3));

        String taskString = gson.toJson(subTask);

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/subtask";
        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskString))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldCreateEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("1", "1");

        String taskString = gson.toJson(epic);

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/epic";
        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskString))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void shouldGetTask() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/task/1";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldGetSubTask() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/subtask/1";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldGetEpic() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/epic/1";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldGetTasks() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/task/";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldGetSubTasks() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/subtask/";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldGetEpics() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/epic/";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldRemoveTask() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/task/1";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldRemoveSubTask() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/subtask/1";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldRemoveEpic() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/epic/1";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldRemoveAllTasks() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/task";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldRemoveAllSubTasks() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/subtask";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void shouldRemoveAllEpics() throws IOException, InterruptedException {
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        String url = "http://localhost:8080/tasks/epic";
        URI uri = URI.create(url);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, bodyHandler);
        Assertions.assertEquals(200, response.statusCode());
    }


}
