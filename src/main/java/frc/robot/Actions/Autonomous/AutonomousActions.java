package frc.robot.Actions.Autonomous;

import frc.robot.ACE.ACE.Actions;
import frc.robot.Components.DriveComponent;

public class AutonomousActions extends Actions {

  public AutonomousActions() {
    setIsActiveForAutonomous();
    //Required components for an added actions need to be added here
    addRequiredComponent(DriveComponent.class);
  }

  @Override
  public void initialize() {
    addActions(GoForward::new).setTargetDistInFeet(10);
    addActions(GoForward::new).setTargetDistInFeet(25);
  }

  @Override
  public void runActions() {
    runActionGroup();

  }

  @Override
  public void interruptActions() {

  }
}
