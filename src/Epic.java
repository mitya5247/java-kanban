import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Epic extends Task {
    SubTask subTask;
    ArrayList<SubTask> subTasks = new ArrayList<>();
    HashMap<String, ArrayList> epicSubTaskMap= new HashMap<>();
//    HashMap<Integer, HashMap> epicMap = new HashMap<>();



    public Epic(String name, String description) {
        super(name, description);
     //   subTasks.add(subTask);
    }


    public void createSubTask(String name, String description) {
        subTask = new SubTask(name, description);
    }

    @Override
    public String toString() {
        if (!(subTasks.isEmpty())) {
            return "Epic{" +
                    "id='" + id + '\'' +
                    "name='" + name + '\'' +
                    "subTasks=" + subTasks + '\'' +
                    '}';
        } else {
            return "Epic{" +
                    "id='" + id + '\'' +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
