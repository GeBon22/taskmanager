import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class TaskManager {
    private ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    private static final Map<String, Integer> PRIORITY_MAP;

    static {
        PRIORITY_MAP = new HashMap<>();
        PRIORITY_MAP.put("High", 1);
        PRIORITY_MAP.put("Medium", 2);
        PRIORITY_MAP.put("Low", 3);
    }

    public void addTask(String title, String description, String priority, LocalDate dueDate) {
        if(!PRIORITY_MAP.containsKey(priority)) {
            System.out.println("Invalid priority! Please use Low, Medium or High.");
            return;
        }

        Task newTask = new Task(title, description, priority, dueDate);
        tasks.add(newTask);
    }

    public void displayTasks() {
        for (Task task : tasks) {
            System.out.println(task.getTitle());
        }
    }

    public void updateTask(int index, String newTitle, String newDescription, boolean completed) {
        if (index >= 0 && index < tasks.size()) {
            Task taskToUpdate = tasks.get(index);
            taskToUpdate.setTitle(newTitle);
            taskToUpdate.setDescription(newDescription);
            if(completed){
                taskToUpdate.markAsCompleted();
            } else {
                taskToUpdate.markAsIncomplete();
            }
        } else {
            System.out.println("Task index out of range");
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task removed successfully");
        } else {
            System.out.println("Task index out of range");
        }
    }

    public void displayCompletedTasks() {
        for (Task task: tasks) {
            if (task.isCompleted()) {
                System.out.println("Completed Tasks: " + task.getTitle());
            }
        }
    }

    public ArrayList<Task> searchTask(String keyword) {
        ArrayList<Task> foundTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (task.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }

    public void listAllTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println(i + ": " + task.getTitle() +
                    " | Priority: " + task.getPriority() +
                    " | Due Date: " + task.getDueDate() +
                    " | Completed: " + task.isCompleted());
        }
    }

    public void sortTasksByPriority() {
        tasks.sort(Comparator.comparingInt(a -> PRIORITY_MAP.get(a.getPriority())));
    }

    public void sortTasksByDueDate() {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task a, Task b) {
                return a.getDueDate().compareTo(b.getDueDate());
            }
        });
    }

    public List<Task> filterTasksByDueDate(LocalDate filterDate) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (task.getDueDate().isEqual(filterDate)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public List<Task> filterTasksDueSoon() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusWeeks(1);
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (task.getDueDate().isAfter(today) && task.getDueDate().isBefore(nextWeek)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public void saveTasksToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task: tasks) {
                String line = String.join(",", task.getTitle(), task.getDescription(),
                        task.getPriority(), task.getDueDate().toString(),
                        String.valueOf(task.isCompleted()));
                        writer.write(line);
                        writer.newLine();
            }
            System.out.println("Tasks saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public void loadTasksFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");
                String title = attributes[0];
                String description = attributes[1];
                String priority = attributes[2];
                LocalDate dueDate = LocalDate.parse(attributes[3]);
                boolean isCompleted = Boolean.parseBoolean(attributes[4]);

                Task task = new Task(title, description, priority, dueDate);
                if (isCompleted) task.markAsCompleted();
                tasks.add(task);
            }
            System.out.println("Tasks loaded successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while loading tasks: " + e.getMessage());
        }
    }

}
