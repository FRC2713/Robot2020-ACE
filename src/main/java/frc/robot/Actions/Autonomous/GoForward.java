package frc.robot.Actions.Autonomous;

import frc.robot.Components.DriveComponent;
import frc.robot.ACE.Actions;

public class GoForward extends Actions {

  private DriveComponent driveComponent;
  private double accumulatedDist = 0;
  private double[] old_E_Value;
  private double targetDist = 0;

  public GoForward() {
    setIsActiveForAutonomous();
  }

  @Override
  public void initialize() {
    driveComponent = (DriveComponent) getComponent("DriveComponent");
    old_E_Value = new double[1];
    old_E_Value[0] = 0;
    targetDist = 10;
  }

  @Override
  public void runActions() {
    if (accumulatedDist > targetDist) {
      driveComponent.stopDrive();
      setAreActionsDone(true);
      return;
    }
    System.out.println("Hello: " + targetDist);
    accumulatedDist += driveComponent.toFeet(driveComponent.encoderDistance(1, old_E_Value));
    if (accumulatedDist <= targetDist) {
      driveComponent.tankDrive(0.25, 0.25);
    } else {
      driveComponent.stopDrive();
    }
    accumulatedDist += 0.025;
  }

  public void setTargetDistInInches(double inches) {
    targetDist = inches / 12;
  }

  public void setTargetDistInFeet(double feet) {
    targetDist = feet;
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
