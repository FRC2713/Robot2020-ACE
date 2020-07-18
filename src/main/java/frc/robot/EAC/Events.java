package frc.robot.EAC;

public abstract class Events extends EACBase {

  public void pollEvents() {
  }

  protected final Components getComponents(String name) {
    return getComponent(1, name);
  }
}
