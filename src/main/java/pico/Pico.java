package pico;

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
            try {
                handleCommand(input, taskList);
            } catch (PicoException e) {
                System.out.println(" " + e.getMessage());
            }
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

    private static void handleCommand(String input, TaskList taskList) throws PicoException {
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

        throw new PicoException("Beep boop! That command doesn't exist on my planet.");
    }

    private static void handleTodo(String input, TaskList taskList) throws PicoException {
        String description = getCommandArgs(input, "todo");
        if (description.isEmpty()) {
            throw new PicoException("A todo with no description? Even aliens label their tasks, earthling!");
        }
        Task task = new Todo(description);
        addTaskAndPrint(task, taskList);
    }

    private static void handleDeadline(String input, TaskList taskList) throws PicoException {
        String args = getCommandArgs(input, "deadline");
        int byIndex = args.indexOf(DEADLINE_DELIMITER);
        if (byIndex == -1) {
            throw new PicoException("Galactic format error! Use: deadline <description> /by <date>");
        }
        String description = args.substring(0, byIndex).trim();
        String by = args.substring(byIndex + DEADLINE_DELIMITER.length()).trim();
        if (description.isEmpty()) {
            throw new PicoException("A deadline with no description? My sensors can't compute that!");
        }
        if (by.isEmpty()) {
            throw new PicoException("When is this due, earthling? The /by field cannot be empty.");
        }
        Task task = new Deadline(description, by);
        addTaskAndPrint(task, taskList);
    }

    private static void handleEvent(String input, TaskList taskList) throws PicoException {
        String args = getCommandArgs(input, "event");
        int fromIndex = args.indexOf(EVENT_FROM_DELIMITER);
        int toIndex = args.indexOf(EVENT_TO_DELIMITER);
        if (fromIndex == -1 || toIndex == -1) {
            throw new PicoException("Galactic format error! Use: event <description> /from <start> /to <end>");
        }
        String description = args.substring(0, fromIndex).trim();
        String from = args.substring(fromIndex + EVENT_FROM_DELIMITER.length(), toIndex).trim();
        String to = args.substring(toIndex + EVENT_TO_DELIMITER.length()).trim();
        if (description.isEmpty()) {
            throw new PicoException("An event with no description? My sensors can't compute that!");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new PicoException("When does this event start and end? Even space-time has boundaries!");
        }
        Task task = new Event(description, from, to);
        addTaskAndPrint(task, taskList);
    }

    private static void addTaskAndPrint(Task task, TaskList taskList) throws PicoException {
        if (!taskList.addTask(task)) {
            throw new PicoException("My memory banks are full! I can only store up to 100 tasks.");
        }
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskList.getTaskCount() + " tasks in the list.");
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

    private static void handleMark(String input, TaskList taskList) throws PicoException {
        int taskNumber = parseTaskNumber(input, "mark");
        Task task = taskList.markTask(taskNumber);
        if (task == null) {
            throw new PicoException("Task " + taskNumber + " doesn't exist in my star chart!");
        }
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    private static void handleUnmark(String input, TaskList taskList) throws PicoException {
        int taskNumber = parseTaskNumber(input, "unmark");
        Task task = taskList.unmarkTask(taskNumber);
        if (task == null) {
            throw new PicoException("Task " + taskNumber + " doesn't exist in my star chart!");
        }
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    private static boolean isCommandWord(String input, String commandWord) {
        return input.equals(commandWord) || input.startsWith(commandWord + " ");
    }

    private static int parseTaskNumber(String input, String commandWord) throws PicoException {
        String commandArgs = getCommandArgs(input, commandWord);
        if (commandArgs.isEmpty()) {
            throw new PicoException("Which task, earthling? Please provide a task number.");
        }

        try {
            return Integer.parseInt(commandArgs);
        } catch (NumberFormatException e) {
            throw new PicoException("'" + commandArgs + "' is not a number in any galaxy I know!");
        }
    }
}
