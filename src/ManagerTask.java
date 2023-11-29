import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ManagerTask {

    int nextID = 1;
    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    HashMap<Integer, Epic> epicMap = new HashMap<>(); // хранение эпиков как объектов по id после создания


    ArrayList<HashMap> taskArrayAll = new ArrayList<>();

    public ManagerTask() {
      //  taskArrayAll.add(taskMap);
       // taskArrayAll.add(subTaskMap);
      //  taskArrayAll.add(epicMap);
    }




    void getListTasks() {
        for (Task task : taskMap.values()) {
            System.out.println(task);
        }
    }

    void getListSubTasks() {
        for (SubTask subTask : subTaskMap.values()) {
            System.out.println(subTask);
        }
    }

    void getListEpics() {
        for (Epic epic : epicMap.values()) {
            System.out.println(epic);
        }
    }

    void removeTasks() {
        taskMap.clear();
    }
    void removeSubTasks() {
        subTaskMap.clear();
    }
    void removeEpics() {
        epicMap.clear();
    }


  //  Object getTask(int id) {
    //    Object myHashMap = null;
       // for (HashMap hashMap : taskArrayAll) {
       //     hashMap.get(id);
      ///      myHashMap = hashMap.get(id);
      //      System.out.println("Мапа, которую вернули");
    //        System.out.println(myHashMap);

 //           }
     //   return myHashMap;
  //  }


    int createTask(Task task) {
        task.id = nextID;
        nextID++;
        taskMap.put(task.id, task);
        return task.id;
    }
    int createSubTask(SubTask subTask) {
        subTask.id = nextID;
        nextID++;
        subTaskMap.put(subTask.id, subTask);
        return subTask.id;
    }


    int createEpic(Epic epic) {
        epic.id = nextID;
        nextID++;
        epicMap.put(epic.id, epic); // скорее всего поменяем
        return epic.id;
    }

    void putSubTaskToEpic(Epic epic, SubTask subTask) {
        epic.subTasksID.add(subTask.id);
        subTask.idEpic = epic.id;
    }







//    void updateTask(int id, Task task) {
  //      for (HashMap hashMap : taskArrayAll) {
    //        if (hashMap.containsKey(id)) {
      //          hashMap.get(id);
        //        System.out.println("Введите что хотите обновить");
          //      String newName = scanner.next();
    //            hashMap.put(id, task.name);
    //        } else {
     //           System.out.println("Задачей с таким id не найдено");
 //           }
 //       }
 //       System.out.println("После изменения нужного id");
 //       this.getListTasks();
 //   }


    void removeTask(int id) {
        for (HashMap hashMap : taskArrayAll) {
            if (hashMap.containsKey(id)) {
                hashMap.remove(id);
            } else {
                System.out.println("Задачей с таким id не найдено");
            }
        }
        System.out.println("После удаления нужного id");
        this.getListTasks();
    }


}
