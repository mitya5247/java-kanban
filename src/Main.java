public class Main {

    public static void main(String[] args) {
        ManagerTask managerTask = new ManagerTask();
        managerTask.createTask();
        managerTask.createTask();
        managerTask.createTask();
        managerTask.createTask();


        System.out.println("Список задач");
        managerTask.getListTasks();
    //    managerTask.removeTask(2); // работает
        managerTask.updateTask(2);
    }
}
