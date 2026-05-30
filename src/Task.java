import java.time.LocalDate;

public class Task {
    private String title;
    private String description;
    private String priority; // Low, Medium, High
    private LocalDate dueDate;
    private boolean isCompleted;

    public Task(String title, String description, String priority, LocalDate dueDate, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s",
                title,
                description,
                priority,
                dueDate.toString(),
                isCompleted);
    }

    public static Task fromCsv(String csv) {
        String[] parts = csv.split(",");
        if (parts.length != 5) {
            return null;
        }
        return new Task(
                parts[0],
                parts[1],
                parts[2],
                LocalDate.parse(parts[3]),
                Boolean.parseBoolean(parts[4])
        );
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] %s - %s (Due: %s, Priority: %s)",
                isCompleted ? "X" : " ",
                title,
                description,
                dueDate,
                priority
        );
    }
}