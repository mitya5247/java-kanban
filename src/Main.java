public class Main {

    public static void main(String[] args) {
        ManagerTask managerTask = new ManagerTask();
        Epic epic = new Epic("Построить дом", "Разбить на подзадачи");
        SubTask subTask = new SubTask("Взять ипотеку", "Как часть плана по покупке дома", "DONE");
        SubTask subTask1 = new SubTask("Найти участок", "Выбрать землю, где я буду строиться", "DONE");
        SubTask subTask2 = new SubTask("Взять в аренду офис", "Посмотреть площади, где сдают помещения", "DONE");

        SubTask subTask3 = new SubTask("Взять ипотеку", "Как часть плана по покупке дома", "IN_PROGRESS");


        System.out.println("Зарегистрирована задача под id " + managerTask.createSubTask(subTask));
        System.out.println("Зарегистрирована задача под id " + managerTask.createSubTask(subTask1));
        System.out.println("Зарегистрирована задача под id " + managerTask.createSubTask(subTask2));
        System.out.println("Зарегистрирована задача под id " + managerTask.createSubTask(subTask3));

        System.out.println("Зарегистрирована задача под id " + managerTask.createEpic(epic));





        managerTask.putSubTaskToEpic(epic, subTask);
        managerTask.putSubTaskToEpic(epic, subTask1);
        managerTask.putSubTaskToEpic(epic, subTask2);
        managerTask.putSubTaskToEpic(epic, subTask3);



        managerTask.updateSubTask(subTask);
        managerTask.updateEpic(epic);

        System.out.println("Мапа со статусом " + managerTask.epicMap);



        managerTask.getListSubtasksOfEpic(epic);


















        //      System.out.println("Список задач");
   //     managerTask.getListTasks();
    //    managerTask.removeTask(2); // работает
    //    managerTask.updateTask(2); // работает
    //    managerTask.removeAllTasks();
    //    managerTask.getTask(2);
    }
}
