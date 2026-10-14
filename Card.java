import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Card extends JLabel {
    protected String symbol;
    protected boolean isRevealed;
    protected boolean isMatched;
    protected MemoryGame game;

    public Card(String symbol, MemoryGame game) {
        this.symbol = symbol;
        this.game = game;
        this.isRevealed = false;
        this.isMatched = false;

        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
        setFont(new Font("Sans Serif", Font.PLAIN, 50));
        setOpaque(true);
        setBackground(new Color(70, 130, 180));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        setPreferredSize(new Dimension(100, 120));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.cardClicked(Card.this);
            }
        });
    }

    public void showCard() {
        isRevealed = true;
        setText(symbol);
        setBackground(new Color(245, 245, 220));
        setForeground(Color.BLACK);
    }

    public void hideCard() {
        if (!isMatched) {
            isRevealed = false;
            setText("");
            setBackground(new Color(70, 130, 180));
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
        if (matched) {
            isRevealed = true;
            setBackground(Color.WHITE);
        }
    }
}