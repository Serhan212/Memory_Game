import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private JLabel promptLabel;
    private JLabel scoreLabel;
    private int score = 0;

    public ScorePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        promptLabel = new JLabel("Select the first card", JLabel.LEFT);
        promptLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));

        scoreLabel = new JLabel("Score: 0", JLabel.RIGHT);
        scoreLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));

        add(promptLabel, BorderLayout.WEST);
        add(scoreLabel, BorderLayout.EAST);
    }

    public void setPrompt(String text) {
        promptLabel.setText(text);
    }

    public void updateScore(int newScore) {
        score = newScore;
        scoreLabel.setText("Score: " + score);
    }
}