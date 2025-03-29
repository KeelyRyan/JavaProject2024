package com.ticket;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoggerUtil {

    private static final Path userFile = Paths.get("logs", "users.txt");

    static {
        try {
            if (!Files.exists(userFile.getParent())) {
                Files.createDirectory(userFile.getParent());
            }
            if (!Files.exists(userFile)) {
                Files.createFile(userFile);
            }
        } catch (IOException e) {
            System.err.println("Error initializing user file: " + e.getMessage());
        }
    }

    public static void saveUsers(List<User> users) {
        List<String> userLines = users.stream()
                .map(user -> user.getUserId() + "," + user.getUserName() + "," + user.getEmailAddress() + "," + user.getPassword())
                .collect(Collectors.toList());
        try {
            Files.write(userFile, userLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("User data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
        }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try {
            if (Files.exists(userFile)) {
                List<String> lines = Files.readAllLines(userFile);
                for (String line : lines) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        String userId = parts[0];
                        String username = parts[1];
                        String email = parts[2];
                        String password = parts[3];

                        User user = switch (userId.substring(0, 3)) {
                            case "ADM" -> new Admin(email, userId, password);
                            case "ORG" -> new Organiser(username, email, password);
                            case "ATT" -> new Attendee(username, email, password);
                            default -> null;
                        };
                        if (user != null) {
                            users.add(user);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading user data: " + e.getMessage());
        }
        return users;
    }
}
