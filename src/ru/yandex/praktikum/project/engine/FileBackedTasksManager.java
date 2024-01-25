package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.exceptions.SaveException;
import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;
import ru.yandex.praktikum.project.store.Tasks;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileBackedTasksManager extends InMemoryTaskManager implements ManagerTask {

    File fileName;

    public FileBackedTasksManager(File file) {
        fileName = new File("/home/mitya5247/Программирование/dev/java-kanban/java-kanban/text.csv");
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File("/home/mitya5247/Программирование/dev/java-kanban/java-kanban/text.csv");
       // FileBackedTasksManager.loadFromFile(file);
        System.out.println(FileBackedTasksManager.loadFromFile(file).taskMap);
        System.out.println(FileBackedTasksManager.loadFromFile(file).subTaskMap);
        System.out.println(FileBackedTasksManager.loadFromFile(file).epicMap);

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

    @Override
    public void updateStatusEpic(Epic epic) {
        super.updateStatusEpic(epic);
        this.save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void putSubTaskToEpic(Epic epic, SubTask subTask) {
        super.putSubTaskToEpic(epic, subTask);
        save();
    }

    @Override
    public Task getTask(int id) {
        super.getTask(id);
        this.save();
        return taskMap.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        super.getSubTask(id);
        this.save();
        return subTaskMap.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        super.getEpic(id);
        this.save();
        historyManager.remove(id);
        return epicMap.get(id);
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        historyManager.remove(id);
        this.save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        historyManager.remove(id);
        this.save();
    }

    @Override
    public void removeSubTask(int id) {
        super.removeSubTask(id);
        historyManager.remove(id);
        this.save();
    }

    public String read() {
        String lastString = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(fileName)))) {
            while (bufferedReader.ready()) {
                String string = bufferedReader.readLine();
                lastString = string;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lastString;
    }

    public static String historyToString(InMemoryHistoryManager manager) {
        String history = "";
        StringBuilder stringBuilder = new StringBuilder();
        List<Task> listHistory = manager.getHistory();
        for (Task task : listHistory) {
            stringBuilder.append(task.getId() + ",");
        }
        history = stringBuilder.toString();
        return history;
    }

    public static List<Integer> historyFromString(File file) throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(file);
            String line = "";
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                if (!scanner.hasNextLine()) {
                    break;
                }
            }
            List<Integer> listIdHistory = new ArrayList<>();
            String[] idArray = line.split(",");
            for (String id : idArray) {
                listIdHistory.add(Integer.parseInt(id));
            }
            return listIdHistory;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл не найден");
        } catch (NumberFormatException e) {
            System.out.println("История в файле не найдена");
            return null;
        }
    }

    public void save() throws SaveException {
        String firstString = " ";
        String taskString = null;
        String taskIdEpic = " ";


        try (FileWriter fw = new FileWriter(String.valueOf(fileName))) {
            firstString = String.join(",", "id", "type", "name", "status",
                    "description", "epic" + ";" + "\n");
            fw.write(firstString);
            for (Task testTask : taskMap.values()) {
                taskString = String.join(",", String.valueOf(testTask.getId()),
                        String.valueOf(Tasks.TASK), testTask.getName(), testTask.getDescription(),
                        testTask.getDescription() + "\n");
                fw.write(taskString);
            }
            for (SubTask testTask : subTaskMap.values()) {
                if (testTask instanceof SubTask) {
                    taskIdEpic = String.valueOf(((SubTask) testTask).getIdEpic());
                }
                taskString = String.join(",", String.valueOf(testTask.getId()),
                        String.valueOf(Tasks.SUBTASK), testTask.getName(), testTask.getDescription(),
                        testTask.getDescription(), taskIdEpic + "\n");
                fw.write(taskString);
            }
            for (Epic testTask : epicMap.values()) {
                taskString = String.join(",", String.valueOf(testTask.getId()),
                        String.valueOf(Tasks.EPIC), testTask.getName(), testTask.getDescription(),
                        testTask.getDescription() + "\n");
                fw.write(taskString);
            }

            fw.write("\n");
            fw.write(FileBackedTasksManager.historyToString(historyManager));
        } catch (IOException e) {
            throw new SaveException("Ошибка сохранения файла");
        }
    }

    public void fromString(String value) {
        String[] taskArray = value.split(",");
        switch (taskArray[1]) {
            case "TASK": {
                Task task = new Task(taskArray[2], taskArray[4], taskArray[3]);
                task.setId(Integer.parseInt(taskArray[0]));
                taskMap.put(task.getId(), task);
                break;
            }
            case "SUBTASK": {
                SubTask subTask = new SubTask(taskArray[2], taskArray[4], taskArray[3]);
                subTask.setId(Integer.parseInt(taskArray[0]));
                if (!taskArray[5].isBlank()) {
                    subTask.setIdEpic(Integer.parseInt(taskArray[5]));
                }
                subTaskMap.put(subTask.getId(), subTask);
                break;
            }
            case "EPIC": {
                Epic epic = new Epic(taskArray[2], taskArray[4]);
                epic.setId(Integer.parseInt(taskArray[0]));
                epicMap.put(epic.getId(), epic);
                break;
            }
        }
    }

    public void taskFromFile() throws IOException {
        try {
            Scanner scanner = new Scanner(fileName);
            String line = scanner.nextLine();
            while (!line.isBlank()) {
                line = scanner.nextLine();
                if (line.isBlank()) {
                    return;
                }
                this.fromString(line);
            }
        } catch (IOException e) {
            throw new SaveException("Ошибка загрузки задач из файла");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) throws FileNotFoundException, IOException {
        FileBackedTasksManager.historyFromString(file);
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.taskFromFile();
        return fileBackedTasksManager;
    }


}
