package pico;

public class Pico {
    private static final String DATA_FILE_PATH = "./data/pico.txt";

    private final Storage storage;
    private final Ui ui;
    private TaskList taskList;

    public Pico(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            taskList = storage.load();
        } catch (PicoException e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        String input = ui.readCommand();
        while (!input.equals("bye")) {
            ui.showLine();
            try {
                handleCommand(input);
            } catch (PicoException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
            input = ui.readCommand();
        }
        ui.showGoodbye();
        ui.close();
    }

    private void handleCommand(String input) throws PicoException {
        String commandWord = Parser.getCommandWord(input);

        switch (commandWord) {
        case "list":
            ui.showTaskList(taskList);
            break;
        case "mark":
            handleMark(input);
            break;
        case "unmark":
            handleUnmark(input);
            break;
        case "delete":
            handleDelete(input);
            break;
        case "todo":
            addTask(Parser.parseTodo(input));
            break;
        case "deadline":
            addTask(Parser.parseDeadline(input));
            break;
        case "event":
            addTask(Parser.parseEvent(input));
            break;
        default:
            throw new PicoException("Beep boop! That command doesn't exist on my planet.");
        }
    }

    private void addTask(Task task) throws PicoException {
        taskList.addTask(task);
        storage.save(taskList);
        ui.showTaskAdded(task, taskList.getTaskCount());
    }

    private void handleDelete(String input) throws PicoException {
        int taskNumber = Parser.parseTaskNumber(input, "delete");
        Task task = taskList.deleteTask(taskNumber);
        if (task == null) {
            throw new PicoException("Task " + taskNumber + " doesn't exist in my star chart!");
        }
        storage.save(taskList);
        ui.showTaskDeleted(task, taskList.getTaskCount());
    }

    private void handleMark(String input) throws PicoException {
        int taskNumber = Parser.parseTaskNumber(input, "mark");
        Task task = taskList.markTask(taskNumber);
        if (task == null) {
            throw new PicoException("Task " + taskNumber + " doesn't exist in my star chart!");
        }
        storage.save(taskList);
        ui.showTaskMarked(task);
    }

    private void handleUnmark(String input) throws PicoException {
        int taskNumber = Parser.parseTaskNumber(input, "unmark");
        Task task = taskList.unmarkTask(taskNumber);
        if (task == null) {
            throw new PicoException("Task " + taskNumber + " doesn't exist in my star chart!");
        }
        storage.save(taskList);
        ui.showTaskUnmarked(task);
    }

    public static void main(String[] args) {
        new Pico(DATA_FILE_PATH).run();
    }
}
