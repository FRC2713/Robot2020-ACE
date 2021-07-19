package frc.robot.Events;

import frc.robot.ACE.ACE.Events;
import frc.robot.Components.GyroComponent;
import frc.robot.Components.PixyComponent;

public class GyroEvents extends Events {

  private GyroComponent gyroComponent;

  public GyroEvents() {
    setIsActiveForAutonomous();
    addRequiredComponent(GyroComponent.class);
  }

  @Override
  public void initialize() {
    gyroComponent = (GyroComponent) getComponent("GyroComponent");
    //gyroComponent.calibrate();
  }

  public double getAngle() {
    return gyroComponent.getAngle();
  }

}
