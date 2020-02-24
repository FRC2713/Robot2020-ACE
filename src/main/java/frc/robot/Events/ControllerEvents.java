package frc.robot.Events;

import frc.robot.Components.Controllers;
import frc.robot.EAC.Events;

public class ControllerEvents extends Events {

  private Controllers controllers;

  public ControllerEvents() {
    setIsActiveForTeleOp();
    addRequiredComponent(Controllers.class);
  }

  @Override
  public void initialize() {
    controllers = (Controllers) getComponents("Controllers");
  }

  public boolean openIntake() {
    return controllers.getXboxLeftBumper();
  }

  public boolean closeIntake() {
    return controllers.getXboxRightBumper();
  }

  public boolean swapPolarity() {
    return controllers.getXboxBButton();
  }

  public boolean swapDriveMode() {
    return controllers.getXboxSwapDriveModeButton();
  }

  public double getSpeedForArcade() {
    return controllers.getXboxYLeftAxis();
  }

  public double getTurnForArcade() {
    return controllers.getXboxXLeftAxis();
  }

  public double getSpeedForBradford() {
    return controllers.getXboxYLeftAxis();
  }

  public double getTurnForBradford() {
    return controllers.getXboxXRightAxis();
  }

  public void rumbleXbox(double intensity, int ms) {
    controllers.rumbleXbox(intensity,ms);
  }
}
