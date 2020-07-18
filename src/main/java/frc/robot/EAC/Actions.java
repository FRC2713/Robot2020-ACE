package frc.robot.EAC;

public abstract class Actions extends EACBase {

  public abstract void runActions();

  public abstract void interruptActions();

  public void resetActions() {
  }

  public final void doInterruptActions() {
    interruptActions();
    resetActions();
  }

  protected final Events getEvents(String name) {
    return RobotManager.getEvents(this, name);
  }

  protected final Actions spawnActions(Class<? extends Actions> actions) {
    return RobotManager.spawnActions(this, actions);
  }

  protected final Components getComponents(String name) {
    return getComponent(2, name);
  }
}
