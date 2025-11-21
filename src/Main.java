import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Define constants for menu options
    private static final int ADD_TASK_OPTION = 1;
    private static final int VIEW_TASKS_OPTION = 2;
    private static final int UPDATE_TASK_OPTION = 3;
    private static final int DELETE_TASK_OPTION = 4;
    private static final int SEARCH_TASKS_OPTION = 5;
    private static final int EXIT_OPTION = 6;

    private static final String[] MENU_OPTIONS = {
            "Add Task",
            "View Tasks",
            "Update Task",
            "Delete Task",
            "Search Tasks",
            "Exit"
    };

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            displayMenu();
            System.out.print("Choose an option: ");
            input = scanner.nextLine();

            // Add Task
            if (input.equals(String.valueOf(ADD_TASK_OPTION))) {
                System.out.print("Enter Task Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter Task Description: ");
                String description = scanner.nextLine();
                Task newTask = new Task(title, description);
                manager.addTask(newTask);
                System.out.println("Task added successfully!");

                // View Tasks
            } else if (input.equals(String.valueOf(VIEW_TASKS_OPTION))) {
                System.out.println("All Tasks:");
                manager.listAllTasks();

                // Update Task
            } else if (input.equals(String.valueOf(UPDATE_TASK_OPTION))) {
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
            } else if (input.equals(String.valueOf(DELETE_TASK_OPTION))) {
                System.out.print("Enter Task Index to Delete: ");
                int index = Integer.parseInt(scanner.nextLine());
                manager.deleteTask(index);

                // Search Tasks
            } else if (input.equals(String.valueOf(SEARCH_TASKS_OPTION))) {
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
            } else if (input.equals(String.valueOf(EXIT_OPTION))) {
                System.out.println("Exiting the application. Goodbye!");
            } else {
                System.out.println("Invalid option, please try again.");
            }
        } while (!input.equals(String.valueOf(EXIT_OPTION)));

        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("\nTask Management System");
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            System.out.println((i + 1) + ". " + MENU_OPTIONS[i]);
        }
    }
}
