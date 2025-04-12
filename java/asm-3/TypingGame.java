import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TypingGame implements KeyListener {
  private JLabel roundLabel;
  private JLabel mistakeLabel;
  private JLabel timeLabel;
  private String currentWord;
  private int counter;

  private static final String[] KEYBOARD_KEYS = {
      "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
      "A", "S", "D", "F", "G", "H", "J", "K", "L", ";",
      "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/"
  };
  private static final String[] WORDS = {
      "<Last_Name>", "COMPUTER", "PROGRAMMING", "CAT", "OBJECT", "COLLEGE", "CLASS", "TEXT", "COMMUNITY", "AI"
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

  private JPanel createMainPanel() {
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(new Color(255, 255, 255));

    JLabel wordLabel = new JLabel(currentWord, SwingConstants.CENTER);
    wordLabel.setFont(new Font("Arial", Font.BOLD, 48));
    mainPanel.add(wordLabel, BorderLayout.CENTER);

    return mainPanel;
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
    currentWord = WORDS[0];
    counter = 0;

    // Timer
    Timer timer = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        counter++;
        timeLabel.setText("Time: " + counter + "s");
      }
    });
    timer.start();

    JFrame frame = new JFrame("Typing Game");
    JPanel infoPanel = createInfoPanel();
    JPanel mainPanel = createMainPanel();
    JPanel keyboardPanel = createKeyboardPanel();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(640, 400);
    frame.addKeyListener(this);
    frame.setFocusable(true);
    frame.requestFocusInWindow();
    frame.setVisible(true);

    frame.add(infoPanel, BorderLayout.NORTH);
    frame.add(mainPanel, BorderLayout.CENTER);
    frame.add(keyboardPanel, BorderLayout.SOUTH);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    System.out.println("Key Typed: " + e.getKeyChar());
  }

  @Override
  public void keyReleased(KeyEvent e) {
    System.out.println("Key Released: " + e.getKeyChar());
  }

  @Override
  public void keyPressed(KeyEvent e) {
    System.out.println("Key Pressed: " + e.getKeyChar());
  }

  public static void main(String[] args) {
    // A safe way to put the GUI on the EDT
    SwingUtilities.invokeLater(TypingGame::new);
  }
}
