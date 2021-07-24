package frc.robot.Events;

import frc.robot.ACE.Events;
import frc.robot.Components.GyroComponent;

public class GyroEvents extends Events {

  private GyroComponent gyroComponent;

  public GyroEvents() {
    setIsActiveForAutonomous();
    addRequiredComponent(GyroComponent.class);
  }

  @Override
  public void initialize() {
    gyroComponent = (GyroComponent) getComponent("GyroComponent");
  }

  public double getAngle() {
    return gyroComponent.getAngle();
  }

}
