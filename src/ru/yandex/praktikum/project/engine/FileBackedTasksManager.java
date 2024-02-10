package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.exceptions.SaveException;
import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;
import ru.yandex.praktikum.project.store.Tasks;


import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileBackedTasksManager extends InMemoryTaskManager implements ManagerTask {

    File fileName;

    public FileBackedTasksManager(File file) {
        fileName = new File("text.csv");
    }

    public static void main(String[] args) {
        File file = new File("text.csv");

        FileBackedTasksManager oldManager = new FileBackedTasksManager(file);

        Task task0 = new Task("4", "4", "NEW", 15, "10:00");
        Task task1 = new Task("5", "5", "NEW", 20, "10:00");

        SubTask subTask1 = new SubTask("2", "2", "IN_PROGRESS", 15, "16:00");
        SubTask subTask2 = new SubTask("3", "3", "DONE", 20, "16:00");
        SubTask subTask3 = new SubTask("3", "3", "DONE", 20, "16:00");


        Epic epic = new Epic("14", "14");
        Epic epic1 = new Epic("15", "15");

        oldManager.createTask(task0);
        oldManager.createTask(task1);

        oldManager.createSubTask(subTask1);
        oldManager.createSubTask(subTask2);
        oldManager.createSubTask(subTask3);


        oldManager.createEpic(epic);
        oldManager.createEpic(epic1);

        oldManager.getSubTask(subTask1.getId());
    //    oldManager.getSubTask(subTask2.getId());
        oldManager.getTask(task0.getId());
        oldManager.getEpic(epic.getId());
        oldManager.getEpic(epic1.getId());
        oldManager.getSubTask(subTask1.getId());


        oldManager.putSubTaskToEpic(epic, subTask1);
  //      oldManager.putSubTaskToEpic(epic, subTask3);

  //      oldManager.putSubTaskToEpic(epic1, subTask2);

        System.out.println("Новый метод" + oldManager.getPrioritizedTasks());



        FileBackedTasksManager newManager = FileBackedTasksManager.loadFromFile(file); // создали новый менеджер из файла
        System.out.println(newManager.getTaskMap());
        System.out.println(newManager.getSubTaskMap());
        System.out.println(newManager.getEpicMap());



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
        return getTaskMap().get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        super.getSubTask(id);
        this.save();
        return getSubTaskMap().get(id);
    }

    @Override
    public Epic getEpic(int id) {
        super.getEpic(id);
        this.save();
        return getEpicMap().get(id);
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

    public static List<Integer> historyFromString(File file) {
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
        } catch (NumberFormatException e) {
            System.out.println("История в файле " + String.valueOf(file) + " не найдена");
            return null;
        } catch (FileNotFoundException e) {
            throw new SaveException("Файл " + file + " не найден");
        }
    }

    public void save() throws SaveException {
        String firstString = " ";
        String taskString = null;
        String taskIdEpic = " ";


        try (FileWriter fw = new FileWriter(String.valueOf(fileName))) {
            firstString = String.join(",", "id", "type", "name", "status",
                    "description", "epic", "duration", "startTime", "endTime" + ";" + "\n");
            fw.write(firstString);
            for (Task testTask : getTaskMap().values()) {
                taskString = String.join(",", String.valueOf(testTask.getId()),
                        String.valueOf(Tasks.TASK), testTask.getName(), testTask.getStatus(),
                        testTask.getDescription(), " " ,String.valueOf(testTask.getDuration()), testTask.getStartTimeInString(), testTask.getEndTime() + "\n");
                fw.write(taskString);
            }
            for (SubTask testTask : getSubTaskMap().values()) {
                if (testTask instanceof SubTask) {
                    taskIdEpic = String.valueOf(((SubTask) testTask).getIdEpic());
                }
                taskString = String.join(",", String.valueOf(testTask.getId()),
                        String.valueOf(Tasks.SUBTASK), testTask.getName(), testTask.getStatus(),
                        testTask.getDescription(), taskIdEpic, String.valueOf(testTask.getDuration()), testTask.getStartTimeInString(), testTask.getEndTime() + "\n");
                fw.write(taskString);
            }
            for (Epic testTask : getEpicMap().values()) {
                taskString = String.join(",", String.valueOf(testTask.getId()),
                        String.valueOf(Tasks.EPIC), testTask.getName(), testTask.getStatus(),
                        testTask.getDescription(), " " ,String.valueOf(testTask.getDuration()), testTask.getStartTimeInString(), testTask.getEndTime() + "\n");
                fw.write(taskString);
            }

            fw.write("\n");
            fw.write(FileBackedTasksManager.historyToString(historyManager));
        } catch (FileNotFoundException e) {
            throw new SaveException("Файл по пути " + fileName + " не найден, проверьте путь к файлу");
        } catch (IOException e) {
            throw new SaveException("Запись в файл не произошла. Проверьте FileWriter");
        } catch (NullPointerException e) {
            throw new SaveException("Проверьте наличие задач в taskMap, subTaskMap, epicMap");
        }
    }

    public void fromString(String value) {
        String[] taskArray = value.split(",");
        switch (taskArray[1]) {
            case "TASK": {
                Task task = new Task(taskArray[2], taskArray[4], taskArray[3],  Integer.parseInt(taskArray[6]), taskArray[7]);
                task.setId(Integer.parseInt(taskArray[0]));
                getTaskMap().put(task.getId(), task);
                break;
            }
            case "SUBTASK": {
                SubTask subTask = new SubTask(taskArray[2], taskArray[4], taskArray[3], Integer.parseInt(taskArray[6]), taskArray[7]);
                subTask.setId(Integer.parseInt(taskArray[0]));
                if (!taskArray[5].isBlank()) {
                    subTask.setIdEpic(Integer.parseInt(taskArray[5]));
                }
                getSubTaskMap().put(subTask.getId(), subTask);
                break;
            }
            case "EPIC": {
                Epic epic = new Epic(taskArray[2], taskArray[4]);
                epic.setId(Integer.parseInt(taskArray[0]));
                getEpicMap().put(epic.getId(), epic);
                epic.setDuration(Integer.parseInt(taskArray[6]));
                epic.getStartTime(LocalTime.parse(taskArray[7]));
                epic.getEndTime(LocalTime.parse(taskArray[7]).plusMinutes(Integer.parseInt(taskArray[6])));
                break;
            }
        }
    }

    public void taskFromFile() throws SaveException {
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
        } catch(FileNotFoundException e) {
            throw new SaveException("Файл по пути " + fileName + " не найден");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager.historyFromString(file);
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.taskFromFile();
        return fileBackedTasksManager;
    }


}
