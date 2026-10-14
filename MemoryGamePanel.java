import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGamePanel extends JPanel {
    private ArrayList<Card> cards;
    private int gridSize;

    public MemoryGamePanel(int gridSize, MemoryGame game) {
        this.gridSize = gridSize;
        setLayout(new GridLayout(gridSize, gridSize, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(220, 220, 220));

        cards = new ArrayList<>();
        initializeCards(game);
    }

    private void initializeCards(MemoryGame game) {
        int totalCells = gridSize * gridSize;
        
        String[] symbols = { "♠", "♥", "♦", "♣" };

        for (String symbol : symbols) {
            cards.add(new PairedCard(symbol, game));
            cards.add(new PairedCard(symbol, game));
        }

        int emptyCardCount = totalCells - 8;
        for (int i = 0; i < emptyCardCount; i++) {
            cards.add(new EmptyCard(game));
        }

        Collections.shuffle(cards);

        for (Card card : cards) {
            add(card);
        }
    }

    public void revealAllCards(boolean show) {
        for (Card card : cards) {
            if (show) {
                card.showCard();
            } else {
                card.hideCard();
            }
        }
    }
}