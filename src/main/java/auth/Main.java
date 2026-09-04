package auth;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("========================================");
        System.out.println("   Welcome to Authentication System     ");
        System.out.println("========================================");

        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Change Password");
            System.out.println("4. Exit");
            System.out.print("> ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter new username: ");
                    String regUser = scanner.nextLine().trim();
                    System.out.print("Enter new password: ");
                    String regPass = scanner.nextLine().trim();
                    authService.register(regUser, regPass);
                    break;

                case "2":
                    System.out.print("Enter username: ");
                    String loginUser = scanner.nextLine().trim();
                    System.out.print("Enter password: ");
                    String loginPass = scanner.nextLine().trim();
                    authService.login(loginUser, loginPass);
                    break;

                case "3":
                    System.out.print("Enter username: ");
                    String changeUser = scanner.nextLine().trim();
                    System.out.print("Enter current password: ");
                    String oldPass = scanner.nextLine().trim();
                    System.out.print("Enter new password: ");
                    String newPass = scanner.nextLine().trim();
                    authService.changePassword(changeUser, oldPass, newPass);
                    break;

                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }

        scanner.close();
    }
}