package pico;

/**
 * Parses user input into commands and task objects.
 */
public class Parser {
    private static final String DEADLINE_DELIMITER = " /by ";
    private static final String EVENT_FROM_DELIMITER = " /from ";
    private static final String EVENT_TO_DELIMITER = " /to ";

    /**
     * Extracts the command word from the user input.
     *
     * @param input The full user input string.
     * @return The first word of the input, representing the command.
     */
    public static String getCommandWord(String input) {
        return input.split(" ", 2)[0];
    }

    /**
     * Extracts the arguments portion of the user input after the command word.
     *
     * @param input The full user input string.
     * @param commandWord The command word to strip from the input.
     * @return The arguments string, or an empty string if there are none.
     */
    public static String getCommandArgs(String input, String commandWord) {
        if (input.length() <= commandWord.length()) {
            return "";
        }
        return input.substring(commandWord.length()).trim();
    }

    /**
     * Parses a task number from the user input for commands that operate on a specific task.
     *
     * @param input The full user input string.
     * @param commandWord The command word to strip from the input.
     * @return The parsed task number.
     * @throws PicoException If no task number is provided or the argument is not a valid number.
     */
    public static int parseTaskNumber(String input, String commandWord) throws PicoException {
        String args = getCommandArgs(input, commandWord);
        if (args.isEmpty()) {
            throw new PicoException("Which task, earthling? Please provide a task number.");
        }
        try {
            return Integer.parseInt(args);
        } catch (NumberFormatException e) {
            throw new PicoException("'" + args + "' is not a number in any galaxy I know!");
        }
    }

    /**
     * Parses a todo task from the user input.
     *
     * @param input The full user input string containing the todo command.
     * @return A new Todo task parsed from the input.
     * @throws PicoException If the description is empty.
     */
    public static Todo parseTodo(String input) throws PicoException {
        String description = getCommandArgs(input, "todo");
        if (description.isEmpty()) {
            throw new PicoException("A todo with no description? Even aliens label their tasks, earthling!");
        }
        return new Todo(description);
    }

    /**
     * Parses a deadline task from the user input.
     *
     * @param input The full user input string containing the deadline command.
     * @return A new Deadline task parsed from the input.
     * @throws PicoException If the format is invalid or required fields are empty.
     */
    public static Deadline parseDeadline(String input) throws PicoException {
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
        return new Deadline(description, by);
    }

    /**
     * Parses an event task from the user input.
     *
     * @param input The full user input string containing the event command.
     * @return A new Event task parsed from the input.
     * @throws PicoException If the format is invalid or required fields are empty.
     */
    public static Event parseEvent(String input) throws PicoException {
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
        return new Event(description, from, to);
    }
}
