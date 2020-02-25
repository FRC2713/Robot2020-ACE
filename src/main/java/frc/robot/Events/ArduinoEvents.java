package frc.robot.Events;

import frc.robot.Components.ArduinoSensors;
import frc.robot.EAC.Events;

public class ArduinoEvents extends Events {

  private ArduinoSensors arduinoSensors;

  public ArduinoEvents() {
   setIsActiveForTeleOp();
   setIsActiveForAutonomous();
   addRequiredComponent(ArduinoSensors.class);
  }

  @Override
  public void initialize() {
    arduinoSensors = (ArduinoSensors) getComponents("ArduinoSensors");
  }

  @Override
  public void pollEvents() {
    arduinoSensors.pollSensors();
  }

  public double getLRFInches() {
    return arduinoSensors.getLRFinches();
  }

  public boolean getSwitch() {
    return arduinoSensors.getSwitchBool();
  }
}
