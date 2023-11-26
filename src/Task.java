import com.sun.jdi.IntegerType;

import java.util.HashMap;

public class Task {
    int id = 0;
    String status = "NEW";
    String name;
    String description = null;
    HashMap<Integer, String> taskMap = new HashMap<>();

    public Task(String name, String description) {
        id = id + 1;
        this.name = name;
        this.description = description;
        taskMap.put(id, this.name);
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
