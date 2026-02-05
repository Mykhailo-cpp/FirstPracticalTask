package org.example;

import java.util.Scanner;

public class Main {

    public static void displayMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("VIGENÈRE CIPHER - ENCRYPTION & DECRYPTION SYSTEM");
        System.out.println("=".repeat(60));
        System.out.println("1. Encrypt (Basic Mode - Letters Only)");
        System.out.println("2. Decrypt (Basic Mode - Letters Only)");
        System.out.println("3. Encrypt (Advanced Mode - All Printable ASCII)");
        System.out.println("4. Decrypt (Advanced Mode - All Printable ASCII)");
        System.out.println("5. Exit");
        System.out.println("=".repeat(60));
    }

    public static String validateInput(String text, String key, String mode) {
        if (text == null || text.isEmpty()) {
            return "Error: Text cannot be empty.";
        }

        if (key == null || key.isEmpty()) {
            return "Error: Key cannot be empty.";
        }

        if (mode.equals("basic")) {
            for (char c : key.toCharArray()) {
                if (!Character.isLetter(c)) {
                    return "Error: In basic mode, key must contain only alphabetic characters.";
                }
            }
        }

        return null;
    }

    public static String encryptBasic(String plaintext, String key) {

    }

    public static String decryptBasic(String ciphertext, String key) {

    }

    public static String encryptAdvanced(String plaintext, String key) {

    }

    public static String decryptAdvanced(String ciphertext, String key) {

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Vigenère Cipher System!");

        while (true) {
            displayMenu();
            System.out.print("\nEnter your choice (1-5): ");
            String choice = scanner.nextLine().trim();


            if (!choice.matches("[1-5]")) {
                System.out.println("\nInvalid choice. Please enter a number between 1 and 4.");
                continue;
            }

            String mode = (choice.equals("1") || choice.equals("2")) ? "basic" : "advanced";
            String operation = (choice.equals("1") || choice.equals("3")) ? "encrypt" : "decrypt";

            System.out.println("\n--- " + operation.toUpperCase() + " (" + mode.toUpperCase() + " MODE) ---");
            System.out.print("Enter the text to " + operation + ": ");
            String text = scanner.nextLine();
            System.out.print("Enter the encryption key: ");
            String key = scanner.nextLine();

            String error = validateInput(text, key, mode);
            if (error != null) {
                System.out.println("\n" + error);
                continue;
            }

            try {
                String result = "";

                switch (choice) {
                    case "1":
                        result = encryptBasic(text, key);
                        System.out.println("\nOriginal Text: " + text);
                        System.out.println("Encryption Key: " + key);
                        System.out.println("Encrypted Text: " + result);
                        break;

                    case "2":
                        result = decryptBasic(text, key);
                        System.out.println("\nEncrypted Text: " + text);
                        System.out.println("Decryption Key: " + key);
                        System.out.println("Decrypted Text: " + result);
                        break;

                    case "3":
                        result = encryptAdvanced(text, key);
                        System.out.println("\nOriginal Text: " + text);
                        System.out.println("Encryption Key: " + key);
                        System.out.println("Encrypted Text: " + result);
                        break;

                    case "4":
                        result = decryptAdvanced(text, key);
                        System.out.println("\nEncrypted Text: " + text);
                        System.out.println("Decryption Key: " + key);
                        System.out.println("Decrypted Text: " + result);
                        break;

                    case "5":
                        System.exit(0);
                        break;
                }
            } catch (Exception e) {
                System.out.println("\nAn error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
        scanner.close();
    }
}
