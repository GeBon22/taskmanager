import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int ADD_TASK_OPTION = 1;
    private static final int VIEW_TASKS_OPTION = 2;
    private static final int MARK_AS_COMPLETED_OPTION = 3;
    private static final int UPDATE_TASK_OPTION = 4;
    private static final int DELETE_TASK_OPTION = 5;
    private static final int SEARCH_TASKS_OPTION = 6;
    private static final int SORT_BY_PRIORITY_OPTION = 7;
    private static final int SORT_BY_DUE_DATE_OPTION = 8;
    private static final int FILTER_DUE_TODAY_OPTION = 9;
    private static final int EXIT_OPTION = 10;

    private static final String[] MENU_OPTIONS = {
            "Add Task",
            "View Tasks",
            "Mark as Completed",
            "Update Task",
            "Delete Task",
            "Search Tasks",
            "Sort Tasks by Priority",
            "Sort Tasks by Due Date",
            "Filter Tasks Due Today",
            "Exit"
    };

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.loadTasksFromFile("tasks.csv");
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            displayMenu();
            System.out.print("Choose an option: ");
            input = scanner.nextLine();

            if (input.equals("Sort Tasks by Priority")) {
                manager.sortTasksByPriority();
            } else if (input.equals("Filter Tasks Due Today")) {
                List<Task> dueTodayTasks = manager.filterTasksByDueDate(LocalDate.now());
                dueTodayTasks.forEach(task -> System.out.println(task.getTitle()));
            } else if (input.equals(String.valueOf(ADD_TASK_OPTION))) {
                handleAddTask(scanner, manager);
            } else if (input.equals(String.valueOf(VIEW_TASKS_OPTION))) {
                System.out.println("All Tasks:");
                manager.listAllTasks();
            } else if (input.equals(String.valueOf(MARK_AS_COMPLETED_OPTION))) {
                handleMarkAsCompleted(scanner, manager);
            } else if (input.equals(String.valueOf(UPDATE_TASK_OPTION))) {
                handleUpdateTask(scanner, manager);
            } else if (input.equals(String.valueOf(DELETE_TASK_OPTION))) {
                handleDeleteTask(scanner, manager);
            } else if (input.equals(String.valueOf(SEARCH_TASKS_OPTION))) {
                handleSearchTasks(scanner, manager);
            } else if (input.equals(String.valueOf(SORT_BY_PRIORITY_OPTION))) {
                manager.sortTasksByPriority();
                System.out.println("Tasks sorted by priority.");
            } else if (input.equals(String.valueOf(SORT_BY_DUE_DATE_OPTION))) {
                manager.sortTasksByDueDate();
                System.out.println("Tasks sorted by due date.");
            } else if (input.equals(String.valueOf(FILTER_DUE_TODAY_OPTION))) {
                handleFilterDueToday(manager);
            } else if (input.equals(String.valueOf(EXIT_OPTION))) {
                System.out.println("Exiting the application. Goodbye!");
            } else {
                System.out.println("Invalid option, please try again.");
            }
        } while (!input.equals(String.valueOf(EXIT_OPTION)));

        manager.saveTasksToFile("tasks.csv");
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\nTask Management System");
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            System.out.println((i + 1) + ". " + MENU_OPTIONS[i]);
        }
    }

    private static void handleAddTask(Scanner scanner, TaskManager manager) {
        System.out.print("Enter Task Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Task Description: ");
        String description = scanner.nextLine();

        LocalDate dueDate = null;
        boolean validDate = false;
        while (!validDate) {
            System.out.print("Enter Due Date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            try {
                dueDate = LocalDate.parse(dateInput);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        String priority = null;
        boolean validPriority = false;
        while (!validPriority) {
            System.out.print("Enter Task Priority (Low, Medium, High): ");
            priority = scanner.nextLine();
            if (priority.equals("Low") || priority.equals("Medium") || priority.equals("High")) {
                validPriority = true;
            } else {
                System.out.println("Invalid priority! Please use Low, Medium, or High.");
            }
        }

        manager.addTask(title, description, priority, dueDate);
        System.out.println("Task added successfully!");
    }

    private static void handleMarkAsCompleted(Scanner scanner, TaskManager manager) {
        System.out.println("Select Task to mark as completed");
        manager.displayTasks();

        boolean validInput = false;
        while (!validInput) {
            try {
                int selectedTask = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (selectedTask >= 1 && selectedTask <= manager.getTaskCount()) {
                    manager.markTaskAsCompleted(selectedTask - 1);
                    System.out.println("Task marked as completed!");
                    validInput = true;
                } else {
                    System.out.println("Invalid task number. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void handleUpdateTask(Scanner scanner, TaskManager manager) {
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
    }

    private static void handleDeleteTask(Scanner scanner, TaskManager manager) {
        System.out.print("Enter Task Index to Delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        manager.deleteTask(index);
        System.out.println("Task deleted successfully!");
    }

    private static void handleSearchTasks(Scanner scanner, TaskManager manager) {
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine();
        List<Task> foundTasks = manager.searchTask(keyword);
        if (foundTasks.isEmpty()) {
            System.out.println("No tasks found with that keyword.");
        } else {
            System.out.println("Found Tasks:");
            for (Task task : foundTasks) {
                System.out.println(" - " + task.getTitle());
            }
        }
    }

    private static void handleFilterDueToday(TaskManager manager) {
        List<Task> dueTodayTasks = manager.filterTasksByDueDate(LocalDate.now());
        if (dueTodayTasks.isEmpty()) {
            System.out.println("No tasks are due today!");
        } else {
            System.out.println("Tasks due today:");
            for (Task task : dueTodayTasks) {
                System.out.println(" - " + task.getTitle());
            }
        }
    }
}