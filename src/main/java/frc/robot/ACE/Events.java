package frc.robot.ACE;

public abstract class Events extends ACEBase {

  public void pollEvents() {
  }

  protected final Component getComponents(String name) {
    return getComponent(1, name);
  }
}
