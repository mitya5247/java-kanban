public class Main {

    public static void main(String[] args) {
        ManagerTask managerTask = new ManagerTask();
        Task task = new Task("Купить молоко", "Купить молоко, чтобы напоить их детей");
        Epic epic = new Epic("Построить дом", "Разбить на подзадачи");
        SubTask subTask = new SubTask("Взять ипотеку", "Как часть плана по покупке дома");
        System.out.println("Зарегистрирована задача под id " + managerTask.createTask(task));
        System.out.println("Зарегистрирована задача под id " + managerTask.createSubTask(subTask));
        System.out.println("Зарегистрирована задача под id " + managerTask.createEpic(epic));
        System.out.println("Мапа Task" + managerTask.taskMap);
        System.out.println("Мапа SubTask" + managerTask.subTaskMap);
        System.out.println("Мапа Epic" + managerTask.epicMap);
        System.out.println("Мапа Epic новая" + managerTask.epicMapWithID);










        //      System.out.println("Список задач");
   //     managerTask.getListTasks();
    //    managerTask.removeTask(2); // работает
    //    managerTask.updateTask(2); // работает
    //    managerTask.removeAllTasks();
    //    managerTask.getTask(2);
    }
}
