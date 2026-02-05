import java.util.Scanner;

public class Pico {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String[] tasks = new String[100];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println(line);
        System.out.println(" Greetings! I'm Pico, your extraterrestrial bot!");
        System.out.println(" What can I do for you, earthling?");
        System.out.println(line);

        System.out.print("                              You: ");
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(line);
            if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
            } else if (taskCount < tasks.length) {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println(" added: " + input);
            } else {
                System.out.println(" Sorry, I can only store up to 100 tasks.");
            }
            System.out.println(line);
            System.out.print("                              You: ");
            input = scanner.nextLine();
        }

        System.out.println(line);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(line);

        scanner.close();
    }
}
