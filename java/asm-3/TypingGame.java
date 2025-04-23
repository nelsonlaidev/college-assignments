import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class TypingGame implements KeyListener {
  private JLabel roundLabel;
  private JLabel mistakeLabel;
  private JLabel timeLabel;
  private JLabel wordLabel;
  private String currentWord;
  private String originalWord;

  private int counter;
  private int mistakeCount;
  private int roundCount;
  private Timer timer;
  private JFrame frame;
  private boolean isGameOver;

  private static final int MAX_ROUNDS = 5;
  private static final Color DEFAULT_MAIN_PANEL_COLOR = Color.WHITE;
  private static final Color WRONG_MAIN_PANEL_COLOR = Color.RED;

  private static final String[] KEYBOARD_KEYS = {
      "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
      "A", "S", "D", "F", "G", "H", "J", "K", "L", ";",
      "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/"
  };
  private static final String[] WORDS = {
      "LAI", "COMPUTER", "PROGRAMMING", "CAT", "OBJECT", "COLLEGE", "CLASS", "TEXT", "COMMUNITY", "AI"
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

    wordLabel = new JLabel(currentWord, SwingConstants.CENTER);
    wordLabel.setFont(new Font("Arial", Font.BOLD, 48));
    wordLabel.setOpaque(true);
    wordLabel.setBackground(DEFAULT_MAIN_PANEL_COLOR);
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
    frame = new JFrame("Typing Game");
    JPanel infoPanel = createInfoPanel();
    JPanel mainPanel = createMainPanel();
    JPanel keyboardPanel = createKeyboardPanel();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(640, 400);
    frame.addKeyListener(this);

    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowActivated(WindowEvent e) {
        frame.requestFocusInWindow();
      }
    });

    frame.setFocusable(true);
    frame.requestFocusInWindow();
    frame.setVisible(true);

    frame.add(infoPanel, BorderLayout.NORTH);
    frame.add(mainPanel, BorderLayout.CENTER);
    frame.add(keyboardPanel, BorderLayout.SOUTH);

    newRound();

    // Timer
    timer = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        counter++;
        setTimeLabel(counter);
      }
    });
    timer.start();
  }

  private void setTimeLabel(int time) {
    timeLabel.setText("Time: " + time + "s");
  }

  private void newRound() {
    roundCount++;
    roundLabel.setText("Round: " + roundCount);

    int randomIndex = (int) (Math.random() * WORDS.length);
    originalWord = WORDS[randomIndex];
    currentWord = originalWord;
    wordLabel.setText(currentWord);

    frame.requestFocusInWindow();
  }

  private void endGame() {
    isGameOver = true;
    timer.stop();
    // Use HTML to create a new line
    wordLabel
        .setText("<html><div style='text-align: center;font-size: 16px;'>Game End!<br>Time used: " + counter + " second"
            + (counter > 1 ? "s" : "") + "</div></html>");
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Nothing to do here
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Nothing to do here
  }

  @Override
  public void keyPressed(KeyEvent e) {
    char input = Character.toUpperCase(e.getKeyChar());
    char currentChar = Character.toUpperCase(currentWord.charAt(0));

    // If the game is over or the input is not a valid key, return
    if (isGameOver || !Arrays.asList(KEYBOARD_KEYS).contains(String.valueOf(input)) || currentWord.length() == 0) {
      return;
    }

    if (input == currentChar) {
      currentWord = currentWord.substring(1);
      wordLabel.setText(currentWord);
      wordLabel.setBackground(DEFAULT_MAIN_PANEL_COLOR);

      if (currentWord.isEmpty()) {
        if (roundCount < MAX_ROUNDS) {
          newRound();
        } else {
          endGame();
        }
      }
    } else {
      mistakeCount++;
      mistakeLabel.setText("Mistake: " + mistakeCount);
      // Penalty for wrong input
      counter += 5;
      setTimeLabel(counter);
      wordLabel.setBackground(WRONG_MAIN_PANEL_COLOR);
      currentWord = originalWord;
      wordLabel.setText(currentWord);
    }
  }

  public static void main(String[] args) {
    // A safe way to put the GUI on the EDT
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new TypingGame();
      }
    });
  }
}
