import java.util.Scanner;

public class Pico {
    private static final String DIVIDER_LINE = "____________________________________________________________";
    private static final String USER_PROMPT = "                              You: ";
    private static final String DEADLINE_DELIMITER = " /by ";
    private static final String EVENT_FROM_DELIMITER = " /from ";
    private static final String EVENT_TO_DELIMITER = " /to ";

    public static void main(String[] args) {
        TaskList taskList = new TaskList();
        Scanner scanner = new Scanner(System.in);

        printWelcome();

        String input = readInput(scanner);
        while (!input.equals("bye")) {
            System.out.println(DIVIDER_LINE);
            handleCommand(input, taskList);
            System.out.println(DIVIDER_LINE);
            input = readInput(scanner);
        }

        printGoodbye();

        scanner.close();
    }

    private static void printWelcome() {
        System.out.println(DIVIDER_LINE);
        System.out.println(" Greetings! I'm Pico, your extraterrestrial bot!");
        System.out.println(" What can I do for you, earthling?");
        System.out.println(DIVIDER_LINE);
    }

    private static void printGoodbye() {
        System.out.println(DIVIDER_LINE);
        System.out.println(" Goodbye, earthling! Safe travels through the cosmos!");
        System.out.println(DIVIDER_LINE);
    }

    private static String readInput(Scanner scanner) {
        System.out.print(USER_PROMPT);
        return scanner.nextLine();
    }

    private static void handleCommand(String input, TaskList taskList) {
        if (input.equals("list")) {
            handleList(taskList);
            return;
        }

        if (isCommandWord(input, "mark")) {
            handleMark(input, taskList);
            return;
        }

        if (isCommandWord(input, "unmark")) {
            handleUnmark(input, taskList);
            return;
        }

        if (isCommandWord(input, "todo")) {
            handleTodo(input, taskList);
            return;
        }

        if (isCommandWord(input, "deadline")) {
            handleDeadline(input, taskList);
            return;
        }

        if (isCommandWord(input, "event")) {
            handleEvent(input, taskList);
            return;
        }

        System.out.println(" Sorry, I don't understand that command.");
    }

    private static void handleTodo(String input, TaskList taskList) {
        String description = getCommandArgs(input, "todo");
        if (description.isEmpty()) {
            System.out.println(" Sorry, the description of a todo cannot be empty.");
            return;
        }
        Task task = new Todo(description);
        addTaskAndPrint(task, taskList);
    }

    private static void handleDeadline(String input, TaskList taskList) {
        String args = getCommandArgs(input, "deadline");
        int byIndex = args.indexOf(DEADLINE_DELIMITER);
        if (byIndex == -1) {
            System.out.println(" Sorry, please use the format: deadline <description> /by <date>");
            return;
        }
        String description = args.substring(0, byIndex).trim();
        String by = args.substring(byIndex + DEADLINE_DELIMITER.length()).trim();
        if (description.isEmpty() || by.isEmpty()) {
            System.out.println(" Sorry, please use the format: deadline <description> /by <date>");
            return;
        }
        Task task = new Deadline(description, by);
        addTaskAndPrint(task, taskList);
    }

    private static void handleEvent(String input, TaskList taskList) {
        String args = getCommandArgs(input, "event");
        int fromIndex = args.indexOf(EVENT_FROM_DELIMITER);
        int toIndex = args.indexOf(EVENT_TO_DELIMITER);
        if (fromIndex == -1 || toIndex == -1) {
            System.out.println(" Sorry, please use the format: event <description> /from <start> /to <end>");
            return;
        }
        String description = args.substring(0, fromIndex).trim();
        String from = args.substring(fromIndex + EVENT_FROM_DELIMITER.length(), toIndex).trim();
        String to = args.substring(toIndex + EVENT_TO_DELIMITER.length()).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            System.out.println(" Sorry, please use the format: event <description> /from <start> /to <end>");
            return;
        }
        Task task = new Event(description, from, to);
        addTaskAndPrint(task, taskList);
    }

    private static void addTaskAndPrint(Task task, TaskList taskList) {
        if (taskList.addTask(task)) {
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + task);
            System.out.println(" Now you have " + taskList.getTaskCount() + " tasks in the list.");
        } else {
            System.out.println(" Sorry, I can only store up to 100 tasks.");
        }
    }

    private static String getCommandArgs(String input, String commandWord) {
        if (input.length() <= commandWord.length()) {
            return "";
        }
        return input.substring(commandWord.length()).trim();
    }

    private static void handleList(TaskList taskList) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 1; i <= taskList.getTaskCount(); i++) {
            System.out.println(" " + i + "." + taskList.getTaskByNumber(i));
        }
    }

    private static void handleMark(String input, TaskList taskList) {
        Integer taskNumber = parseTaskNumber(input, "mark");
        Task task = taskNumber == null ? null : taskList.markTask(taskNumber);
        if (task == null) {
            System.out.println(" Sorry, please provide a valid task number.");
            return;
        }
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    private static void handleUnmark(String input, TaskList taskList) {
        Integer taskNumber = parseTaskNumber(input, "unmark");
        Task task = taskNumber == null ? null : taskList.unmarkTask(taskNumber);
        if (task == null) {
            System.out.println(" Sorry, please provide a valid task number.");
            return;
        }
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    private static boolean isCommandWord(String input, String commandWord) {
        return input.equals(commandWord) || input.startsWith(commandWord + " ");
    }

    private static Integer parseTaskNumber(String input, String commandWord) {
        String commandArgs = getCommandArgs(input, commandWord);
        if (commandArgs.isEmpty()) {
            return null;
        }

        try {
            return Integer.parseInt(commandArgs);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
