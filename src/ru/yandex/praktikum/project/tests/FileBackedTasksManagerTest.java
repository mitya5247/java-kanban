package ru.yandex.praktikum.project.tests;

import org.junit.jupiter.api.*;
import ru.yandex.praktikum.project.main.engine.FileBackedTasksManager;
import ru.yandex.praktikum.project.main.engine.Managers;
import ru.yandex.praktikum.project.main.store.Epic;
import ru.yandex.praktikum.project.main.store.SubTask;
import ru.yandex.praktikum.project.main.store.Task;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FileBackedTasksManagerTest extends TaskManagerTest {

    @BeforeEach
    public void rewriteFile() throws FileNotFoundException {
        File file = new File("testNew.csv");
        try (FileWriter fileWriter = new FileWriter(file)) {
            Scanner scanner = new Scanner(file);
            String line = "";
            while (scanner.hasNext()) {
                fileWriter.write("");
                if (line.isBlank()) {
                    return;
                }
                line = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + file + " не прочитан");
        } catch (IOException e) {
            System.out.println(("Запись в файл не произошла. Проверьте FileWriter"));
        }
    }

    public String fileReader(File file) throws IOException {
        String line = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                line = scanner.nextLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла " + file);
        }
        return line;
    }

    @Test
    public void shoudldWriteToFile() {
        File file = new File("testNew.csv");

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        Task task0 = new Task("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));
        Task task1 = new Task("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 2, 0, 0));
        SubTask subTask = new SubTask("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));
        Epic epic = new Epic("0", "0");


        // пустой список задач
        Assertions.assertEquals(0, fileBackedTasksManager.getFileName().length());

        // эпик без подзадач
        fileBackedTasksManager.createEpic(epic);
        String expected = "1,EPIC,0,NEW,0, ,0,-999999999-01-01T00:00,null";
        try {
            Assertions.assertEquals(expected, this.fileReader(file));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        fileBackedTasksManager.createTask(task0);
        fileBackedTasksManager.createTask(task1);
        fileBackedTasksManager.createSubTask(subTask);

        // список задач заполнен, истории нет
        Assertions.assertTrue(fileBackedTasksManager.getFileName().length() != 0);
        Assertions.assertNull(FileBackedTasksManager.historyFromString(file));

    }

    @AfterAll
    public static void shouldLoadFromFile() {
        File file = new File("text.csv");
        FileBackedTasksManager newManager = FileBackedTasksManager.loadFromFile(file);

        Assertions.assertEquals(newManager.getTaskMap().get(1), Managers.getDefaultHistory().getHistory().get(0));

        Assertions.assertFalse(newManager.getTaskMap().isEmpty());
        Assertions.assertFalse(newManager.getSubTaskMap().isEmpty());
        Assertions.assertFalse(newManager.getEpicMap().isEmpty());


    }


}
