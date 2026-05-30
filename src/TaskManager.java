import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(String title, String description, String priority, LocalDate dueDate) {
        tasks.add(new Task(title, description, priority, dueDate, false));
    }

    public void markTaskAsCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).setCompleted(true);
        }
    }

    public void updateTask(int index, String newTitle, String newDescription, boolean completed) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.setTitle(newTitle);
            task.setDescription(newDescription);
            task.setCompleted(completed);
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public void listAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void displayTasks() {
        listAllTasks();
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public List<Task> searchTask(String keyword) {
        return tasks.stream()
                .filter(task -> task.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void sortTasksByPriority() {
        tasks.sort(Comparator.comparing(Task::getPriority));
    }

    public void sortTasksByDueDate() {
        tasks.sort(Comparator.comparing(Task::getDueDate));
    }

    public List<Task> filterTasksByDueDate(LocalDate dueDate) {
        return tasks.stream()
                .filter(task -> task.getDueDate().equals(dueDate))
                .collect(Collectors.toList());
    }

    public void saveTasksToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                writer.println(task.toCsv());
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public void loadTasksFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromCsv(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks from file: " + e.getMessage());
        }
    }
}