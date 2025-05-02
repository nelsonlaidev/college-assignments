import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
  private Map<String, JButton> keyButtons = new HashMap<>();

  private static final int MAX_ROUNDS = 5;
  private static final Color DEFAULT_MAIN_PANEL_COLOR = Color.WHITE;
  private static final Color WRONG_MAIN_PANEL_COLOR = Color.RED;

  private static final Color CORRECT_KEY_COLOR = Color.GREEN;
  private static final Color INCORRECT_KEY_COLOR = Color.RED;
  private static final Color DEFAULT_KEY_COLOR = Color.BLACK;

  private static final String[] KEYBOARD_KEYS = {
      "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
      "A", "S", "D", "F", "G", "H", "J", "K", "L", ";",
      "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/"
  };
  private static final String[] WORDS = {
      "LAI", "COMPUTER", "PROGRAMMING", "CAT", "OBJECT", "COLLEGE", "CLASS", "TEXT", "COMMUNITY", "AI"
  };

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

  /**
   * Creates a JLabel with the specified text, centered horizontally,
   * and styled with Arial font, plain, size 20.
   *
   * @param text The text to display on the label.
   * @return A styled JLabel instance.
   */
  private JLabel createInfoLabel(String text) {
    JLabel label = new JLabel(text, SwingConstants.CENTER);
    label.setFont(new Font("Arial", Font.PLAIN, 20));
    return label;
  }

  /**
   * Creates the information panel containing round, mistake, and time labels.
   * The panel uses a GridLayout with 1 row and 3 columns and a light gray
   * background.
   *
   * @return JPanel containing the info labels.
   */
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

  /**
   * Creates the main panel that displays the current word to type.
   * The word is shown in a large bold Arial font, centered horizontally,
   * with a background color that changes based on correctness.
   *
   * @return JPanel containing the word label.
   */
  private JPanel createMainPanel() {
    JPanel mainPanel = new JPanel(new BorderLayout());

    wordLabel = new JLabel(currentWord, SwingConstants.CENTER);
    wordLabel.setFont(new Font("Arial", Font.BOLD, 48));
    wordLabel.setOpaque(true);
    wordLabel.setBackground(DEFAULT_MAIN_PANEL_COLOR);
    mainPanel.add(wordLabel, BorderLayout.CENTER);

    return mainPanel;
  }

  /**
   * Creates the keyboard panel with buttons representing keys Q to /.
   * Each button is styled with Arial font size 24.
   * Mouse listeners simulate key press and release events for each button.
   *
   * @return JPanel containing the keyboard buttons.
   */
  private JPanel createKeyboardPanel() {
    JPanel keyboardPanel = new JPanel(new GridLayout(3, 10));

    for (String key : KEYBOARD_KEYS) {
      JButton button = new JButton(key);
      button.setFont(new Font("Arial", Font.PLAIN, 24));
      keyboardPanel.add(button);
      keyButtons.put(key, button);

      // Add mouse listener to simulate keyPressed and keyReleased
      button.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          handleKeyPressed(key.charAt(0));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          handleKeyReleased(key.charAt(0));
        }
      });
    }

    return keyboardPanel;
  }

  /**
   * Handles the visual update when a key is released.
   * Resets the foreground color of the corresponding key button to default.
   *
   * @param released The character of the key released.
   */
  private void handleKeyReleased(char released) {
    String keyStr = String.valueOf(released);
    JButton releasedButton = keyButtons.get(keyStr);
    if (releasedButton != null) {
      releasedButton.setForeground(DEFAULT_KEY_COLOR);
    }
  }

  /**
   * Handles the logic when a key is pressed.
   * Checks if the pressed key matches the first character of the current word.
   * If correct, updates the word display and color, and proceeds to next round or
   * ends game.
   * If incorrect, increments mistake count, applies time penalty, changes colors,
   * and resets the word.
   *
   * @param input The character of the key pressed.
   */
  private void handleKeyPressed(char input) {
    char currentChar = Character.toUpperCase(currentWord.charAt(0));

    // If the game is over or the input is not a valid key, return
    if (isGameOver || !Arrays.asList(KEYBOARD_KEYS).contains(String.valueOf(input)) || currentWord.length() == 0) {
      return;
    }

    if (input == currentChar) {
      JButton correctButton = keyButtons.get(String.valueOf(input));
      if (correctButton != null) {
        correctButton.setForeground(CORRECT_KEY_COLOR);
      }

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

      JButton wrongButton = keyButtons.get(String.valueOf(input));
      if (wrongButton != null) {
        wrongButton.setForeground(INCORRECT_KEY_COLOR);
      }

      currentWord = originalWord;
      wordLabel.setText(currentWord);
    }
  }

  /**
   * Updates the time label to display the elapsed time in seconds.
   *
   * @param time The elapsed time in seconds.
   */
  private void setTimeLabel(int time) {
    timeLabel.setText("Time: " + time + "s");
  }

  /**
   * Starts a new round by incrementing the round count,
   * selecting a random word from the WORDS array,
   * resetting the current word and updating the display.
   * Also requests focus for the game window.
   */
  private void newRound() {
    roundCount++;
    roundLabel.setText("Round: " + roundCount);

    int randomIndex = (int) (Math.random() * WORDS.length);
    originalWord = WORDS[randomIndex];
    currentWord = originalWord;
    wordLabel.setText(currentWord);
    wordLabel.setBackground(DEFAULT_MAIN_PANEL_COLOR);

    frame.requestFocusInWindow();
  }

  /**
   * Ends the game by stopping the timer,
   * setting the game over flag,
   * and updating the word label to show the final time used.
   * The message is formatted with HTML for multi-line display.
   */
  private void endGame() {
    isGameOver = true;
    timer.stop();
    // Use HTML to create a new line
    wordLabel
        .setText("<html><div style='text-align: center;font-size: 16px;'>Game End!<br>Time used: " + counter + " second"
            + (counter > 1 ? "s" : "") + "</div></html>");
  }

  /**
   * Unused method from KeyListener interface.
   * Required to be implemented but no action is taken on keyTyped events.
   *
   * @param e The KeyEvent object.
   */
  @Override
  public void keyTyped(KeyEvent e) {
    // Nothing to do here
  }

  /**
   * Handles key release events from the keyboard.
   * Converts the key character to uppercase and delegates to handleKeyReleased.
   *
   * @param e The KeyEvent object.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    char released = Character.toUpperCase(e.getKeyChar());
    handleKeyReleased(released);
  }

  /**
   * Handles key press events from the keyboard.
   * Converts the key character to uppercase and delegates to handleKeyPressed.
   *
   * @param e The KeyEvent object.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    char input = Character.toUpperCase(e.getKeyChar());
    handleKeyPressed(input);
  }

  /**
   * Main method to launch the TypingGame application.
   * Uses SwingUtilities.invokeLater to ensure GUI creation on the Event Dispatch
   * Thread.
   *
   * @param args Command line arguments (not used).
   */
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
