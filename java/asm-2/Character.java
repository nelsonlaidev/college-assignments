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
  public String getName() {
    return this.name;
  }

  public GameState getState() {
    return this.state;
  }

  public int getCoins() {
    return this.coins;
  }

  public String getMessage() {
    return this.message;
  }

  public int getLives() {
    return this.lives;
  }

  public GameState getPreviousState() {
    return this.previousState;
  }

  public int getInvincibilityRounds() {
    return this.invincibleRounds;
  }

  // Setters
  public void setState(GameState state) {
    this.state = state;
  }

  public void setInvincibilityRounds(int invincibleRounds) {
    this.invincibleRounds = invincibleRounds;
  }

  public void resetInvincibilityRounds() {
    this.invincibleRounds = INITIAL_INVINCIBLE_ROUNDS;
  }

  // Methods
  public void collectCoin() {
    coins += 1;
    // Collecting 5 coins grants an extra life to the game character.
    // Then reset the coins to 0.
    if (coins % 5 == 0) {
      lives += 1;
      coins = 0;
    }
  }

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
        // Save the previous state before creating the Star power-up
        this.previousState = this.state;
        // Reset the invincible rounds to the initial value
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

  public void gainLife() {
    lives += 1;
  }

  // Other unused methods
  public void jump() {
    System.out.println("Jump!");
  }

  public void run() {
    System.out.println("Run!");
  }

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
    super("Luigi", "I'm-a Luigi");
  }
}

class Peach extends Character {
  public Peach() {
    super("Peach", "Please be careful!");
  }

  public void floatInAir() {
    System.out.println("I'm floating in the air!");
  }
}

class Toad extends Character {
  public Toad() {
    super("Toad", "Yay!");
  }

  public void revealHiddenItem() {
    System.out.println("I'm revealing a hidden item!");
  }
}
