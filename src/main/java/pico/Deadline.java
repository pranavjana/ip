package pico;

/**
 * Represents a task with a deadline date.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Creates a new deadline task with the given description and due date.
     *
     * @param description The description of the deadline task.
     * @param by The due date or time for this task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
