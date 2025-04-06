
/**
 * JavaDoc: https://www.baeldung.com/javadoc
 * Printf: https://www.baeldung.com/java-printstream-printf
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TreasureHunt {
  private final int rows;
  private final int cols;
  private final int maxGuesses;
  private final String[][] grid;
  private final Point treasurePoint;
  private final Random random;
  private final Scanner scanner;
  private final ArrayList<Point> guesses;

  // Constants: grid symbols
  private static final String EMPTY_CELL = "-";
  private static final String GUESS_CELL = "+";
  private static final String TREASURE_CELL = "*";

  public TreasureHunt(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.maxGuesses = (rows * cols) / 2;
    this.grid = new String[rows][cols];
    this.random = new Random();
    this.scanner = new Scanner(System.in);
    this.guesses = new ArrayList<Point>();
    this.treasurePoint = new Point(random.nextInt(rows), random.nextInt(cols));

    // Initialize the grid and set all cells to empty state
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid[i][j] = EMPTY_CELL;
      }
    }
  }

  // Display the grid
  private void displayGrid() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }

  /**
   * Calculate Manhattan distance
   * 
   * @param guess guessed position
   * @return Manhattan distance to treasure
   */
  private int getManhattanDistance(Point guess) {
    return Math.abs(guess.x - treasurePoint.x) + Math.abs(guess.y - treasurePoint.y);
  }

  /**
   * Validate if guess is within grid bounds
   * 
   * @param guess guessed position
   * @return whether valid
   */
  private boolean validateGuess(Point guess) {
    return guess.x < rows && guess.y < cols && guess.x >= 0 && guess.y >= 0;
  }

  /**
   * Start the Treasure Hunt game
   */
  private void start() {
    System.out.println("---------- Welcome to Treasure Hunt ----------");
    System.out.printf("[Debug] Treasure Location: (%s, %s)%n", treasurePoint.x, treasurePoint.y);

    while (true) {
      // Request user input
      System.out.print("Enter your guess (row and column) of the treasure location: ");
      String[] input = scanner.nextLine().trim().split(" ");

      // Expect a valid coordinate
      if (input.length != 2) {
        promptUser();
        continue;
      }

      try {
        Point guess = new Point(Integer.parseInt(input[0]), Integer.parseInt(input[1]));

        // Case: Within grid bounds? - no
        if (!validateGuess(guess)) {
          promptUser();
          continue;
        }

        // Case: Within grid bounds? - yes
        // |
        // v
        // Case: Repeated guess - yes
        if (guesses.contains(guess)) {
          System.out.println("Repeated guess! Please input again.");
          continue;
        }

        // Case: Repeated guess - no
        // Add guess to list
        guesses.add(guess);
        // Update grid
        grid[guess.x][guess.y] = GUESS_CELL;

        // Calculate Manhattan distance
        int distance = getManhattanDistance(guess);

        // Case: Found treasure? - yes
        if (guess.equals(treasurePoint)) {
          System.out.printf("Congratulations! You found the treasure at (%s, %s) with %s unique guess(es)!%n", treasurePoint.x, treasurePoint.y, guesses.size());
          // Mark treasure on grid
          grid[treasurePoint.x][treasurePoint.y] = TREASURE_CELL;
          // Display final grid
          displayGrid();
          break;
        }

        // Case: Found treasure? - no
        System.out.printf("The manhattan distance of the position (%s, %s) to the treasure is %s.%n", guess.x, guess.y, distance);
        System.out.printf("You have now made %s unique guess(es).%n", guesses.size());
        displayGrid();

        // Case: Max guesses reached? - yes
        if (guesses.size() == maxGuesses) {
          System.out.println("You have reached the maximum number of guesses. Game Over!");
          break;
        }

        // Case: Max guesses reached? - no
        System.out.println("Keep searching!\n");
      } catch (NumberFormatException e) {
        promptUser();
        continue;
      }
    }
  }

  // Prompt user when the guess is invalid
  private static void promptUser() {
    System.out.println("Invalid guess! Please input again.");
  }

  /**
   * Entry point
   * 
   * @param args <m> and <n> for the grid dimensions
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Usage: java TreasureHunt <m> <n>");
      return;
    }

    try {
      int rows = Integer.parseInt(args[0]);
      int cols = Integer.parseInt(args[1]);

      if (rows <= 0 || cols <= 0) {
        System.out.println("m and n should be greater than zero");
        return;
      }

      // Case: Valid arguments? - yes
      TreasureHunt treasureHunt = new TreasureHunt(rows, cols);
      treasureHunt.start();
    } catch (NumberFormatException e) {
      // Case: Valid arguments? - no
      System.out.println("m and n should be positive numbers");
    }
  }
}
