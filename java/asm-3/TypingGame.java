import javax.swing.*;
import java.awt.*;

public class TypingGame {
  private JLabel roundLabel;
  private JLabel mistakeLabel;
  private JLabel timeLabel;
  private static final String[] KEYBOARD_KEYS = {
      "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
      "A", "S", "D", "F", "G", "H", "J", "K", "L", ";",
      "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/"
  };

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

  private JPanel createKeyboardPanel() {
    JPanel keyboardPanel = new JPanel(new GridLayout(3, 10));

    for (String key : KEYBOARD_KEYS) {
      JButton button = new JButton(key);
      button.setFont(new Font("Arial", Font.PLAIN, 24));
      keyboardPanel.add(button);
    }

    return keyboardPanel;
  }

  public TypingGame() {
    JFrame frame = new JFrame("Typing Game");
    JPanel infoPanel = createInfoPanel();
    JPanel keyboardPanel = createKeyboardPanel();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 500);
    frame.setVisible(true);

    frame.add(infoPanel, BorderLayout.NORTH);
    frame.add(keyboardPanel, BorderLayout.SOUTH);
  }

  public static void main(String[] args) {
    // A safe way to put the GUI on the EDT
    SwingUtilities.invokeLater(TypingGame::new);
  }
}
