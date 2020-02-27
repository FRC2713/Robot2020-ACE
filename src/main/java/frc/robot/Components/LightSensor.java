package frc.robot.Components;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.EAC.Components;
import frc.robot.RobotMap;

public class LightSensor extends Components {

  private DigitalInput lightSensor;

  public LightSensor() {
    setIsActiveForTeleOp();
  }

  @Override
  public void initialize() {
    lightSensor = new DigitalInput(RobotMap.lightSensor);
  }

  public boolean isTripped() {
    return !lightSensor.get();
  }

  public void printOut() {
    System.out.println("LIGHT SENSOR OUTPUT:" + isTripped());
  }
}
