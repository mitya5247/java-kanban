import com.sun.jdi.IntegerType;

import java.util.HashMap;

public class Task {
    protected int id;
    protected String status = "NEW";
    protected String name;
    protected String description = null;

    protected Task(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    protected Task() {
    }

    @Override
    public String toString() {
        return "Task='{" +
                "status='" + status + '\'' +
                "name='" + name + '\'' +
                "description='" + description + '\'' + '}';

    }
}
