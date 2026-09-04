package auth;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final String filePath = "users.txt";

    // Load all users from users.txt into memory at startup
    public Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return users; // Return empty map if file doesn't exist yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String username = parts[0];
                    String passwordHash = parts[1];
                    users.put(username, new User(username, passwordHash));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }

        return users;
    }

    // Save a new user line into users.txt
    public void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.getUsername() + ":" + user.getPasswordHash());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving user to file: " + e.getMessage());
        }
    }

    // Overwrite users.txt with all current users in memory
    public void saveAllUsers(Map<String, User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (User user : users.values()) {
                writer.write(user.getUsername() + ":" + user.getPasswordHash());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error rewriting user file: " + e.getMessage());
        }
    }
}