package frc.robot.EAC;

public abstract class EACBase {

  private boolean isActiveForAutonomous = false;

  private boolean isActiveForTeleOp = false;

  private boolean isActiveForDisabled = false;

  private boolean isActiveForTest = false;

  private boolean isActiveForPeriodic = false;

  public abstract void initialize();

  private boolean isInitialized = false;

  public final void doInitialization() {
    if (isInitialized) return;
    initialize();
    isInitialized = true;
  }

  protected final void setIsActiveForAutonomous() {
    isActiveForAutonomous = true;
  }

  public final boolean getIsActiveForAutonomous() {
    return isActiveForAutonomous;
  }

  protected final void setIsActiveForTeleOp() {
    isActiveForTeleOp = true;
  }

  public final boolean getIsActiveForTeleOp() {
    return isActiveForTeleOp;
  }

  protected final void setIsActiveForDisabled() {
    isActiveForDisabled = true;
  }

  public final boolean getIsActiveForDisabled() {
    return isActiveForDisabled;
  }

  protected final void setIsActiveForTest() {
    isActiveForTest = true;
  }

  public final boolean getIsActiveForTest() {
    return isActiveForTest;
  }

  protected final void setIsActiveForPeriodic() {
    isActiveForPeriodic = true;
  }

  public final boolean getIsActiveForPeriodic() {
    return isActiveForPeriodic;
  }

  protected Components getComponents(String name) {
    return RobotManager.getComponents(this, name);
  }

  protected final void addRequiredComponent(Class<? extends Components> components) {
    RobotManager.addComponents(components);
  }
}
