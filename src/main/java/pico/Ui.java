package pico;

import java.util.Scanner;

public class Ui {
    private static final String DIVIDER_LINE = "____________________________________________________________";
    private static final String USER_PROMPT = "                              You: ";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.print(USER_PROMPT);
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println(DIVIDER_LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println(" Greetings! I'm Pico, your extraterrestrial bot!");
        System.out.println(" What can I do for you, earthling?");
        showLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println(" Goodbye, earthling! Safe travels through the cosmos!");
        showLine();
    }

    public void showError(String message) {
        System.out.println(" " + message);
    }

    public void showLoadingError() {
        System.out.println(" Could not load saved tasks. Starting with an empty list.");
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskMarked(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showTaskList(TaskList taskList) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 1; i <= taskList.getTaskCount(); i++) {
            System.out.println(" " + i + "." + taskList.getTaskByNumber(i));
        }
    }

    public void showMatchingTasks(TaskList taskList) {
        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 1; i <= taskList.getTaskCount(); i++) {
            System.out.println(" " + i + "." + taskList.getTaskByNumber(i));
        }
    }

    public void close() {
        scanner.close();
    }
}
