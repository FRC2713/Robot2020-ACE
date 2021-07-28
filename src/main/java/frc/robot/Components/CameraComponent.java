package frc.robot.Components;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ACE.Component;

public class CameraComponent extends Component {

  private CameraServer cs = CameraServer.getInstance();
  private UsbCamera frontCamera;
  private UsbCamera backCamera;
  private int currCam = 0;

  public CameraComponent() {
    setIsActiveForTeleOp();
    setIsActiveForDisabled();
    setIsActiveForAutonomous();
    setComponentIsPrimaryForInput();
  }

  @Override
  public void initialize() {
    try {
      cs = CameraServer.getInstance();
      frontCamera = cs.startAutomaticCapture(0);
      backCamera = cs.startAutomaticCapture(1);
      frontCamera.setResolution(256, 144);
      frontCamera.setFPS(30);
      backCamera.setResolution(256, 144);
      backCamera.setFPS(30);
      backCamera.close();
    } catch(Exception e) {
      frontCamera = null;
      backCamera = null;
      System.out.println(e.toString());
    }
  }

  public void changeCamera() {
    if (currCam == 1 && backCamera != null){
      backCamera.close();
      currCam = 0;
      frontCamera = cs.startAutomaticCapture(currCam);
      SmartDashboard.putBoolean("is this running", true);
    }
    else if (currCam == 0 && frontCamera != null){
      frontCamera.close();
      currCam = 1;
      backCamera = cs.startAutomaticCapture(currCam);
      SmartDashboard.putBoolean("is this running", true);
    }
  }
}
