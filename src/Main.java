import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            displayMenu();
            System.out.print("Choose an option: ");
            input = scanner.nextLine();

            // Add Task
            if (input.equals("1")) {
                System.out.print("Enter Task Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter Task Description: ");
                String description = scanner.nextLine();
                Task newTask = new Task(title, description);
                manager.addTask(newTask);
                System.out.println("Task added successfully!");

                // View Tasks
            } else if (input.equals("2")) {
                System.out.println("All Tasks:");
                manager.listAllTasks();

                // Update Task
            } else if (input.equals("3")) {
                System.out.print("Enter Task Index to Update: ");
                int index = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter New Title: ");
                String newTitle = scanner.nextLine();
                System.out.print("Enter New Description: ");
                String newDescription = scanner.nextLine();
                System.out.print("Is it completed? (true/false): ");
                boolean completed = Boolean.parseBoolean(scanner.nextLine());
                manager.updateTask(index, newTitle, newDescription, completed);
                System.out.println("Task updated successfully!");

                // Delete Task
            } else if (input.equals("4")) {
                System.out.print("Enter Task Index to Delete: ");
                int index = Integer.parseInt(scanner.nextLine());
                manager.deleteTask(index);

                // Search Tasks
            } else if (input.equals("5")) {
                System.out.print("Enter keyword to search: ");
                String keyword = scanner.nextLine();
                ArrayList<Task> foundTasks = manager.searchTask(keyword);
                if (foundTasks.isEmpty()) {
                    System.out.println("No tasks found with that keyword.");
                } else {
                    System.out.println("Found Tasks:");
                    for (Task task : foundTasks) {
                        System.out.println(" - " + task.getTitle());
                    }
                }

                // Exit
            } else if (input.equals("6")) {
                System.out.println("Exiting the application. Goodbye!");
            } else {
                System.out.println("Invalid option, please try again.");
            }
        } while (!input.equals("6"));

        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("\nTask Management System");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Update Task");
        System.out.println("4. Delete Task");
        System.out.println("5. Search Tasks");
        System.out.println("6. Exit");
    }
}
