package frc.robot.Actions.Autonomous;

import frc.robot.ACE.Actions;
import frc.robot.Components.DriveComponent;

public class AutonomousActions extends Actions {

  private GoForward goForward;

  public AutonomousActions() {
    setIsActiveForAutonomous();
    //Required components for a spawned action need to be added here
    addRequiredComponent(DriveComponent.class);
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
