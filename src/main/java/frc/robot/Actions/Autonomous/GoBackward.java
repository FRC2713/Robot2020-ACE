package frc.robot.Actions.Autonomous;

import frc.robot.ACE.Actions;
import frc.robot.Components.DriveComponent;

public class GoBackward extends Actions {


  static final double ACCEL_CONSTANT = 0.005; //0.02 will bring speed from 0 to 1 in 1 second; scales linearly
  double SLEW_DIST = 5; //Determines how much distance the robot is given to slow down, in feet - will always be equal to the time taken to speed up.
  double leftSpeed = 0;
  double rightSpeed = 0;
  private double accumulatedDist = 0;
  private double[] old_E_Value;
  private double targetDist = 0;

  private DriveComponent driveComponent;

  public GoBackward() {
    setIsActiveForAutonomous();
  }

  @Override
  public void initialize() {
    driveComponent = (DriveComponent) getComponent("DriveComponent");
    old_E_Value = new double[1];
    old_E_Value[0] = 0;
    driveComponent.resetEncoder(1, old_E_Value);
  }

  public void setTargetDistance(double distance) {
    targetDist = distance;
    if (SLEW_DIST > (targetDist / 2)) {
      SLEW_DIST = targetDist / 2;
    }
  }

  @Override
  public void runActions() {
    if (accumulatedDist > targetDist) {
      driveComponent.stopDrive();
      setAreActionsDone(true);
      return;
    }
    System.out.println("Hello: " + targetDist);
    accumulatedDist = Math.abs(driveComponent.toFeet(driveComponent.accumulatedEncoderDistance(1, old_E_Value)));
    if (accumulatedDist < targetDist - SLEW_DIST) {
      if (leftSpeed > -1) {
        leftSpeed -= ACCEL_CONSTANT;
        rightSpeed -= ACCEL_CONSTANT;
      }
    } else {
      if (leftSpeed > ACCEL_CONSTANT) {
        leftSpeed += ACCEL_CONSTANT;
        rightSpeed += ACCEL_CONSTANT;
      } else {
        accumulatedDist = targetDist + 1;
      }
    }
    driveComponent.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void resetActions() {
    driveComponent.resetEncoder(1, old_E_Value);
    accumulatedDist = 0;
  }

  @Override
  public void interruptActions() {
    driveComponent.stopDrive();
  }
}
