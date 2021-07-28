package frc.robot.Actions.Autonomous;

import frc.robot.Components.DriveComponent;
import frc.robot.ACE.Actions;
import frc.robot.Events.EncoderEvents;

public class GoForward extends Actions {

  static final double ACCEL_CONSTANT = 0.005; //0.02 will bring speed from 0 to 1 in 1 second; scales linearly
  double SLEW_DIST = 5; //Determines how much distance the robot is given to slow down, in feet - will always be equal to the time taken to speed up.
  double leftSpeed = 0;
  double rightSpeed = 0;
  private double accumulatedDist = 0;
  private double[] oldEncoderValue;
  private double targetDist = 0;

  private DriveComponent driveComponent;
  private EncoderEvents encoderEvents;

  public GoForward() {
    setIsActiveForAutonomous();
  }

  @Override
  public void initialize() {
    driveComponent = (DriveComponent) getComponent("DriveComponent");
    encoderEvents = (EncoderEvents) getEvents("EncoderEvents");
    oldEncoderValue = new double[1];
    oldEncoderValue[0] = 0;
    encoderEvents.resetEncoder(1, oldEncoderValue);
  }

  public void setTargetDistance(double distance) {
    targetDist = distance;
    if (SLEW_DIST > (targetDist / 2)) {
      SLEW_DIST = targetDist / 2;
    }
  }

  @Override
  public void runActions() {
    if (accumulatedDist >= targetDist) {
      driveComponent.stopDrive();
      setAreActionsDone(true);
      return;
    }
    System.out.println("Hello: " + targetDist);
    accumulatedDist = Math.abs(encoderEvents.getAccumulatedDistanceInFeet(1, oldEncoderValue));
    if (accumulatedDist < targetDist - SLEW_DIST) {
      if (leftSpeed < 1) {
        leftSpeed += ACCEL_CONSTANT;
        rightSpeed += ACCEL_CONSTANT;
      }
    } else {
      if (leftSpeed > ACCEL_CONSTANT) {
        leftSpeed -= ACCEL_CONSTANT;
        rightSpeed -= ACCEL_CONSTANT;
      } else {
        accumulatedDist = targetDist + 1;
      }
    }
    driveComponent.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void resetActions() {
    encoderEvents.resetEncoder(1, oldEncoderValue);
    accumulatedDist = 0;
  }

  @Override
  public void interruptActions() {
    driveComponent.stopDrive();
  }
}
