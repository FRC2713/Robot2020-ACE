package frc.robot.Events;

import frc.robot.Components.Controllers;
import frc.robot.ACE.Events;

public class ControllerEvents extends Events {

  private Controllers controllers;

  public ControllerEvents() {
    setIsActiveForTeleOp();
    addRequiredComponent(Controllers.class);
  }

  @Override
  public void initialize() {
    controllers = (Controllers) getComponent("Controllers");
  }

  @Override
  public void pollEvents() {
    controllers.updateAllButtons();
  }

  public boolean openIntake() {
    return controllers.getXboxLeftBumper();
  }

  public boolean closeIntake() {
    return controllers.getXboxRightBumper();
  }

  public boolean runIntakeConveyer() {
    return controllers.getXboxRightTriggerAsButton(1);
  }

  public boolean runClimber() {
    return controllers.getClimberButton();
  }

  public double getClimberSpeed() {
    return controllers.getAttackYAxis();
  }

  public boolean winchUp() {
    return controllers.getWinchUpButton();
  }

  public boolean winchDown() {
    return controllers.getWinchDownButton();
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
