package frc.robot.ACE;

public abstract class Events extends ACEBase {

  public void pollEvents() {
  }

  public void resetEvents() {
  }

  protected final Component getComponent(String name) {
    return getComponent(1, name);
  }
}
