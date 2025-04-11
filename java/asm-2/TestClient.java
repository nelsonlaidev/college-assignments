import java.util.Scanner;

public class TestClient {
  private final Scanner scanner;
  private Character player;
  private int round;

  /**
   * - Initializes the scanner for user input
   * - Sets the starting round value to 1.
   */
  public TestClient() {
    this.scanner = new Scanner(System.in);
    this.round = 1;
  }

  /**
   * Prompts the user to choose a character (Mario, Luigi, Peach, or Toad)
   * and returns the appropriate Character instance based on user selection.
   *
   * @return A Character instance based on user selection.
   */
  private Character getCharacter() {
    Character choice;
    int input;

    // Get user input
    System.out.print("Choose a character: (1) Mario, (2) Luigi, (3) Toad, (4) Peach\n");

    while (true) {
      try {
        input = Integer.parseInt(scanner.nextLine().trim());

        switch (input) {
          case 1:
            choice = new Mario();
            break;

          case 2:
            choice = new Luigi();
            break;

          case 3:
            choice = new Toad();
            break;

          case 4:
            choice = new Peach();
            break;

          default:
            throw new IllegalArgumentException("Can't not found the character");
        }

        break;
      } catch (NumberFormatException e) {
        System.out.println("Please type a integer between the range 1-4");
        continue;
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid choice. Please select a character from 1-4");
        continue;
      }
    }

    return choice;
  }

  /**
   * Prints the player's current lives, coins, and state to the console.
   */
  private void printState() {
    System.out.printf("LIVES: %s COINS: %s STATUS: %s%n", player.getLives(), player.getCoins(), player.getState());
  }

  /**
   * Contains the main game loop. It processes user actions (collect coin,
   * collect power-up, hit enemy), updates the round number, manages
   * invincibility rounds, and ends the game when lives reach 0 or the user exits.
   */
  private void start() {
    player = getCharacter();
    System.out.println("******************************");
    System.out.printf("%s: %s%n", player.getName(), player.getMessage());
    System.out.println("Let's start!");

    while (player.getLives() > 0) {
      System.out.printf("Round: %s%n", round);
      printState();
      System.out
          .print("Choose an action: (1) Collect Coin, (2) Collect Power-Up, (3) Hit Enemy, or type 'end' to exit.: ");

      String action = scanner.nextLine().trim();

      switch (action) {
        case "end":
          // User chose end game
          System.out.println("Ending the game. Bye bye!");
          return;

        case "1":
          player.collectCoin();
          break;

        case "2":
          player.collectPowerUp();
          break;

        case "3":
          player.hitEnemy();
          break;

        default:
          System.out.println("Invalid action. Please select a action from 1-3 or type 'end' to exit.");
          continue;
      }

      round += 1;

      if (player.getState() == GameState.INVINCIBLE) {
        player.setInvincibilityRounds(player.getInvincibilityRounds() - 1);

        if (player.getInvincibilityRounds() == 0) {
          System.out.println("Oh! Star effect has gone!");
          player.setState(player.getPreviousState());
          player.resetInvincibilityRounds();
        }
      }
    }

    if (player.getLives() == 0) {
      System.out.println("Game Over!");
    }
  }

  /**
   * The entry point of the application. Creates an instance of TestClient
   * and calls the start() method to run the game loop.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    TestClient testClient = new TestClient();
    testClient.start();
  }
}
