package ru.yandex.praktikum.project.store;

public class SubTask extends Task {


    protected int idEpic;

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

    public SubTask(String name, String description, String status, int duration, String startTime) {
        super(name, description, status, duration, startTime);
    }
}
