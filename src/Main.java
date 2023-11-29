public class Main {

    public static void main(String[] args) {
        ManagerTask managerTask = new ManagerTask();
        Task task = new Task("Купить молоко", "Купить молоко, чтобы напоить их детей");
        Epic epic = new Epic("Построить дом", "Разбить на подзадачи");
        SubTask subTask = new SubTask("Взять ипотеку", "Как часть плана по покупке дома");
        SubTask subTask1 = new SubTask("Найти участок", "Выбрать землю, где я буду строиться");
        SubTask subTask2 = new SubTask("Взять в аренду офис", "    ");
        Epic epic1 = new Epic("Открыть бизнес", "Хочу открыть бизнес для себя и семьи");


        System.out.println("Зарегистрирована задача под id " + managerTask.createTask(task));
        System.out.println("Зарегистрирована задача под id " + managerTask.createSubTask(subTask));
        System.out.println("Зарегистрирована задача под id " + managerTask.createEpic(epic));
        System.out.println("Зарегистрирована задача под id " + managerTask.createSubTask(subTask1));
        System.out.println("Зарегистрирована задача под id " + managerTask.createEpic(epic1));
        System.out.println("Зарегистрирована задача под id " + managerTask.createSubTask(subTask2));



        managerTask.putSubTaskToEpic(epic, subTask);
        managerTask.putSubTaskToEpic(epic, subTask1);
        managerTask.putSubTaskToEpic(epic1, subTask2);

        managerTask.getListAll();














        //      System.out.println("Список задач");
   //     managerTask.getListTasks();
    //    managerTask.removeTask(2); // работает
    //    managerTask.updateTask(2); // работает
    //    managerTask.removeAllTasks();
    //    managerTask.getTask(2);
    }
}
