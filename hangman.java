import java.util.*;

public class HangmanGame {
    private static final String[] WORDS = { "apple", "banana", "orange", "grape", "pear", "kiwi", "mango", "cherry" };
    private static final int MAX_TRIES = 6;

    private String wordToGuess;
    private StringBuilder guessedWord;
    private Set<Character> guessedLetters;
    private int remainingTries;

    public HangmanGame() {
        wordToGuess = getRandomWord();
        guessedWord = new StringBuilder("_".repeat(wordToGuess.length()));
        guessedLetters = new HashSet<>();
        remainingTries = MAX_TRIES;
    }

    private String getRandomWord() {
        Random random = new Random();
        return WORDS[random.nextInt(WORDS.length)];
    }

    public boolean isGameOver() {
        return guessedWord.indexOf("_") == -1 || remainingTries == 0;
    }

    public boolean isWordGuessed() {
        return guessedWord.toString().equals(wordToGuess);
    }

    public boolean guessLetter(char letter) {
        if (guessedLetters.contains(letter)) {
            return false; // Letter already guessed
        }

        guessedLetters.add(letter);
        int index = wordToGuess.indexOf(letter);

        if (index == -1) {
            remainingTries--;
            return false; // Incorrect guess
        }

        while (index != -1) {
            guessedWord.setCharAt(index, letter);
            index = wordToGuess.indexOf(letter, index + 1);
        }

        return true; // Correct guess
    }

    public void printGameStatus() {
        System.out.println("Guessed Word: " + guessedWord);
        System.out.println("Remaining Tries: " + remainingTries);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HangmanGame game = new HangmanGame();

        System.out.println("Welcome to Hangman!");
        System.out.println("Guess the word:");

        while (!game.isGameOver()) {
            game.printGameStatus();

            char guess = scanner.next().charAt(0);
            if (!Character.isLetter(guess)) {
                System.out.println("Invalid input. Please enter a letter.");
                continue;
            }

            if (game.guessLetter(guess)) {
                System.out.println("Correct guess!");
            } else {
                System.out.println("Incorrect guess!");
            }
        }

        if (game.isWordGuessed()) {
            System.out.println("Congratulations! You guessed the word: " + game.wordToGuess);
        } else {
            System.out.println("Game over! The word was: " + game.wordToGuess);
        }

        scanner.close();
    }
}
