package frc.robot.Components;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.ACE.ACE.Component;
import frc.robot.RobotMap;

public class LightSensor extends Component {

  private DigitalInput lightSensor;

  public LightSensor() {
    setIsActiveForTeleOp();
    setComponentIsPrimaryForInput();
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
