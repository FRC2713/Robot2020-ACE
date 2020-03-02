package frc.robot.EAC;

public abstract class EACBase {

  private boolean isActiveForAutonomous = false;

  private boolean isActiveForTeleOp = false;

  private boolean isActiveForDisabled = false;

  private boolean isActiveForTest = false;

  private boolean isActiveForPeriodic = false;

  private int mode = -1;

  public abstract void initialize();

  private boolean isInitialized = false;

  public final void doInitialization() {
    if (isInitialized) return;
    isInitialized = true;
    initialize();
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

  public final void setMode(int mode) {
    this.mode = mode;
  }

  protected boolean isModeInit() {
    return mode == -1;
  }

  protected boolean isModeAutonomous() {
    return mode == 0;
  }

  protected boolean isModeTeleOp() {
    return mode == 1;
  }

  protected boolean isModeDisabled() {
    return mode == 2;
  }

  protected boolean isModeTest() {
    return mode == 3;
  }

  protected boolean isModePeriodic() {
    return mode == 4;
  }

  protected Components getComponents(String name) {
    return RobotManager.getComponents(this, name);
  }

  protected final void addRequiredComponent(Class<? extends Components> components) {
    RobotManager.addComponents(components);
  }
}
