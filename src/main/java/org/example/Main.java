package org.example;

import java.util.Scanner;

public class Main {

    private static final char[] ALPHABET = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static final int ASCII_START = 32;
    private static final int ASCII_END = 126;
    private static final int ASCII_RANGE = ASCII_END - ASCII_START + 1;

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

    private static int getAlphabetPosition(char ch) {
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    public static String encryptBasic(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        String keyUpper = key.toUpperCase();
        int keyIndex = 0;

        for (int i = 0; i < plaintext.length(); i++) {
            char ch = plaintext.charAt(i);

            if (Character.isLetter(ch)) {

                boolean isUpper = Character.isUpperCase(ch);
                char charUpper = Character.toUpperCase(ch);

                int charPos = getAlphabetPosition(charUpper);
                char keyChar = keyUpper.charAt(keyIndex % keyUpper.length());
                int keyPos = getAlphabetPosition(keyChar);

                //(C + K) % 26
                int encryptedPos = (charPos + keyPos) % ALPHABET.length;
                char encryptedChar = ALPHABET[encryptedPos];

                if (!isUpper) {
                    encryptedChar = Character.toLowerCase(encryptedChar);
                }

                ciphertext.append(encryptedChar);
                keyIndex++;
            } else {
                ciphertext.append(ch);
            }
        }

        return ciphertext.toString();
    }

    public static String decryptBasic(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        String keyUpper = key.toUpperCase();
        int keyIndex = 0;

        for (int i = 0; i < ciphertext.length(); i++) {
            char ch = ciphertext.charAt(i);

            if (Character.isLetter(ch)) {

                boolean isUpper = Character.isUpperCase(ch);
                char charUpper = Character.toUpperCase(ch);


                int charPos = getAlphabetPosition(charUpper);
                char keyChar = keyUpper.charAt(keyIndex % keyUpper.length());
                int keyPos = getAlphabetPosition(keyChar);

                //(C - K + 26) mod 26
                int decryptedPos = (charPos - keyPos + ALPHABET.length) % ALPHABET.length;
                char decryptedChar = ALPHABET[decryptedPos];

                if (!isUpper) {
                    decryptedChar = Character.toLowerCase(decryptedChar);
                }

                plaintext.append(decryptedChar);
                keyIndex++;
            } else {
                plaintext.append(ch);
            }
        }

        return plaintext.toString();
    }

    public static String encryptAdvanced(String plaintext, String key) {

        StringBuilder ciphertext = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < plaintext.length(); i++) {
            char ch = plaintext.charAt(i);
            int charCode = (int) ch;

            if (charCode >= ASCII_START && charCode <= ASCII_END) {

                char keyChar = key.charAt(keyIndex % key.length());
                int keyCode = (int) keyChar;

                int charPos = charCode - ASCII_START;
                int keyPos = keyCode - ASCII_START;

                // (P + K) mod range
                int encryptedPos = (charPos + keyPos) % ASCII_RANGE;
                char encryptedChar = (char) (encryptedPos + ASCII_START);

                ciphertext.append(encryptedChar);
                keyIndex++;
            } else {
                ciphertext.append(ch);
            }
        }

        return ciphertext.toString();
    }

    public static String decryptAdvanced(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < ciphertext.length(); i++) {
            char ch = ciphertext.charAt(i);
            int charCode = (int) ch;

            if (charCode >= ASCII_START && charCode <= ASCII_END) {
                char keyChar = key.charAt(keyIndex % key.length());
                int keyCode = (int) keyChar;

                int charPos = charCode - ASCII_START;
                int keyPos = keyCode - ASCII_START;

                // (C - K) mod range
                int decryptedPos = (charPos - keyPos + ASCII_RANGE) % ASCII_RANGE;
                char decryptedChar = (char) (decryptedPos + ASCII_START);

                plaintext.append(decryptedChar);
                keyIndex++;
            } else {
                plaintext.append(ch);
            }
        }

        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Vigenère Cipher System!");

        while (true) {
            displayMenu();
            System.out.print("\nEnter your choice (1-5): ");
            String choice = scanner.nextLine().trim();


            if (!choice.matches("[1-5]")) {
                System.out.println("\nInvalid choice. Please enter a number between 1 and 5.");
                continue;
            }

            if(choice.equals("5")) {
                System.out.println("\nExiting...");
                break;
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
                }
            } catch (Exception e) {
                System.out.println("\nAn error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }
}
