package frc.robot.Events;

import frc.robot.Components.Controllers.Controllers;
import frc.robot.ACE.Events;
import frc.robot.RobotMap;

public class ControllerEvents extends Events {

  private Controllers controllers;

  public ControllerEvents() {
    setIsActiveForTeleOp();
    setIsActiveForDisabled();
    addRequiredComponent(Controllers.class);
  }

  @Override
  public void initialize() {
    controllers = (Controllers) getComponent("Controllers");
  }

  @Override
  public void pollEvents() {
    controllers.update();
  }

  @Override
  public void resetEvents() {
    controllers.reset();
  }

  public boolean openIntake() {
    return controllers.getDriveController().getLeftBumper();
  }

  public boolean closeIntake() {
    return controllers.getDriveController().getRightBumper();
  }

  public boolean runIntakeConveyor() {
    return controllers.getDriveController().getRightTriggerAsToggle(1);
  }

  public boolean runClimber() {
    return controllers.getAuxiliaryController().getButtonHeld(RobotMap.climberButton);
  }

  public double getClimberSpeed() {
    return controllers.getAuxiliaryController().getYAxis();
  }

  public boolean winchUp() {
    return controllers.getAuxiliaryController().getButtonHeld(RobotMap.winchUpButton);
  }

  public boolean winchDown() {
    return controllers.getAuxiliaryController().getButtonHeld(RobotMap.winchDownButton);
  }

  public boolean swapPolarity() {
    return controllers.getDriveController().getBButton();
  }

  public boolean swapDriveMode() {
    return controllers.getDriveController().getSwapDriveModeButton();
  }

  public double getSpeedForArcade() {
    return controllers.getDriveController().getYLeftAxis();
  }

  public double getTurnForArcade() {
    return controllers.getDriveController().getXLeftAxis();
  }

  public double getSpeedForBradford() {
    return controllers.getDriveController().getYLeftAxis();
  }

  public double getTurnForBradford() {
    return controllers.getDriveController().getXRightAxis();
  }

  public void rumbleXbox(double intensity, int ms) {
    controllers.getDriveController().rumble(intensity, ms);
  }
}
