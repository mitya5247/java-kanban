package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTasksManager extends InMemoryTaskManager implements ManagerTask {

    Path fileName;
    String uri = "";
    public FileBackedTasksManager(String uri, String nameFile) {
        fileName = Paths.get(uri, nameFile);
        this.uri = uri;
    }

    public void read() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(fileName)))) {
            while (bufferedReader.ready()) {
                String string = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Task task) throws IOException {
        String taskString = this.toString(task);
        try(FileWriter fileWriter = new FileWriter(String.valueOf(fileName), true)) {
            fileWriter.write(taskString + "\n" );
            this.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString(Task task) throws IOException {
        String epic = String.valueOf(task);
        String firstString = " ";
        String taskString = null;

        String taskIdEpic = " ";
        if (task instanceof SubTask) {
            taskIdEpic = String.valueOf(((SubTask) task).getIdEpic());
        }

        try {
            if (Files.size(fileName) == 0) {
                firstString = String.join(",", "id", "type", "name", "status", "description", "epic" + ";" + "\n");
            }
            taskString = String.join(",", String.valueOf(task.getId()), "task", task.getName(),  task.getDescription(), task.getDescription(), taskIdEpic);
            if (firstString.equals(" ")) {
                return taskString;
            }
        } catch(FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch(IOException e) {
            throw new IOException(e);
        }
        return firstString + taskString;
    }

}
