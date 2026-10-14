import java.awt.Color;

public class EmptyCard extends Card {
    public EmptyCard(MemoryGame game) {
        super("", game);
    }

    @Override
    public void showCard() {
        isRevealed = true;
        setText("");
        setBackground(Color.WHITE);
    }
}