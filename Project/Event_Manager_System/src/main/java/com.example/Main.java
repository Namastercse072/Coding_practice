import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final HashSet<String> events = new HashSet<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Add event method
    public static String addEvent(String name, String dateTime, int durationMinutes) {
        if (name == null || name.isBlank()) {
            return "Invalid event name";
        }

        LocalDateTime eventDateTime;
        try {
            eventDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            return "Invalid date format";
        }

        if (durationMinutes <= 0) {
            return "Invalid duration";
        }

        String eventKey = name + "|" + dateTime + "|" + durationMinutes;
        if (!events.add(eventKey)) {
            return "Error: Event already exists";
        }

        return "Event added: " + name + " at " + dateTime;
    }

    // Helper: find event by name
    private static String findEvent(String name) {
        for (String e : events) {
            String[] parts = e.split("\\|");
            if (parts[0].equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

    // Command: between
    private static String timeBetween(String name1, String name2) {
        String e1 = findEvent(name1);
        String e2 = findEvent(name2);

        if (e1 == null || e2 == null) {
            return "Error: Event not found";
        }

        String[] p1 = e1.split("\\|");
        String[] p2 = e2.split("\\|");
        LocalDateTime d1 = LocalDateTime.parse(p1[1], formatter);
        LocalDateTime d2 = LocalDateTime.parse(p2[1], formatter);

        Duration diff = Duration.between(d1, d2).abs();
        long hours = diff.toHours();
        long minutes = diff.toMinutes() % 60;

        return "Time between events: " + hours + " hours and " + minutes + " minutes";
    }

    // Command: overlap
    private static String overlap(String dateStr) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            return "Error: Invalid date format";
        }

        List<String> sameDayEvents = new ArrayList<>();
        for (String e : events) {
            String[] parts = e.split("\\|");
            LocalDateTime start = LocalDateTime.parse(parts[1], formatter);
            int duration = Integer.parseInt(parts[2]);
            LocalDateTime end = start.plusMinutes(duration);

            if (start.toLocalDate().equals(date) || end.toLocalDate().equals(date)) {
                sameDayEvents.add(parts[0] + "|" + parts[1] + "|" + parts[2]);
            }
        }

        // Check overlap pairs
        for (int i = 0; i < sameDayEvents.size(); i++) {
            for (int j = i + 1; j < sameDayEvents.size(); j++) {
                String[] e1 = sameDayEvents.get(i).split("\\|");
                String[] e2 = sameDayEvents.get(j).split("\\|");

                LocalDateTime s1 = LocalDateTime.parse(e1[1], formatter);
                LocalDateTime e1End = s1.plusMinutes(Integer.parseInt(e1[2]));
                LocalDateTime s2 = LocalDateTime.parse(e2[1], formatter);
                LocalDateTime e2End = s2.plusMinutes(Integer.parseInt(e2[2]));

                if (!(e1End.isBefore(s2) || e2End.isBefore(s1))) {
                    return "Overlapping events: " + e1[0] + " and " + e2[0];
                }
            }
        }

        return "No overlapping events";
    }

    // Command: next
    private static String nextEvent() {
        if (events.isEmpty()) return "No upcoming events";

        LocalDateTime now = LocalDateTime.now();
        String nextEvent = null;
        LocalDateTime nextTime = null;

        for (String e : events) {
            String[] parts = e.split("\\|");
            LocalDateTime start = LocalDateTime.parse(parts[1], formatter);

            if (start.isAfter(now)) {
                if (nextTime == null || start.isBefore(nextTime)) {
                    nextTime = start;
                    nextEvent = e;
                }
            }
        }

        if (nextEvent == null) return "No upcoming events";

        String[] parts = nextEvent.split("\\|");
        return "Next event: " + parts[0] + " at " + parts[1];
    }

    // Main interactive loop
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) break;

            // Command handling
            if (input.startsWith("between")) {
                String[] parts = input.split(" ");
                if (parts.length == 3) {
                    System.out.println(timeBetween(parts[1], parts[2]));
                } else {
                    System.out.println("Error: Event not found");
                }
                continue;
            }

            if (input.startsWith("overlap")) {
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    System.out.println(overlap(parts[1]));
                } else {
                    System.out.println("Error: Invalid date format");
                }
                continue;
            }

            if (input.equals("next")) {
                System.out.println(nextEvent());
                continue;
            }

            // Otherwise treat input as event creation
            String dateTime = scanner.nextLine();
            String durationStr = scanner.nextLine();

            int duration;
            try {
                duration = Integer.parseInt(durationStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid duration");
                continue;
            }

            String result = addEvent(input, dateTime, duration);
            System.out.println(result);
        }

        scanner.close();
    }
}
