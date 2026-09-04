package auth;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    // Hashes a raw password using BCrypt
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verifies a raw password against a stored BCrypt hash
    public boolean verify(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }

    // Quick main method to test
    public static void main(String[] args) {
        PasswordHasher hasher = new PasswordHasher();

        String rawPassword = "hello123";

        // Hash the password
        String hashedPassword = hasher.hash(rawPassword);
        System.out.println("BCrypt Hash: " + hashedPassword);

        // Test correct password
        boolean isCorrect = hasher.verify("hello123", hashedPassword);
        System.out.println("Verify 'hello123': " + isCorrect); // Should print true

        // Test wrong password
        boolean isWrong = hasher.verify("wrong123", hashedPassword);
        System.out.println("Verify 'wrong123': " + isWrong); // Should print false
    }
}