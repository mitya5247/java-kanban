import java.util.ArrayList;

public class Epic extends Task {
    SubTask subTask;
    ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
        subTasks.add(subTask);
    }
}
