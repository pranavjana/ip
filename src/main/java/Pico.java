import java.util.Scanner;

public class Pico {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        Scanner scanner = new Scanner(System.in);

        System.out.println(line);
        System.out.println(" Greetings! I'm Pico, your extraterrestrial bot!");
        System.out.println(" What can I do for you, earthling?");
        System.out.println(line);

        System.out.print("                              You: ");
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println("Pico: " + input);
            System.out.println();
            System.out.print("                              You: ");
            input = scanner.nextLine();
        }

        System.out.println(line);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(line);

        scanner.close();
    }
}
