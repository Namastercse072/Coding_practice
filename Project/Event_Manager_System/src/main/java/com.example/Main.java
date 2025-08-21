import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        String regex = "Team.*"; // default
        boolean patternEntered = false;

        // Collect events or pattern
        while (true) {
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("exit")) break;

            if (name.startsWith("pattern ")) {
                regex = name.substring(8).trim() + ".*";
                patternEntered = true;
                break;
            }

            String dateTimeStr = scanner.nextLine();
            if (dateTimeStr.equalsIgnoreCase("exit")) break;
            if (dateTimeStr.startsWith("pattern ")) {
                regex = dateTimeStr.substring(8).trim() + ".*";
                patternEntered = true;
                break;
            }

            String durationStr = scanner.nextLine();
            if (durationStr.equalsIgnoreCase("exit")) break;
            if (durationStr.startsWith("pattern ")) {
                regex = durationStr.substring(8).trim() + ".*";
                patternEntered = true;
                break;
            }

            int duration;
            try {
                duration = Integer.parseInt(durationStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid duration");
                continue;
            }

            try {
                if (name == null || name.trim().isEmpty()) {
                    throw new EventException("Event name cannot be empty");
                }
                LocalDateTime startDate = LocalDateTime.parse(dateTimeStr, formatter);
                if (startDate.isBefore(LocalDateTime.now())) {
                    throw new EventException("Event cannot be in the past");
                }
                if (duration <= 0) {
                    throw new EventException("Duration must be positive");
                }
                String event = name.trim() + "|" + dateTimeStr + "|" + duration;
                if (eventAdding.contains(event)) {
                    throw new EventException("Event already exists");
                } else {
                    eventAdding.add(event);
                    System.out.println("Event added: " + name + " at " + startDate.format(formatter));
                }
            } catch (EventException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format");
            }
        }
        scanner.close();

        Pattern pattern = Pattern.compile(regex);

        // After input, find and print matching events
        for (String event : eventAdding) {
            String[] parts = event.split("\\|");
            String name = parts[0];
            String dateTimeStr = parts[1];
            LocalDateTime startDate = LocalDateTime.parse(dateTimeStr, formatter);
            if (pattern.matcher(name).matches()) {
                System.out.println("Found: " + name + " at " + startDate.format(formatter));
            }
        }
    }
}