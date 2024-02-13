package ru.yandex.praktikum.project.engine;


import org.junit.jupiter.api.*;
import ru.yandex.praktikum.project.store.Epic;
import ru.yandex.praktikum.project.store.Status;
import ru.yandex.praktikum.project.store.SubTask;
import ru.yandex.praktikum.project.store.Task;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public abstract class TaskManagerTest<T extends ManagerTask> {

    static InMemoryTaskManager memoryTaskManager;
    static FileBackedTasksManager fileTaskManager;

    @BeforeEach
    public void createManagers() {
        InMemoryTaskManager managerTask = new InMemoryTaskManager();

        File file = new File("test.csv");
        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);

        memoryTaskManager = managerTask;
        fileTaskManager = fileManager;
    }

    /*
    Проверка создания Task, SubTask, Epic
     */
    @Test
    public void shouldCreateTask() {
        Task task = new Task("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));


        // пустой список
        Assertions.assertEquals(0, memoryTaskManager.getTaskMap().size());
        Assertions.assertEquals(0, fileTaskManager.getTaskMap().size());

        memoryTaskManager.createTask(task);
        fileTaskManager.createTask(task);


        // стандартное поведение
        Assertions.assertEquals(task, memoryTaskManager.getTaskMap().get(task.getId()));
        Assertions.assertEquals(task, fileTaskManager.getTaskMap().get(task.getId()));


        // неверный идентификатор
        Assertions.assertNull(memoryTaskManager.getTaskMap().get(2));
        Assertions.assertNull(fileTaskManager.getTaskMap().get(2));

    }

    @Test
    public void shouldCreateSubTask() {
        SubTask subTask = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2024, 3, 1, 0, 0));

        // пустой список
        Assertions.assertEquals(0, memoryTaskManager.getSubTaskMap().size());
        Assertions.assertEquals(0, fileTaskManager.getSubTaskMap().size());

        // стандартное поведение
        memoryTaskManager.createSubTask(subTask);
        fileTaskManager.createSubTask(subTask);

        Assertions.assertEquals(subTask, memoryTaskManager.getSubTaskMap().get(subTask.getId()));
        Assertions.assertEquals(subTask, fileTaskManager.getSubTaskMap().get(subTask.getId()));

        // неверный идентификатор
        Assertions.assertNull(memoryTaskManager.getSubTaskMap().get(2));
        Assertions.assertNull(fileTaskManager.getSubTaskMap().get(2));
    }

    @Test
    public void shouldCreateEpic() {
        Epic epic = new Epic("1", "1");

        // пустой список
        Assertions.assertEquals(0, memoryTaskManager.getEpicMap().size());
        Assertions.assertEquals(0, fileTaskManager.getEpicMap().size());

        // стандартное поведение
        memoryTaskManager.createEpic(epic);
        fileTaskManager.createEpic(epic);

        Assertions.assertEquals(epic, memoryTaskManager.getEpicMap().get(epic.getId()));
        Assertions.assertEquals(epic, fileTaskManager.getEpicMap().get(epic.getId()));

        // неверный идентификатор
        Assertions.assertNull(memoryTaskManager.getEpicMap().get(2));
        Assertions.assertNull(fileTaskManager.getEpicMap().get(2));

    }

    /*
    Запрос Task, SubTask и Epic
     */
    @Test
    public void shouldGetTask() {
        Task task = new Task("1", "1", "NEW", 40, LocalDateTime.of(2024, 3, 1, 0, 0));

        // пустой список
        Assertions.assertEquals(0, memoryTaskManager.getTaskMap().size());
        Assertions.assertEquals(0, fileTaskManager.getTaskMap().size());

        // стандартное поведение
        memoryTaskManager.createTask(task);
        fileTaskManager.createTask(task);

        Assertions.assertEquals(task, memoryTaskManager.getTask(task.getId()));
        Assertions.assertEquals(task, fileTaskManager.getTask(task.getId()));

        // неверный Id
        Assertions.assertThrows(
                NullPointerException.class,
                () -> memoryTaskManager.getTask(2)
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> fileTaskManager.getTask(2)
        );


    }

    @Test
    public void shouldGetSubTask() {
        SubTask subTask = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2024, 3, 1, 0, 0));

        // пустой список
        Assertions.assertEquals(0, memoryTaskManager.getSubTaskMap().size());
        Assertions.assertEquals(0, fileTaskManager.getSubTaskMap().size());

        // стандартное поведение
        memoryTaskManager.createSubTask(subTask);
        fileTaskManager.createSubTask(subTask);

        Assertions.assertEquals(subTask, memoryTaskManager.getSubTask(subTask.getId()));
        Assertions.assertEquals(subTask, fileTaskManager.getSubTask(subTask.getId()));

        // неверный Id
        Assertions.assertThrows(
                NullPointerException.class,
                () -> memoryTaskManager.getSubTask(2)
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> fileTaskManager.getSubTask(2)
        );

    }

    @Test
    public void shouldGetEpic() {
        Epic epic = new Epic("1", "1");

        // пустой список
        Assertions.assertEquals(0, memoryTaskManager.getEpicMap().size());
        Assertions.assertEquals(0, fileTaskManager.getEpicMap().size());

        // стандартное поведение
        memoryTaskManager.createEpic(epic);
        fileTaskManager.createEpic(epic);

        Assertions.assertEquals(epic, memoryTaskManager.getEpicMap().get(epic.getId()));
        Assertions.assertEquals(epic, fileTaskManager.getEpicMap().get(epic.getId()));

        // неверный идентификатор
        Assertions.assertThrows(
                NullPointerException.class,
                () -> memoryTaskManager.getEpic(2)
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> fileTaskManager.getEpic(2)
        );
    }

    /*
    Обновление Task, SubTask, Epic
     */

    @Test
    public void shouldUpdateTask() {
        Task task0 = new Task("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));
        Task task1 = new Task("2", "2", "DONE", 40, LocalDateTime.of(2024, 2, 1, 0, 0));

        // пустой список
        memoryTaskManager.updateTask(task0);
        fileTaskManager.updateTask(task0);


        // стандартная работа
        memoryTaskManager.createTask(task0);
        fileTaskManager.createTask(task0);
        task1.setId(1);
        memoryTaskManager.updateTask(task1);
        fileTaskManager.updateTask(task1);

        Assertions.assertEquals(task1, memoryTaskManager.getTaskMap().get(task1.getId()));
        Assertions.assertEquals(1, memoryTaskManager.getTaskMap().size());

        Assertions.assertEquals(task1, memoryTaskManager.getTaskMap().get(task1.getId()));
        Assertions.assertEquals(1, fileTaskManager.getTaskMap().size());

        // неверный id
        task0.setId(4);
        memoryTaskManager.updateTask(task0);
        fileTaskManager.updateTask(task0);
        Assertions.assertEquals(task1, memoryTaskManager.getTaskMap().get(task1.getId()));
        Assertions.assertEquals(task1, fileTaskManager.getTaskMap().get(task1.getId()));


    }

    @Test
    public void shouldUpdateSubTask() {
        SubTask subTask0 = new SubTask("1", "1", "NEW", 20, LocalDateTime.of(2024, 1, 1, 0, 0));
        SubTask subTask1 = new SubTask("1", "1", "NEW", 20, LocalDateTime.of(2024, 2, 1, 0, 0));

        // пустой список
        memoryTaskManager.updateTask(subTask0);
        fileTaskManager.updateTask(subTask0);


        // стандартная работа
        memoryTaskManager.createTask(subTask0);
        fileTaskManager.createTask(subTask0);
        subTask1.setId(1);
        memoryTaskManager.updateTask(subTask1);
        fileTaskManager.updateTask(subTask1);

        Assertions.assertEquals(subTask1, memoryTaskManager.getTaskMap().get(subTask1.getId()));
        Assertions.assertEquals(1, memoryTaskManager.getTaskMap().size());

        Assertions.assertEquals(subTask1, memoryTaskManager.getTaskMap().get(subTask1.getId()));
        Assertions.assertEquals(1, fileTaskManager.getTaskMap().size());

        // неверный id
        subTask0.setId(4);
        memoryTaskManager.updateTask(subTask0);
        fileTaskManager.updateTask(subTask0);
        Assertions.assertEquals(subTask1, memoryTaskManager.getTaskMap().get(subTask1.getId()));
        Assertions.assertEquals(subTask1, fileTaskManager.getTaskMap().get(subTask1.getId()));



    }

    @Test
    public void shouldUpdateEpic() {
        Epic epic0 = new Epic("1", "1");
        Epic epic1 = new Epic("1", "1");


        // пустой список
        memoryTaskManager.updateEpic(epic0);
        fileTaskManager.updateEpic(epic0);
        Assertions.assertEquals(0, memoryTaskManager.getEpicMap().size());
        Assertions.assertEquals(0, fileTaskManager.getEpicMap().size());


        // стандартная работа
        memoryTaskManager.createEpic(epic0);
        fileTaskManager.createEpic(epic0);

        epic1.setId(epic0.getId());

        memoryTaskManager.updateEpic(epic1);
        fileTaskManager.updateEpic(epic1);

        Assertions.assertEquals(1, memoryTaskManager.getEpicMap().size());
        Assertions.assertEquals(epic1, memoryTaskManager.getEpicMap().get(epic0.getId()));

        Assertions.assertEquals(1, fileTaskManager.getEpicMap().size());
        Assertions.assertEquals(epic1, fileTaskManager.getEpicMap().get(epic0.getId()));

        // неверный id

        epic0.setId(4);
        memoryTaskManager.updateEpic(epic0);
        fileTaskManager.updateEpic(epic0);
        Assertions.assertEquals(epic1, memoryTaskManager.getEpicMap().get(epic1.getId()));
        Assertions.assertEquals(epic1, fileTaskManager.getEpicMap().get(epic1.getId()));



    }

    @Test
    public void shouldHaveEpicInSubTask() {
        Epic epic = new Epic("1", "1");
        SubTask subTask = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2024, 3, 1, 0, 0));

        memoryTaskManager.createSubTask(subTask);
        memoryTaskManager.createEpic(epic);

        fileTaskManager.createSubTask(subTask);
        fileTaskManager.createEpic(epic);

        // пустой список
        Assertions.assertEquals(0, memoryTaskManager.getSubTaskMap().get(subTask.getId()).getIdEpic());
        Assertions.assertEquals(0, fileTaskManager.getSubTaskMap().get(subTask.getId()).getIdEpic());


        memoryTaskManager.putSubTaskToEpic(epic, subTask);
        fileTaskManager.putSubTaskToEpic(epic, subTask);

        // нормальная работа
        int expected = epic.getId();

        Assertions.assertEquals(expected, memoryTaskManager.getSubTaskMap().get(subTask.getId()).getIdEpic());
        Assertions.assertEquals(expected, fileTaskManager.getSubTaskMap().get(subTask.getId()).getIdEpic());

        /*
        Неправильный Id в данном случае задать нельзя, так как метод getIdEpic() не приминает на вхож никаких аргументов
         */
    }

    @Test
    public void shouldPutSubTaskToEpic() {
        SubTask subTask = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2024, 3, 1, 0, 0));
        Epic epic = new Epic("1", "1");

        SubTask subTask1 = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2024, 4, 1, 0, 0));

        memoryTaskManager.createSubTask(subTask);
        memoryTaskManager.createEpic(epic);

        // пустой список
        Assertions.assertTrue(epic.getSubTasksId().isEmpty());

        fileTaskManager.createSubTask(subTask);
        fileTaskManager.createEpic(epic);

        // нормальная работа метода
        memoryTaskManager.putSubTaskToEpic(epic, subTask);
        fileTaskManager.putSubTaskToEpic(epic, subTask);


        Assertions.assertEquals(subTask.getId(), epic.getSubTasksId().get(0));

        // неправильная работа метода


        Assertions.assertThrows(NullPointerException.class, () -> memoryTaskManager.putSubTaskToEpic(epic, subTask1));

    }

    @Test
    public void shouldUpdateStatusOfEpic() {
        Epic epic = new Epic("1", "1");
        SubTask subTask = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2024, 3, 1, 0, 0));
        SubTask subTask1 = new SubTask("1", "1", "IN_PROGRESS", 40, LocalDateTime.of(2024, 2, 1, 15, 0));

        memoryTaskManager.createSubTask(subTask);
        memoryTaskManager.createSubTask(subTask1);
        memoryTaskManager.createEpic(epic);

        fileTaskManager.createSubTask(subTask);
        fileTaskManager.createSubTask(subTask1);
        fileTaskManager.createEpic(epic);

        String expected = "NEW";

        // пустой эпик
        Assertions.assertEquals(expected, memoryTaskManager.getEpicMap().get(epic.getId()).getStatus());
        Assertions.assertEquals(expected, fileTaskManager.getEpicMap().get(epic.getId()).getStatus());

        // нормальная работа
        memoryTaskManager.putSubTaskToEpic(epic, subTask);
        memoryTaskManager.putSubTaskToEpic(epic, subTask1);

        fileTaskManager.putSubTaskToEpic(epic, subTask);
        fileTaskManager.putSubTaskToEpic(epic, subTask1);

        expected = Status.IN_PROGRESS.toString();

        Assertions.assertEquals(expected, memoryTaskManager.getEpicMap().get(epic.getId()).getStatus());
        Assertions.assertEquals(expected, fileTaskManager.getEpicMap().get(epic.getId()).getStatus());


    }

    /*
 Удаление Task, SubTask, Epic
  */

    @Test
    public void shouldRemoveTask() {
        Task task = new Task("1", "1", "NEW", 40, LocalDateTime.of(2024, 3, 1, 0, 0));

        // нормальная работа
        fileTaskManager.createTask(task);
        memoryTaskManager.createTask(task);


        Assertions.assertEquals(1, memoryTaskManager.getTaskMap().size());
        Assertions.assertEquals(1, fileTaskManager.getTaskMap().size());

        memoryTaskManager.removeTask(task.getId());
        fileTaskManager.removeTask(task.getId());

        Assertions.assertEquals(0, memoryTaskManager.getTaskMap().size());
        Assertions.assertEquals(0, fileTaskManager.getTaskMap().size());

        //   неправильный Id
        memoryTaskManager.removeTask(6);
        fileTaskManager.removeTask(5);

        Assertions.assertEquals(0, memoryTaskManager.getTaskMap().size());
        Assertions.assertEquals(0, fileTaskManager.getTaskMap().size());


    }

    @Test
    public void shouldRemoveSubTask() {
        SubTask subTask = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2024, 3, 1, 0, 0));


        // пустой список
        Assertions.assertThrows(NullPointerException.class, () -> memoryTaskManager.removeSubTask(subTask.getId()));
        Assertions.assertThrows(NullPointerException.class, () -> fileTaskManager.removeSubTask(subTask.getId()));

        // нормельная работа
        memoryTaskManager.createSubTask(subTask);
        int mapSizeTask = memoryTaskManager.getSubTaskMap().size();

        Assertions.assertEquals(1, mapSizeTask);
        memoryTaskManager.removeSubTask(subTask.getId());

        int mapSizeNew = memoryTaskManager.getSubTaskMap().size();
        Assertions.assertEquals(0, mapSizeNew);


        // файловый менеджер
        fileTaskManager.createSubTask(subTask);
        int mapSize = fileTaskManager.getSubTaskMap().size();

        Assertions.assertEquals(1, mapSize);
        fileTaskManager.removeSubTask(subTask.getId());

        int mapSizeNewSub = fileTaskManager.getSubTaskMap().size();
        Assertions.assertEquals(0, mapSizeNewSub);

        // неверный id
        fileTaskManager.createSubTask(subTask);
        memoryTaskManager.createSubTask(subTask);

        Assertions.assertThrows(NullPointerException.class, () -> memoryTaskManager.removeSubTask(3));
        Assertions.assertThrows(NullPointerException.class, () -> fileTaskManager.removeSubTask(5));

    }

    @Test
    public void shouldRemoveEpic() {
        Epic epic = new Epic("1", "1");

        // пустой список
        Assertions.assertThrows(NullPointerException.class, () -> memoryTaskManager.removeEpic(epic.getId()));
        Assertions.assertThrows(NullPointerException.class, () -> fileTaskManager.removeEpic(epic.getId()));

        // нормальная работа метода
        memoryTaskManager.createEpic(epic);
        fileTaskManager.createEpic(epic);

        Assertions.assertEquals(1, memoryTaskManager.getEpicMap().size());
        Assertions.assertEquals(1, fileTaskManager.getEpicMap().size());

        memoryTaskManager.removeEpic(epic.getId());
        fileTaskManager.removeEpic(epic.getId());

        Assertions.assertEquals(0, memoryTaskManager.getEpicMap().size());
        Assertions.assertEquals(0, fileTaskManager.getEpicMap().size());

        // неправильный Id
        Assertions.assertThrows(NullPointerException.class, () -> memoryTaskManager.removeEpic(10));
        Assertions.assertThrows(NullPointerException.class, () -> fileTaskManager.removeEpic(10));


    }

    @Test
    public void shouldRemoveAllTasks() {
        Task task0 = new Task("1", "1", "NEW", 40, LocalDateTime.of(2023, 3, 1, 0, 0));
        Task task1 = new Task("1", "1", "NEW", 40, LocalDateTime.of(2022, 3, 1, 0, 0));
        Task task2 = new Task("1", "1", "NEW", 40, LocalDateTime.of(2021, 3, 1, 0, 0));

        // пустой список
        memoryTaskManager.removeTasksAll();
        fileTaskManager.removeTasksAll();

        Assertions.assertEquals(0, memoryTaskManager.getTaskMap().size());
        Assertions.assertEquals(0, fileTaskManager.getTaskMap().size());

        // нормальная работа метода
        memoryTaskManager.createTask(task0);
        memoryTaskManager.createTask(task1);
        memoryTaskManager.createTask(task2);

        fileTaskManager.createTask(task0);
        fileTaskManager.createTask(task1);
        fileTaskManager.createTask(task2);

        Assertions.assertEquals(3, memoryTaskManager.getTaskMap().size());
        Assertions.assertEquals(3, fileTaskManager.getTaskMap().size());

        memoryTaskManager.removeTasksAll();
        fileTaskManager.removeTasksAll();

        Assertions.assertEquals(0, memoryTaskManager.getTaskMap().size());
        Assertions.assertEquals(0, fileTaskManager.getTaskMap().size());

    }

    @Test
    public void shouldRemoveAllSubTasks() {
        SubTask subTask0 = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2023, 3, 1, 0, 0));
        SubTask subTask1 = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2022, 3, 1, 0, 0));
        SubTask subTask2 = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2021, 3, 1, 0, 0));

        // пустой список
        memoryTaskManager.removeSubTasksAll();
        fileTaskManager.removeSubTasksAll();

        Assertions.assertEquals(0, memoryTaskManager.getSubTaskMap().size());
        Assertions.assertEquals(0, fileTaskManager.getSubTaskMap().size());

        // нормальная работа метода
        memoryTaskManager.createSubTask(subTask0);
        memoryTaskManager.createSubTask(subTask1);
        memoryTaskManager.createSubTask(subTask2);

        fileTaskManager.createSubTask(subTask0);
        fileTaskManager.createSubTask(subTask1);
        fileTaskManager.createSubTask(subTask2);

        Assertions.assertEquals(3, memoryTaskManager.getSubTaskMap().size());
        Assertions.assertEquals(3, fileTaskManager.getSubTaskMap().size());

        memoryTaskManager.removeSubTasksAll();
        fileTaskManager.removeSubTasksAll();

        Assertions.assertEquals(0, memoryTaskManager.getSubTaskMap().size());
        Assertions.assertEquals(0, fileTaskManager.getSubTaskMap().size());

    }

    @Test
    public void shouldRemoveAllEpics() {
        Epic epic0 = new Epic("1", "1");
        Epic epic1 = new Epic("2", "1");
        Epic epic2 = new Epic("3", "1");


        memoryTaskManager.removeEpicsAll();
        fileTaskManager.removeEpicsAll();


        memoryTaskManager.createEpic(epic0);
        fileTaskManager.createEpic(epic0);

        memoryTaskManager.createEpic(epic1);
        fileTaskManager.createEpic(epic1);

        memoryTaskManager.createEpic(epic2);
        fileTaskManager.createEpic(epic2);

        Assertions.assertEquals(3, memoryTaskManager.getEpicMap().size());
        Assertions.assertEquals(3, fileTaskManager.getEpicMap().size());

        memoryTaskManager.removeEpicsAll();
        fileTaskManager.removeEpicsAll();

        Assertions.assertEquals(0, memoryTaskManager.getEpicMap().size());
        Assertions.assertEquals(0, fileTaskManager.getEpicMap().size());

    }

    @Test
    public void shouldMakeValidation() {
        SubTask subTask0 = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2023, 3, 1, 0, 0));
        SubTask subTask1 = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2023, 3, 1, 0, 0));

        Task task0 = new Task("1", "1", "NEW", 40, LocalDateTime.of(2023, 3, 1, 0, 0));
        Task task1 = new Task("1", "1", "NEW", 40, LocalDateTime.of(2023, 3, 1, 0, 0));

        memoryTaskManager.createTask(task0); // валидация таска
        memoryTaskManager.createTask(task1);

        memoryTaskManager.createSubTask(subTask0); // валидация сабтаска
        memoryTaskManager.createSubTask(subTask1);

    }

    @Test
    public void shouldGetPrioritizedTasks() {
        SubTask subTask0 = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2023, 3, 1, 10, 0));
        SubTask subTask1 = new SubTask("1", "1", "NEW", 40, LocalDateTime.of(2023, 3, 1, 5, 0));

        Task task0 = new Task("1", "1", "NEW", 40, LocalDateTime.of(2023, 3, 1, 2, 0));
        Task task1 = new Task("1", "1", "NEW", 40, LocalDateTime.of(2023, 3, 1, 12, 0));

        memoryTaskManager.createTask(task0);
        memoryTaskManager.createTask(task1);

        memoryTaskManager.createSubTask(subTask0);
        memoryTaskManager.createSubTask(subTask1);

        List<Task> tasks = List.of(task0, task1, subTask0, subTask1);

        LocalDateTime earlyTime = tasks.get(0).getStartTime();

        LocalDateTime lateTime = tasks.get(0).getStartTime();

        for (Task task : tasks) {
            if (earlyTime.isAfter(task.getStartTime())) {
                earlyTime = task.getStartTime();
            }
            if (lateTime.isBefore(task.getStartTime())) {
                lateTime = task.getStartTime();
            }
        }

        memoryTaskManager.getPrioritizedTasks();

        Assertions.assertEquals(earlyTime, memoryTaskManager.getPrioritizedTasks().get(0)
                .getStartTime()); // проверка первого элемента списка как самого раннего
        Assertions.assertEquals(lateTime, memoryTaskManager.getPrioritizedTasks().get
                (memoryTaskManager.getPrioritizedTasks().size() - 1)
                .getStartTime()); // проверка первого элемента списка как самого позднего

    }


}
