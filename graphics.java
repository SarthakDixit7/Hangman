import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class HangmanGame extends JPanel {
    private static final String[] WORDS = { "apple", "banana", "orange", "grape", "pear", "kiwi", "mango", "cherry" };
    private static final int MAX_TRIES = 6;

    private String wordToGuess;
    private StringBuilder guessedWord;
    private Set<Character> guessedLetters;
    private int remainingTries;

    private String[] hangmanFigures = {
            "  O  \n /|\\ \n / \\ ", // 6 tries remaining
            "  O  \n /|\\ \n /   ", // 5 tries remaining
            "  O  \n /|\\ \n     ", // 4 tries remaining
            "  O  \n /|  \n     ", // 3 tries remaining
            "  O  \n  |  \n     ", // 2 tries remaining
            "  O  \n      \n     ", // 1 try remaining
            "      \n      \n     "  // No tries remaining (game over)
    };

    public HangmanGame() {
        wordToGuess = getRandomWord();
        guessedWord = new StringBuilder("_".repeat(wordToGuess.length()));
        guessedLetters = new HashSet<>();
        remainingTries = MAX_TRIES;

        setPreferredSize(new Dimension(300, 400));
        setBackground(Color.WHITE);
        addKeyListener(new HangmanKeyListener());
        setFocusable(true);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw hangman figure
        int figureIndex = MAX_TRIES - remainingTries;
        String figure = hangmanFigures[figureIndex];
        g.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g.setColor(Color.BLACK);
        g.drawString(figure, 50, 50);

        // Draw guessed word progress
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(guessedWord.toString(), 50, 250);
    }

    private class HangmanKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!isGameOver()) {
                char guess = e.getKeyChar();
                if (Character.isLetter(guess)) {
                    guess = Character.toLowerCase(guess);
                    if (guessLetter(guess)) {
                        if (isWordGuessed()) {
                            repaint();
                            JOptionPane.showMessageDialog(null, "Congratulations! You guessed the word: " + wordToGuess);
                        }
                    } else {
                        repaint();
                        if (remainingTries == 0) {
                            JOptionPane.showMessageDialog(null, "Game over! The word was: " + wordToGuess);
                        }
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hangman");
        HangmanGame game = new HangmanGame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
    }
}
