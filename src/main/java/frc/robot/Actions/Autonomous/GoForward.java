package frc.robot.Actions.Autonomous;

import frc.robot.Components.DriveComponent;
import frc.robot.EAC.Actions;

public class GoForward extends Actions {

  private DriveComponent driveComponent;
  private double accumulatedDist = 0;
  private double[] old_E_Value;


  public GoForward() {
    setIsActiveForAutonomous();
    addRequiredComponent(DriveComponent.class);
  }

  @Override
  public void initialize() {
    driveComponent = (DriveComponent) getComponents("DriveComponent");
    old_E_Value = new double[1];
    old_E_Value[0] = 0;
  }

  @Override
  public void runActions() {
    if (accumulatedDist > 10) return;
    accumulatedDist += driveComponent.toFeet(driveComponent.encoderDistance(1, old_E_Value));
    driveComponent.tankDrive(0.25, 0.25);
  }

  @Override
  public void resetActions() {
    accumulatedDist = 0;
    old_E_Value[0] = 0;
  }

  @Override
  public void interruptActions() {
    driveComponent.stopDrive();
  }
}
