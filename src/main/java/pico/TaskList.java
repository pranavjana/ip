package pico;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public boolean addTask(Task task) {
        tasks.add(task);
        return true;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public Task getTaskByNumber(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            return null;
        }
        return tasks.get(taskNumber - 1);
    }

    public Task deleteTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            return null;
        }
        return tasks.remove(taskNumber - 1);
    }

    public Task markTask(int taskNumber) {
        Task task = getTaskByNumber(taskNumber);
        if (task == null) {
            return null;
        }
        task.markAsDone();
        return task;
    }

    public Task unmarkTask(int taskNumber) {
        Task task = getTaskByNumber(taskNumber);
        if (task == null) {
            return null;
        }
        task.markAsNotDone();
        return task;
    }
}
