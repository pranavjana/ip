package pico;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Storage {
    private static final String FILE_DELIMITER = " \\| ";
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public void save(TaskList taskList) throws PicoException {
        try {
            Files.createDirectories(filePath.getParent());
            FileWriter writer = new FileWriter(filePath.toFile());
            for (int i = 1; i <= taskList.getTaskCount(); i++) {
                writer.write(taskList.getTaskByNumber(i).toFileString() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new PicoException("Failed to save tasks: " + e.getMessage());
        }
    }

    public TaskList load() throws PicoException {
        TaskList taskList = new TaskList();
        File file = filePath.toFile();
        if (!file.exists()) {
            return taskList;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                Task task = parseLine(line);
                if (task != null) {
                    taskList.addTask(task);
                }
            }
        } catch (IOException e) {
            throw new PicoException("Failed to load tasks: " + e.getMessage());
        }
        return taskList;
    }

    private Task parseLine(String line) {
        String[] parts = line.split(FILE_DELIMITER);
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            task = new Deadline(description, parts[3].trim());
            break;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            task = new Event(description, parts[3].trim(), parts[4].trim());
            break;
        default:
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
