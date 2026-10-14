import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.ArrayList;

public class MemoryGame extends JFrame {

    private int score = 0;
    private ScorePanel scorePanel;
    private MemoryGamePanel gamePanel;
    private int difficulty;
    private int totalPairs;
    private int matchedPairsCount = 0;

    private Card firstSelectedCard = null;
    private Card secondSelectedCard = null;
    private boolean isChecking = false;

    public MemoryGame() {

        setTitle("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        if (!selectDifficulty()) {
            System.exit(0);
        }

        scorePanel = new ScorePanel();

        int gridSize = 3;
        if (difficulty == 1) { // medium difficulty
            gridSize = 4;
        }
        if (difficulty == 2) { // hard difficulty
            gridSize = 5;
        }

        gamePanel = new MemoryGamePanel(gridSize, this);

        setLayout(new BorderLayout());
        add(scorePanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        startInitialPreviewTimer();

    }

    private boolean selectDifficulty() {
        String[] options = { "Easy (3x3)", "Medium (4x4)", "Hard (5x5)" };
        int selectedDifficulty = JOptionPane.showOptionDialog(
                null,
                "Select Difficulty Level",
                "Memory Game",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (selectedDifficulty >= 0 && selectedDifficulty < options.length) {
            difficulty = selectedDifficulty;
            return true;
        }
        return false;
    }

    private void startInitialPreviewTimer() {
        gamePanel.revealAllCards(true);
        scorePanel.setPrompt("Memorize the cards! You have 3 seconds.");
        
        Timer previewTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.revealAllCards(false);
                scorePanel.setPrompt("Select the first card.");
                ((Timer) e.getSource()).stop();
            }
        });
        previewTimer.setRepeats(false);
        previewTimer.start();
    }

    public void cardClicked(Card clickedCard) {
        if (isChecking || clickedCard.isRevealed() || clickedCard.isMatched()) {
            return;
        }

        clickedCard.showCard();

        if (firstSelectedCard == null) {
            firstSelectedCard = clickedCard;

            if (firstSelectedCard instanceof EmptyCard) {
                scorePanel.setPrompt("Select the second card");

            } else {
                scorePanel.setPrompt("Select the second card");
            }
        } else {
            secondSelectedCard = clickedCard;
            isChecking = true;

            evaluateCards();
        }
    }

    private void evaluateCards() {
        if (firstSelectedCard instanceof EmptyCard) {
            firstSelectedCard.hideCard();
            firstSelectedCard = secondSelectedCard;
            secondSelectedCard = null;
            isChecking = false;
            scorePanel.setPrompt("Select the second card");
            return;
        }

        if (secondSelectedCard instanceof EmptyCard
                || !firstSelectedCard.getSymbol().equals(secondSelectedCard.getSymbol())) {
            score -= 5;
            scorePanel.updateScore(score);
            scorePanel.setPrompt("No match! Try again.");

            Timer mismatchTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    firstSelectedCard.hideCard();
                    secondSelectedCard.hideCard();
                    firstSelectedCard = null;
                    secondSelectedCard = null;
                    isChecking = false;
                    scorePanel.setPrompt("Select the first card");
                    ((Timer) e.getSource()).stop();
                }
            });
            mismatchTimer.setRepeats(false);
            mismatchTimer.start();

        } else {
            score += 15;
            scorePanel.updateScore(score);

            firstSelectedCard.setMatched(true);
            secondSelectedCard.setMatched(true);

            firstSelectedCard = null;
            secondSelectedCard = null;
            isChecking = false;

            matchedPairsCount++;
            scorePanel.setPrompt("Select the first card");

            if (matchedPairsCount == 4) {
                triggerGameOver();
            }
        }
    }

    private void triggerGameOver() {
        scorePanel.setPrompt("Game Over!");
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Game Over!\nYour Score: " + score + "\nWould you like to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            new MemoryGame();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MemoryGame();
    }
}