public abstract class PowerUpItem {
  private String name;
  private String dialogue;
  protected Character character;

  public PowerUpItem(String name, String dialogue, Character character) {
    this.name = name;
    this.dialogue = dialogue;
    this.character = character;
  }

  // Getters
  public String getName() {
    return this.name;
  }

  public String getDialogue() {
    return this.dialogue;
  }

  // Methods
  public abstract void applyEffect();
}

class OneUpMushroom extends PowerUpItem {
  public OneUpMushroom(Character character) {
    super("One-up Mushroom", "Oh Yeah! I got a life!", character);
  }

  public void applyEffect() {
    character.gainLife();
  }
}

class SuperMushroom extends PowerUpItem {
  public SuperMushroom(Character character) {
    super("Super Mushroom", "Oh Yeah! I grow bigger!", character);
  }

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

  public void applyEffect() {
    character.setState(GameState.INVINCIBLE);
  }
}
