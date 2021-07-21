package frc.robot.Components;

import edu.wpi.first.hal.util.UncleanStatusException;
import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.ACE.Component;


public class ArduinoSensors extends Component {

  private double LRFinches = 0;
  private int SwitchBool = -1;
  private SerialPort port;

  public ArduinoSensors() {
    setIsActiveForTeleOp();
    setIsActiveForAutonomous();
    setComponentIsPrimaryForInput();
  }

  @Override
  public void initialize() {
    try {
      port = new SerialPort(9600, SerialPort.Port.kUSB);
    } catch (UncleanStatusException u) {
      try {
        port = new SerialPort(9600, SerialPort.Port.kUSB2);
      } catch (UncleanStatusException u2) {
        port = null;
      }
    }
    if (port != null) {
      port.enableTermination();
    }
  }

  public void pollSensors() {
    String str;
    if (port == null) return;
    str = port.readString().trim();

    switch (str) {

      case "LRF":

        try {
          LRFinches = Double.parseDouble(port.readString().trim()) * 0.0393701;
        } catch (NumberFormatException e) {
          LRFinches = -1;
        }
        //  if (LRFinches != -1) System.out.println("This is the distance: " + LRFinches);

        break;

      case "Switch":
        String cond = port.readString().trim();
        if (cond.equals("1")) {
          // System.out.println("one");
          SwitchBool = 1;
        } else if (cond.equals("0")) {
          //   System.out.println("zero");
          SwitchBool = 0;
        } else {
          //   System.out.println("Error for switch");
          //  System.out.println("State is: " + cond);
          SwitchBool = -1;
        }

        break;

      default:
        break;
    }
  }

  public double getLRFinches() {
    return LRFinches;
  }

  public boolean getSwitchBool() {
    if (SwitchBool == 1) {
      return true;
    }
    if (SwitchBool == 0) {
      return false;
    }
    if (SwitchBool == -1) {
      System.out.println("Switch failed.");
      return false;
    }

    return false;
  }


}
