import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
  //  SubTask subTask;
    ArrayList<Integer> subTasksID = new ArrayList<>();
  //     HashMap<String, ArrayList> epicSubTaskMap= new HashMap<>();
//    HashMap<Integer, HashMap> epicMap = new HashMap<>();



    public Epic(String name, String description) {
        super(name, description);
     //   subTasks.add(subTask);
    }


  //  public void createSubTask(String name, String description) {
   //     subTask = new SubTask(name, description);
 //   }

    @Override
    public String toString() {
        if (!(subTasksID.isEmpty())) {
            return "Epic{" +
                    "id='" + id + '\'' +
                    "name='" + name + '\'' +
                    "subTasksID=" + subTasksID + '\'' +
                    '}';
        } else {
            return "Epic{" +
                    "id='" + id + '\'' +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
