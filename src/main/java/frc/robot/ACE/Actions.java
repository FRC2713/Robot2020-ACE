package frc.robot.ACE;

public abstract class Actions extends ACEBase {

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

  protected final Component getComponents(String name) {
    return getComponent(2, name);
  }
}
