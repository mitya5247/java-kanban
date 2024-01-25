package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements ManagerTask {

    Path fileName;
    String uri = "";
    InMemoryTaskManager managerTask;


    public FileBackedTasksManager(String uri, String nameFile, InMemoryTaskManager managerTask) {
        fileName = Paths.get(uri, nameFile);
        this.uri = uri;
        this.managerTask = managerTask;
    }

    @Override
    public int createTask(Task task) {
        super.createTask(task);
        this.save();
        return task.getId();
    }

    @Override
    public int createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        this.save();
        return subTask.getId();
    }

    @Override
    public int createEpic(Epic epic) {
        super.createEpic(epic);
        this.save();
        return epic.getId();
    }

    public long read() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(fileName)))) {
            while (bufferedReader.ready()) {
                String string = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Files.size(fileName);
    }

    public String writeHistory() throws IOException {
        List<Task> myHistory = Managers.getDefaultHistory().getHistory();
        StringBuilder stringBuilder = new StringBuilder();
        String idTasks = "";
        for (Task oneTask : myHistory) {
            stringBuilder.append(oneTask.getId());
            stringBuilder.append(",");
        }
        idTasks = stringBuilder.toString();
        return idTasks;
    }

    public void save() {
        String firstString = " ";
        String taskString = null;
        String taskIdEpic = " ";


        try (FileWriter fw = new FileWriter(String.valueOf(fileName))) {
            firstString = String.join(",", "id", "type", "name", "status", "description", "epic" + ";" + "\n");
            fw.write(firstString);
            for (Task testTask : managerTask.taskMap.values()) {
                taskString = String.join(",", String.valueOf(testTask.getId()), "task", testTask.getName(), testTask.getDescription(), testTask.getDescription(), taskIdEpic + "\n");
                fw.write(taskString);
            }
            for (SubTask testTask : managerTask.subTaskMap.values()) {
                if (testTask instanceof SubTask) {
                    taskIdEpic = String.valueOf(((SubTask) testTask).getIdEpic());
                }
                taskString = String.join(",", String.valueOf(testTask.getId()), "task", testTask.getName(), testTask.getDescription(), testTask.getDescription(), taskIdEpic + "\n");
                fw.write(taskString);
            }
            for (Epic testTask : managerTask.epicMap.values()) {
                taskString = String.join(",", String.valueOf(testTask.getId()), "task", testTask.getName(), testTask.getDescription(), testTask.getDescription(), taskIdEpic + "\n");
                fw.write(taskString);
            }

            fw.write("\n");
            fw.write(this.writeHistory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Task fromString(String value) {
        String[] taskArray = value.split(",");
        Task task = new Task(taskArray[2], taskArray[4], taskArray[3]);
        task.setId(Integer.parseInt(taskArray[0]));
        return task;
    }

}
