import com.sun.jdi.IntegerType;

import java.util.HashMap;

public class Task {
    int id;
    String status = "NEW";
    String name;
    String description = null;
    HashMap<Integer, String> taskMap = new HashMap<>();

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task='{" +
                "status='" + status + '\'' +
                "name='" + name + '\'' +
                "description='" + description + '\'' +
                "taskMap='" + taskMap + '\'' + '}';

    }
}
