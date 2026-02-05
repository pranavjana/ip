import java.util.Scanner;

public class Pico {
    private static final String LINE = "____________________________________________________________";
    private static final String USER_PROMPT = "                              You: ";

    public static void main(String[] args) {
        TaskList taskList = new TaskList();
        Scanner scanner = new Scanner(System.in);

        printWelcome();

        String input = readInput(scanner);
        while (!input.equals("bye")) {
            System.out.println(LINE);
            handleCommand(input, taskList);
            System.out.println(LINE);
            input = readInput(scanner);
        }

        printGoodbye();

        scanner.close();
    }

    private static void printWelcome() {
        System.out.println(LINE);
        System.out.println(" Greetings! I'm Pico, your extraterrestrial bot!");
        System.out.println(" What can I do for you, earthling?");
        System.out.println(LINE);
    }

    private static void printGoodbye() {
        System.out.println(LINE);
        System.out.println(" Goodbye, earthling! Safe travels through the cosmos!");
        System.out.println(LINE);
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

        if (taskList.addTask(input)) {
            System.out.println(" added: " + input);
        } else {
            System.out.println(" Sorry, I can only store up to 100 tasks.");
        }
    }

    private static void handleList(TaskList taskList) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 1; i <= taskList.getTaskCount(); i++) {
            System.out.println(" " + i + ". " + taskList.getTaskByNumber(i));
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
        String commandArgs = input.length() > commandWord.length()
                ? input.substring(commandWord.length()).trim()
                : "";
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
