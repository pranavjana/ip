package pico;

public class Parser {
    private static final String DEADLINE_DELIMITER = " /by ";
    private static final String EVENT_FROM_DELIMITER = " /from ";
    private static final String EVENT_TO_DELIMITER = " /to ";

    public static String getCommandWord(String input) {
        return input.split(" ", 2)[0];
    }

    public static String getCommandArgs(String input, String commandWord) {
        if (input.length() <= commandWord.length()) {
            return "";
        }
        return input.substring(commandWord.length()).trim();
    }

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

    public static Todo parseTodo(String input) throws PicoException {
        String description = getCommandArgs(input, "todo");
        if (description.isEmpty()) {
            throw new PicoException("A todo with no description? Even aliens label their tasks, earthling!");
        }
        return new Todo(description);
    }

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
