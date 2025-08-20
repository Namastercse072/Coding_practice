import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Scanner;

class EventException extends Exception {
    public EventException(String message) {
        super(message);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashSet<String> eventAdding = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        /*Method help
        check 
        user loop*/
        while (scanner.hasNextLine()) {
          
            // Name
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("exit")) break;

            // Date/time
            String dateTimeStr = scanner.nextLine();
            if (dateTimeStr.equalsIgnoreCase("exit")) break;

            // Duration
            String durationStr = scanner.nextLine();
            if (durationStr.equalsIgnoreCase("exit")) break;

            int duration;
            try {
                duration = Integer.parseInt(durationStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid duration");
                 continue; // restart loop
            }
            try {
                if (name == null || name.trim().isEmpty()) {
                    throw new EventException("Event name cannot be empty");
                }
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
                if (dateTime.isBefore(LocalDateTime.now())) {
                    throw new EventException("Event cannot be in the past");
                }
                if (duration <= 0) {
                    throw new EventException("Duration must be positive");
                }
                // if all checks pass, print event details
                /* Check event 
                add to hashset
                */
                String event = name.trim() + "|" + dateTimeStr  + "|" + duration;
                // System.out.println(event);
                if (eventAdding.contains(event)) {
                    throw new EventException("Event already exists");
                } else {
                    eventAdding.add(event);
                    System.out.print("Event added: " + name);
                    System.out.println(" at " + dateTime.format(formatter));
                }
            } catch (EventException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format");
            }
        }
        scanner.close();
    }
}