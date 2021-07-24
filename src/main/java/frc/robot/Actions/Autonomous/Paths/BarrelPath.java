package frc.robot.Actions.Autonomous.Paths;

import frc.robot.ACE.Actions;
import frc.robot.Actions.Autonomous.GoForward;
import frc.robot.Actions.Autonomous.GyroTurnLeft;
import frc.robot.Actions.Autonomous.GyroTurnRight;

public class BarrelPath extends Actions {

  public BarrelPath() {
    setIsActiveForAutonomous();
  }

  @Override
  public void initialize() {
    addActions(GoForward::new).setTargetDistance(110.0 / 12);
    addActions(GyroTurnRight::new).setTargetAngle(45);
    addActions(GoForward::new).setTargetDistance(46.426 / 12);
    addActions(GyroTurnRight::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(50.426 / 12);
    addActions(GyroTurnRight::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(50.426 / 12);
    addActions(GyroTurnRight::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(50.426 / 12);
    addActions(GyroTurnRight::new).setTargetAngle(37);
    addActions(GoForward::new).setTargetDistance(98.0 / 12);
    addActions(GyroTurnLeft::new).setTargetAngle(45);
    addActions(GoForward::new).setTargetDistance(46.426 / 12);
    addActions(GyroTurnLeft::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(54.426 / 12);
    addActions(GyroTurnLeft::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(56.426 / 12);
    addActions(GyroTurnLeft::new).setTargetAngle(80);
    addActions(GoForward::new).setTargetDistance(139.279 / 12);
    addActions(GyroTurnLeft::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(50.426 / 12);
    addActions(GyroTurnLeft::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(50.426 / 12);
    addActions(GyroTurnLeft::new).setTargetAngle(21);
    addActions(GoForward::new).setTargetDistance(216.0 / 12);
  }

  @Override
  public void runActions() {
    runActionGroup();
  }
}
