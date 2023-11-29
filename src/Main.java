public class Main {

    public static void main(String[] args) {
        ManagerTask managerTask = new ManagerTask();
        Epic epic = new Epic("Сделать ремонт в кваритире", "Проведение ремонта в квартире");

        SubTask subTask = new SubTask("Переклеить обои", "Выбрать цвет для обоев в комнате", "DONE");
        SubTask subTask1 = new SubTask("Повесить светильник", "Светильник над кроватью", "NEW");


        Epic epic1 = new Epic("Купить квартиру", "Квартира, чтобы сдавать");

        SubTask subTask2 = new SubTask("Начать копить денежку", "Чтобы взять ипотеку", "IN_PROGRESS");

        System.out.println("Эпик1 id " + managerTask.createEpic(epic));
        System.out.println("Подзадача 1 id" + managerTask.createSubTask(subTask));
        System.out.println("Подзадача 2 id" + managerTask.createSubTask(subTask1));


        System.out.println("Эпик2 id " + managerTask.createEpic(epic1));
        System.out.println("Подзадача 1 id" + managerTask.createSubTask(subTask2));


        managerTask.putSubTaskToEpic(epic, subTask);
        managerTask.updateEpic(epic);
        managerTask.putSubTaskToEpic(epic, subTask1);
        managerTask.updateEpic(epic);

        managerTask.putSubTaskToEpic(epic1, subTask2);
        managerTask.updateEpic(epic1);


        System.out.println(epic);
        System.out.println(epic1);

    }
}
