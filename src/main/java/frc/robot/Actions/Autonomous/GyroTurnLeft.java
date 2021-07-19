package frc.robot.Actions.Autonomous;

import frc.robot.ACE.ACE.Actions;
import frc.robot.Components.DriveComponent;

public class GyroTurnLeft extends Actions {

  //private double targetAngle;
  //private double currentAngle;
  //private double originalAngle;
  //final double ACCEL_CONSTANT = 0.003;
  //double SLEW_DIST = 45;
  //private double rightSpeed = 0;
  //private double leftSpeed = 0;

  private DriveComponent driveComponent;

  public GyroTurnLeft() {
    setIsActiveForAutonomous();
  }

  @Override
  public void initialize() {
    driveComponent = (DriveComponent) getComponent("DriveComponent");
  }

  @Override
  public void runActions() {

  }

  @Override
  public void interruptActions() {
    driveComponent.stopDrive();
  }
}
