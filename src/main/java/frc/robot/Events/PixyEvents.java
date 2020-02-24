package frc.robot.Events;

import frc.robot.Components.PixyComponent;
import frc.robot.EAC.Events;

public class PixyEvents extends Events {

  private PixyComponent pixyComponent;

  public PixyEvents() {
    setIsActiveForTeleOp();
    setIsActiveForAutonomous();
    addRequiredComponent(PixyComponent.class);
  }

  @Override
  public void initialize() {
    pixyComponent = (PixyComponent) getComponents("PixyComponent");
    pixyComponent.setLamp((byte) 1, (byte) 1);
    pixyComponent.setLED(255, 255, 255);
  }

  @Override
  public void pollEvents() {
    pixyComponent.processBlocks();
  }
}
