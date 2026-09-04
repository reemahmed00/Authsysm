package auth;

import java.util.Map;

public class AuthService {

    private final Map<String, User> userDatabase;
    private final UserRepository repository;
    private final PasswordHasher hasher;

    public AuthService() {
        this.repository = new UserRepository();
        this.hasher = new PasswordHasher();
        // Load existing users from users.txt into memory on startup
        this.userDatabase = repository.loadUsers();
    }

    public boolean register(String username, String rawPassword) {
        if (username == null || username.trim().isEmpty() || rawPassword == null || rawPassword.trim().isEmpty()) {
            System.out.println("❌ Username or password cannot be empty.");
            return false;
        }

        if (userDatabase.containsKey(username)) {
            System.out.println("❌ Registration failed: Username already exists.");
            return false;
        }

        // 1. Hash password with BCrypt
        String hashedPassword = hasher.hash(rawPassword);

        // 2. Save user in memory and append to users.txt
        User newUser = new User(username, hashedPassword);
        userDatabase.put(username, newUser);
        repository.saveUser(newUser);

        System.out.println("✅ User '" + username + "' registered successfully!");
        return true;
    }

    public boolean login(String username, String rawPassword) {
        User user = userDatabase.get(username);

        if (user == null) {
            System.out.println("❌ Login failed: User not found.");
            return false;
        }

        // Verify password hash
        if (hasher.verify(rawPassword, user.getPasswordHash())) {
            System.out.println("🎉 Login successful! Welcome, " + username + ".");
            return true;
        } else {
            System.out.println("❌ Login failed: Incorrect password.");
            return false;
        }
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = userDatabase.get(username);

        // 1. Verify user exists
        if (user == null) {
            System.out.println("❌ User not found.");
            return false;
        }

        // 2. Verify current password
        if (!hasher.verify(oldPassword, user.getPasswordHash())) {
            System.out.println("❌ Password change failed: Incorrect current password.");
            return false;
        }

        // 3. Validate new password
        if (newPassword == null || newPassword.trim().isEmpty()) {
            System.out.println("❌ New password cannot be empty.");
            return false;
        }

        // 4. Hash new password & update User object
        String newHashedPassword = hasher.hash(newPassword);
        user.setPasswordHash(newHashedPassword);

        // 5. Persist updated user map to users.txt
        repository.saveAllUsers(userDatabase);

        System.out.println("✅ Password changed successfully for '" + username + "'!");
        return true;
    }
}