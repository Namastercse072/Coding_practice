import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

class EventException extends Exception {
    public EventException(String message) {
        super(message);
    }
}

public  class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        String dateTimeStr = scanner.nextLine();
        int duration = Integer.parseInt(scanner.nextLine());
        
        try {
            // guard against empty name
            if (name == null || name.trim().isEmpty()) {
                throw new EventException("Event name cannot be empty");
            }
            // parse date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");  
            LocalDateTime dateTime;

            dateTime = LocalDateTime.parse(dateTimeStr, formatter);
            // guard against past date
            if (dateTime.isBefore(LocalDateTime.now())) {
                throw new EventException("Event cannot be in the past");
            }
            // guard against negative duration
            if (duration <= 0) {
                throw new EventException("Duration must be positive");
            }
            // if all checks pass, print event details
            System.out.print("Valid event: " + name);
            System.out.println(" at " + dateTime.format(formatter));

        } catch (EventException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (DateTimeParseException e) {
           System.out.println("Error: Invalid date format");
        }
    }
  }