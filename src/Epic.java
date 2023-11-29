import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {


    //  SubTask subTask;
    private String status = "NEW";
    ArrayList<Integer> subTasksID = new ArrayList<>();





    public Epic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        if (!(subTasksID.isEmpty())) {
            return "Epic{" +
                    "id='" + id + '\'' +
                    "name='" + name + '\'' +
                    "subTasksID=" + subTasksID + '\'' +
                    "status='" + status + '\'' +
                    '}';
        } else {
            return "Epic{" +
                    "id='" + id + '\'' +
                    "name='" + name + '\'' +
                    "status='" + status + '\'' +
                    '}';
        }
    }

}
