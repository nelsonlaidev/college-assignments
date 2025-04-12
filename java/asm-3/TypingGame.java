import javax.swing.*;
import java.awt.*;

public class TypingGame {
  private JLabel roundLabel;
  private JLabel mistakeLabel;
  private JLabel timeLabel;

  private JLabel createInfoLabel(String text) {
    JLabel label = new JLabel(text, SwingConstants.CENTER);
    label.setFont(new Font("Arial", Font.PLAIN, 20));
    return label;
  }

  private JPanel createInfoPanel() {
    JPanel infoPanel = new JPanel(new GridLayout(1, 3, 10, 0));
    infoPanel.setBackground(new Color(238, 238, 238));

    roundLabel = createInfoLabel("Round: 0");
    mistakeLabel = createInfoLabel("Mistake: 0");
    timeLabel = createInfoLabel("Time: 0s");

    infoPanel.add(roundLabel);
    infoPanel.add(mistakeLabel);
    infoPanel.add(timeLabel);

    return infoPanel;
  }

  public TypingGame() {
    JFrame frame = new JFrame("Typing Game");
    JPanel infoPanel = createInfoPanel();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 500);
    frame.setVisible(true);

    frame.add(infoPanel, BorderLayout.NORTH);
  }

  public static void main(String[] args) {
    // A safe way to put the GUI on the EDT
    SwingUtilities.invokeLater(TypingGame::new);
  }
}
