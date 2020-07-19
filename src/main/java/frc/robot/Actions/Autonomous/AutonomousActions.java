package frc.robot.Actions.Autonomous;

import frc.robot.ACE.Actions;

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
    goForward.setTargetDistInFeet(10);
    goForward.runActions();
  }

  @Override
  public void interruptActions() {
    goForward.doInterruptActions();
  }
}
