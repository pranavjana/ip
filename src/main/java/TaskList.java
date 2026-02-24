public class TaskList {
    private static final int MAX_TASKS = 100;

    private final Task[] tasks;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[MAX_TASKS];
        this.taskCount = 0;
    }

    public boolean addTask(Task task) {
        if (taskCount >= tasks.length) {
            return false;
        }
        tasks[taskCount] = task;
        taskCount++;
        return true;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public Task getTaskByNumber(int taskNumber) {
        if (taskNumber < 1 || taskNumber > taskCount) {
            return null;
        }
        return tasks[taskNumber - 1];
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
