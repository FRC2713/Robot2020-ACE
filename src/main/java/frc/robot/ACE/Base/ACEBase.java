package frc.robot.ACE.Base;

import frc.robot.ACE.ACE.Component;
import frc.robot.ACE.Manager.RobotManager;

public abstract class ACEBase {

  private boolean isActiveForAutonomous = false;

  private boolean isActiveForTeleOp = false;

  private boolean isActiveForDisabled = false;

  private boolean isActiveForTest = false;

  private boolean isActiveForPeriodic = false;

  private boolean isActiveForSimulation = false;

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

  protected final void setIsActiveForSimulation() {
    isActiveForSimulation = true;
  }

  public final boolean getIsActiveForSimulation() {
    return isActiveForSimulation;
  }

  public final void setMode(int mode) {
    this.mode = mode;
  }

  protected final boolean isModeInit() {
    return mode == -1;
  }

  protected final boolean isModeAutonomous() {
    return mode == 0;
  }

  protected final boolean isModeTeleOp() {
    return mode == 1;
  }

  protected final boolean isModeDisabled() {
    return mode == 2;
  }

  protected final boolean isModeTest() {
    return mode == 3;
  }

  protected final boolean isModePeriodic() {
    return mode == 4;
  }

  protected final Component getComponent(int type, String name) {
    return RobotManager.getComponent(type,this,name);
  }

  protected final void addRequiredComponent(Class<? extends Component> component) {
    RobotManager.addComponent(component);
  }
}
