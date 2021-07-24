package frc.robot.Actions.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.ACE.Actions;
import frc.robot.Components.DriveComponent;
import frc.robot.Events.GyroEvents;

public class GyroTurnRight extends Actions {

  private double targetAngle;
  private double currentAngle;
  private double originalAngle;
  static final double ACCEL_CONSTANT = 0.003;
  double SLEW_DIST = 45;
  private double rightSpeed = 0;
  private double leftSpeed = 0;

  private DriveComponent driveComponent;
  private GyroEvents gyroEvents;

  public GyroTurnRight() {
    setIsActiveForAutonomous();
  }

  @Override
  public void initialize() {
    driveComponent = (DriveComponent) getComponent("DriveComponent");
    gyroEvents = (GyroEvents) getEvents("GyroEvents");
  }

  public void setTargetAngle(double angle) {
    targetAngle = angle;
    currentAngle = gyroEvents.getAngle();
    originalAngle = currentAngle;
    if (SLEW_DIST > (targetAngle / 2)) {
      SLEW_DIST = targetAngle / 2;
    }
  }

  @Override
  public void runActions() {
    if (currentAngle >= targetAngle) {
      driveComponent.stopDrive();
      setAreActionsDone(true);
      Timer.delay(0.25);
      return;
    }
    currentAngle = gyroEvents.getAngle() - originalAngle;
    if (currentAngle < targetAngle - SLEW_DIST) {
      if (leftSpeed < 0.5) {
        leftSpeed += ACCEL_CONSTANT;
        rightSpeed -= ACCEL_CONSTANT;
      }
    } else {
      if (leftSpeed > ACCEL_CONSTANT) {
        leftSpeed -= ACCEL_CONSTANT;
        rightSpeed += ACCEL_CONSTANT;
      } else {
        currentAngle = targetAngle + 1;
      }
    }
    driveComponent.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void resetActions() {
    currentAngle = 0;
  }

  @Override
  public void interruptActions() {
    driveComponent.stopDrive();
  }
}
