package frc.robot.Events;

import frc.robot.ACE.Events;
import frc.robot.Components.CameraComponent;
import frc.robot.Components.PixyComponent;

public class CameraEvents extends Events {

  private CameraComponent camera;
  static boolean init = false;

  public CameraEvents() {
    setIsActiveForTeleOp();
    setIsActiveForDisabled();
    setIsActiveForAutonomous();
    addRequiredComponent(CameraComponent.class);
  }

  @Override
  public void initialize() {
    camera = (CameraComponent) getComponent("CameraComponent");
  }

  public void changeCamera() {
    camera.changeCamera();
  }

}
