package bullscows;

import java.util.*;

public class Main {
    private static String SECRET_CODE;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        scanner.useLocale(Locale.US);
        String codeString = "";

        try {
            System.out.println("Input the length of the secret code:");
            codeString = scanner.nextLine();
            int codeLength = Integer.parseInt(codeString);
            System.out.println("Input the number of possible symbols in the code:");
            int maxChar = Integer.parseInt(scanner.nextLine());
            SECRET_CODE = generateCode(codeLength, maxChar);

            gameLoop(codeLength);
        }
        catch (NumberFormatException e) {
            System.out.println("Error: " + codeString + " isn't a valid number.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String generateCode(int length, int maxValue) throws Exception {
        if (length > maxValue) {
            throw new Exception(
                    String.format("Error: it's not possible to generate a code with a length of %d with %d unique symbols.",
                    length, maxValue));
        }

        if (maxValue > 36) {
            throw new Exception("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        }

        if (length == 0) {
            throw new Exception("Error: it's not possible to generate a code with zero length.");
        }

        String code = "";
        List<Integer> usedDigits = new ArrayList<>();
        Random random = new Random(10);
        int currentDigit;

        int i = 0;
        do {
            currentDigit = random.nextInt(maxValue);
            if (!usedDigits.contains(currentDigit)) {
                usedDigits.add(currentDigit);
                if (currentDigit < 9) {
                    code = code.concat(String.valueOf(currentDigit));
                } else {
                    code = code.concat(String.valueOf((char) (currentDigit - 9 + 'a')));
                }
                i++;
            }
        } while (i < length);

        String message = "The secret is prepared: ";
        for (int j = 0; j < length; j++) {
            message = message.concat("*");
        }
        if (maxValue < 10) {
            message = message.concat(" (0-" + maxValue + ").");
        } else if (maxValue == 10) {
            message = message.concat(" (0-9, a).");
        } else {
            message = message.concat(" (0-9, a-" + (char) (maxValue - 11 + 'a') + ").");
        }

        System.out.println(message);

        return code;
    }

    public static int[] grade(String answer, int length) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < length; i++) {
            if (SECRET_CODE.charAt(i) == answer.charAt(i)) {
                bulls++;
            }
            for (int j = 0; j < length; j++) {
                if (SECRET_CODE.charAt(i) == answer.charAt(j) && i != j) {
                    cows++;
                }
            }
        }

        return new int[] {bulls, cows};
    }

    public static String generateString(int[] values) {
        String bulls;
        String cows;

        if (values[0] == 0 && values[1] == 0) {
            return "None";
        }
        switch (values[0]) {
            case 0:
                bulls = "";
                break;
            case 1:
                bulls = "1 bull";
                break;
            default:
                bulls = values[0] + " bulls";
        }
        switch (values[1]) {
            case 0:
                cows = "";
                break;
            case 1:
                cows = "1 cow";
                break;
            default:
                cows = values[1] + " cows";
        }

        return "Grade: " + bulls + (values[1] == 0 || values[0] == 0 ? "" : " and ") + cows;
    }

    public static void gameLoop(int length) {
        int[] bullsAndCows;
        int numberOfTurns = 1;

        System.out.println("Okay, let's start a game!");
        do {
            System.out.printf("Turn %s:\n", numberOfTurns++);
            String playersAnswer = scanner.nextLine();
            bullsAndCows = grade(playersAnswer, length);
            System.out.println(generateString(bullsAndCows));
        } while (bullsAndCows[0] != length);

        System.out.println("Congratulations! You guessed the secret code.");
    }
}
