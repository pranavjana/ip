# Pico User Guide

Pico is a task management chatbot that helps you track your todos, deadlines, and events via a command-line interface.

## Quick Start

1. Ensure you have Java 17 installed.
2. Download the latest `Pico.jar` from the [Releases](https://github.com/pranavjana/ip/releases) page.
3. Run `java -jar Pico.jar` in your terminal.

## Features

### Adding a todo: `todo`

Adds a task with no date attached.

Format: `todo DESCRIPTION`

Example: `todo borrow book`

```
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
```

### Adding a deadline: `deadline`

Adds a task with a due date.

Format: `deadline DESCRIPTION /by DATE`

Example: `deadline return book /by Sunday`

```
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
```

### Adding an event: `event`

Adds a task with a start and end time.

Format: `event DESCRIPTION /from START /to END`

Example: `event project meeting /from Mon 2pm /to 4pm`

```
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
```

### Listing all tasks: `list`

Displays all tasks in your list.

Format: `list`

```
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

### Marking a task as done: `mark`

Marks the specified task as completed.

Format: `mark TASK_NUMBER`

Example: `mark 1`

```
Nice! I've marked this task as done:
 [T][X] borrow book
```

### Unmarking a task: `unmark`

Marks the specified task as not done.

Format: `unmark TASK_NUMBER`

Example: `unmark 1`

```
OK, I've marked this task as not done yet:
 [T][ ] borrow book
```

### Deleting a task: `delete`

Removes the specified task from the list.

Format: `delete TASK_NUMBER`

Example: `delete 2`

```
Noted. I've removed this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
```

### Finding tasks: `find`

Searches for tasks containing the given keyword (case-insensitive).

Format: `find KEYWORD`

Example: `find book`

```
Here are the matching tasks in your list:
1.[T][ ] borrow book
```

### Exiting the program: `bye`

Exits the chatbot.

Format: `bye`

## Data Storage

Tasks are automatically saved to `./data/pico.txt` whenever the list changes. They are loaded automatically on startup.

## Command Summary

| Command | Format |
|---------|--------|
| Todo | `todo DESCRIPTION` |
| Deadline | `deadline DESCRIPTION /by DATE` |
| Event | `event DESCRIPTION /from START /to END` |
| List | `list` |
| Mark | `mark TASK_NUMBER` |
| Unmark | `unmark TASK_NUMBER` |
| Delete | `delete TASK_NUMBER` |
| Find | `find KEYWORD` |
| Exit | `bye` |
