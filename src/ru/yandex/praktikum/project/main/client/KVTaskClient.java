package ru.yandex.praktikum.project.main.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient { // клиент регистрируется на сервере

    URI url;
    String API_TOKEN;
    HttpClient client = HttpClient.newHttpClient();

    public KVTaskClient(String url) throws IOException, InterruptedException {
        this.url = URI.create(url);

        URI registerURI = URI.create(url + "/register");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(registerURI)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, bodyHandler);
        API_TOKEN = response.body();
    }

    public String getAPI_TOKEN() {
        return API_TOKEN;
    }

    public void put(String key, String json) throws IOException, InterruptedException {

        URI uri = URI.create(url + "/save/" + key + "?API_TOKEN=" + API_TOKEN);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(request, bodyHandler);
        System.out.println("Cтатус код для метода put " + response.statusCode());
    }

    public String load(String key) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/load/" + key + "?API_TOKEN=" + API_TOKEN);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(request, bodyHandler);
        System.out.println(response.statusCode());

        System.out.println("Cтатус код для метода load " + response.statusCode());

        return response.body();
    }
}
