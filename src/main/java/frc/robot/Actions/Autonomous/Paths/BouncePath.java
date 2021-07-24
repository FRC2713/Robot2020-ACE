package frc.robot.Actions.Autonomous.Paths;

import frc.robot.ACE.Actions;
import frc.robot.Actions.Autonomous.GoBackward;
import frc.robot.Actions.Autonomous.GoForward;
import frc.robot.Actions.Autonomous.GyroTurnLeft;
import frc.robot.Actions.Autonomous.GyroTurnRight;

public class BouncePath extends Actions {

  public BouncePath() {
    setIsActiveForAutonomous();
  }

  @Override
  public void initialize() {
    addActions(GyroTurnRight::new).setTargetAngle(63.435);
    addActions(GoForward::new).setTargetDistance(67.082/12);
    addActions(GyroTurnLeft::new).setTargetAngle(40.601);
    addActions(GoBackward::new).setTargetDistance(123.693/12);
    addActions(GyroTurnLeft::new).setTargetAngle(75.964);
    addActions(GoBackward::new).setTargetDistance(30.0/12);
    addActions(GyroTurnLeft::new).setTargetAngle(45);
    addActions(GoBackward::new).setTargetDistance(42.426/12);
    addActions(GyroTurnLeft::new).setTargetAngle(45);
    addActions(GoBackward::new).setTargetDistance(90.0/12);
    addActions(GoForward::new).setTargetDistance(90.0/12);
    addActions(GyroTurnLeft::new).setTargetAngle(45);
    addActions(GoForward::new).setTargetDistance(42.426/12);
    addActions(GyroTurnLeft::new).setTargetAngle(45);
    addActions(GoForward::new).setTargetDistance(60.0/12);
    addActions(GyroTurnLeft::new).setTargetAngle(90);
    addActions(GoForward::new).setTargetDistance(120.0/12);
    addActions(GyroTurnLeft::new).setTargetAngle(45);
    addActions(GoBackward::new).setTargetDistance(42.426/12);
  }

  @Override
  public void runActions() {
    runActionGroup();
  }
}
