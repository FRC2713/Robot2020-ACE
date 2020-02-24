package frc.robot.Actions.Autonomous;

import frc.robot.EAC.Actions;

public class AutonomousActions extends Actions {

  private GoForward goForward;

  public AutonomousActions() {
    setIsActiveForAutonomous();
  }

  @Override
  public void initialize() {
    goForward = (GoForward) spawnActions(GoForward.class);
  }

  @Override
  public void runActions() {
    goForward.runActions();
  }

  @Override
  public void interruptActions() {
    goForward.doInterruptActions();
  }
}
