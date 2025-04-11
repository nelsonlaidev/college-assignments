public abstract class PowerUpItem {
  private String name;
  private String dialogue;
  protected Character character;

  /**
   * Initializes a power-up item with its name, dialogue, and the character it affects.
   * This constructor is intended to be called by subclasses.
   *
   * @param name      The name of the power-up item.
   * @param dialogue  The dialogue message associated with the power-up.
   * @param character The character instance that this power-up will affect.
   */
  public PowerUpItem(String name, String dialogue, Character character) {
    this.name = name;
    this.dialogue = dialogue;
    this.character = character;
  }

  // Getters
  /**
   * Returns the name of the power-up item.
   *
   * @return The name of the power-up.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the dialogue message associated with this power-up item.
   *
   * @return The dialogue string.
   */
  public String getDialogue() {
    return this.dialogue;
  }

  // Methods
  /**
   * Abstract method to apply the specific effect of the power-up item
   * to the associated character. Subclasses must implement this method.
   */
  public abstract void applyEffect();
}

class OneUpMushroom extends PowerUpItem {
  public OneUpMushroom(Character character) {
    super("One-up Mushroom", "Oh Yeah! I got a life!", character);
  }

  /**
   * Applies the One-Up Mushroom effect: grants the character an extra life.
   */
  public void applyEffect() {
    character.gainLife();
  }
}

class SuperMushroom extends PowerUpItem {
  public SuperMushroom(Character character) {
    super("Super Mushroom", "Oh Yeah! I grow bigger!", character);
  }

  /**
   * Applies the Super Mushroom effect: changes the character's state to BIG
   * if they are currently in the SMALL state.
   */
  public void applyEffect() {
    // Only change state to BIG if the character is in SMALL state
    if (character.getState() == GameState.SMALL) {
      character.setState(GameState.BIG);
    }
  }
}

class Flower extends PowerUpItem {
  public Flower(Character character) {
    super("Flower", "Oh Yeah! I got a fire!", character);
  }

  /**
   * Applies the Flower effect: changes the character's state to FIRE,
   * unless they are currently INVINCIBLE.
   */
  public void applyEffect() {
    // Only change state to FIRE if the character is not in INVINCIBLE state
    if (character.getState() != GameState.INVINCIBLE) {
      character.setState(GameState.FIRE);
    }
  }
}

class Star extends PowerUpItem {
  public Star(Character character) {
    super("Star", "Oh Yeah! I become invincible!", character);
  }

  /**
   * Applies the Star effect: changes the character's state to INVINCIBLE.
   */
  public void applyEffect() {
    character.setState(GameState.INVINCIBLE);
  }
}
