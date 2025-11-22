import java.util.ArrayList;
import java.time.LocalDate;

public class TaskManager {
    private ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(String title, String description, String priority, LocalDate dueDate) {
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
}
