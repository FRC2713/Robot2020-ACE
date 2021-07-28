package frc.robot.Actions.Autonomous.Paths;

import frc.robot.ACE.Actions;
import frc.robot.Actions.Autonomous.GoForward;
import frc.robot.Actions.Autonomous.GyroTurnLeft;
import frc.robot.Actions.Autonomous.GyroTurnRight;

public class SlalomPath extends Actions {

  public SlalomPath() {
    setIsActiveForAutonomous();
  }

  @Override
  public void initialize() {
    addActions(GyroTurnLeft::new).setTargetAngle(45);
    addActions(GoForward::new).setTargetDistance(76.011 / 12);
    addActions(GyroTurnRight::new).setTargetAngle(45);
    addActions(GoForward::new).setTargetDistance(132.504 / 12);
    addActions(GyroTurnRight::new).setTargetAngle(45);
    addActions(GoForward::new).setTargetDistance(76.011 / 12);
    addActions(GyroTurnLeft::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(42.426 / 12);
    addActions(GyroTurnLeft::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(42.426 / 12);
    addActions(GyroTurnLeft::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(75.06 / 12);
    addActions(GyroTurnRight::new).setTargetAngle(45);
    addActions(GoForward::new).setTargetDistance(133.849 / 12);
    addActions(GyroTurnRight::new).setTargetAngle(45);
    addActions(GoForward::new).setTargetDistance(75.08 / 12);
  }

  @Override
  public void runActions() {
    runActionGroup();
  }
}
