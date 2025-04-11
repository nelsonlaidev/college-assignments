/**
 * Since I have experience on using enums in other languages, I decided to use them here.
 * It is more readable and easier to manage.
 * 
 * Reference: https://www.baeldung.com/a-guide-to-java-enums
 */
enum GameState {
  SMALL, BIG, FIRE, INVINCIBLE
}

public class Character {
  private String name;
  private GameState state;
  private int coins;
  private String message;
  private int lives;
  private GameState previousState;
  private int invincibleRounds;

  private static final GameState INITIAL_STATE = GameState.SMALL;
  private static final int INITIAL_COINS = 0;
  private static final int INITIAL_LIVES = 3;
  // +1 for the star power-up
  private static final int INITIAL_INVINCIBLE_ROUNDS = 3 + 1;

  /**
   * Initializes a new character with the given name and message,
   * and other default values.
   *
   * @param name    The name of the character.
   * @param message The message associated with the character.
   */
  public Character(String name, String message) {
    this.name = name;
    this.message = message;

    this.state = INITIAL_STATE;
    this.coins = INITIAL_COINS;
    this.lives = INITIAL_LIVES;
    this.previousState = INITIAL_STATE;
    this.invincibleRounds = INITIAL_INVINCIBLE_ROUNDS;
  }

  // Getters
  /**
   * Returns the character's name.
   *
   * @return The name of the character.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the current game state of the character.
   *
   * @return The current GameState.
   */
  public GameState getState() {
    return this.state;
  }

  /**
   * Returns the current number of coins collected by the character.
   *
   * @return The number of coins.
   */
  public int getCoins() {
    return this.coins;
  }

  /**
   * Returns the message associated with the character.
   *
   * @return The character's message string.
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * Returns the current number of lives the character has.
   *
   * @return The number of lives.
   */
  public int getLives() {
    return this.lives;
  }

  /**
   * Returns the character's state before becoming invincible.
   * Used to revert state after invincibility wears off.
   *
   * @return The previous GameState.
   */
  public GameState getPreviousState() {
    return this.previousState;
  }

  /**
   * Returns the remaining number of rounds the character is invincible.
   *
   * @return The number of remaining invincible rounds.
   */
  public int getInvincibilityRounds() {
    return this.invincibleRounds;
  }

  // Setters
  /**
   * Sets the character's current game state.
   *
   * @param state The new GameState value.
   */
  public void setState(GameState state) {
    this.state = state;
  }

  /**
   * Sets the number of remaining invincibility rounds.
   *
   * @param invincibleRounds The number of rounds to set.
   */
  public void setInvincibilityRounds(int invincibleRounds) {
    this.invincibleRounds = invincibleRounds;
  }

  /**
   * Resets the number of invincibility rounds to its initial value.
   */
  public void resetInvincibilityRounds() {
    this.invincibleRounds = INITIAL_INVINCIBLE_ROUNDS;
  }

  // Methods
  /**
   * Increments the coin count. If the coin count reaches a multiple of 5,
   * grants an extra life and resets the coin count to 0.
   */
  public void collectCoin() {
    coins += 1;

    if (coins % 5 == 0) {
      lives += 1;
      coins = 0;
    }
  }

  /**
   * Randomly selects a power-up item (OneUpMushroom, SuperMushroom, Flower, Star),
   * prints its name and dialogue, and applies its effect to the character.
   * Saves the previous state if a Star is collected.
   */
  public void collectPowerUp() {
    PowerUpItem powerUpItem = null;

    // random number between 0 and 3 (inclusive)
    int randomNumber = (int) (Math.random() * 4);

    // switch case to determine which power-up item is collected
    switch (randomNumber) {
      case 0:
        powerUpItem = new OneUpMushroom(this);
        break;

      case 1:
        powerUpItem = new SuperMushroom(this);
        break;

      case 2:
        powerUpItem = new Flower(this);
        break;

      case 3:
        this.previousState = this.state;
        this.invincibleRounds = INITIAL_INVINCIBLE_ROUNDS;
        powerUpItem = new Star(this);
        break;
    }

    if (powerUpItem != null) {
      System.out.printf("%s collected a %s\n", this.getName(), powerUpItem.getName());
      System.out.println(powerUpItem.getDialogue());

      powerUpItem.applyEffect();
    }
  }

  /**
   * Processes the consequences of the character being hit by an enemy.
   * - If SMALL, loses a life.
   * - If BIG or FIRE, reverts to SMALL state.
   * - If INVINCIBLE, takes no damage.
   */
  public void hitEnemy() {
    System.out.println("Oh enemy!");

    switch (this.state) {
      case SMALL:
        System.out.println("Mama mia! I lost a life!");
        this.lives -= 1;
        break;

      case BIG:
        System.out.println("Ooops! I become small.");
        this.state = GameState.SMALL;
        break;

      case FIRE:
        System.out.println("Oh yeah! I kill the enemy!");
        System.out.println("Ooops! I become small.");
        this.state = GameState.SMALL;
        break;

      case INVINCIBLE:
        System.out.println("Woohoo!");
        break;

      default:
        break;
    }
  }

  // Increments the character's lives count by one.
  public void gainLife() {
    lives += 1;
  }

  // Other unused methods
  // Simulates the character jumping.
  public void jump() {
    System.out.println("Jump!");
  }

  // Simulates the character running.
  public void run() {
    System.out.println("Run!");
  }

  // Simulates the character moving.
  public void move() {
    System.out.println("Move!");
  }
}

class Mario extends Character {
  public Mario() {
    super("Mario", "Let's-a go!");
  }
}

class Luigi extends Character {
  public Luigi() {
    super("Luigi", "I'm-a Luigi!");
  }
}

class Peach extends Character {
  public Peach() {
    super("Peach", "Please be careful!");
  }

  // Simulates Peach floating in the air.
  public void floatInAir() {
    System.out.println("I'm floating in the air!");
  }
}

class Toad extends Character {
  public Toad() {
    super("Toad", "Yay!");
  }

  // Simulates Toad revealing a hidden item.
  public void revealHiddenItem() {
    System.out.println("I'm revealing a hidden item!");
  }
}
