package frc.robot.Actions;

import frc.robot.Components.DriveComponent;
import frc.robot.EAC.Actions;
import frc.robot.Events.ControllerEvents;

public class DriveActions extends Actions {

  private ControllerEvents controllerEvents;
  private DriveComponent driveComponent;
  private boolean useArcadeInsteadOfBradford = false;
  private boolean polarityBoolean = false;
  private double lastLeftStickVal = 0;
  private double lastRightStickVal = 0;
  private double joystickChangeLimit = 0.3;
  private int polarity = 1;

  public DriveActions() {
    setIsActiveForTeleOp();
    addRequiredComponent(DriveComponent.class);
  }

  @Override
  public void initialize() {
    controllerEvents = (ControllerEvents) getEvents("ControllerEvents");
    driveComponent = (DriveComponent) getComponents("DriveComponent");
  }

  @Override
  public void runActions() {

    double speed;
    double turn;

    if (controllerEvents.swapDriveMode()) {
      useArcadeInsteadOfBradford = !useArcadeInsteadOfBradford;
      lastRightStickVal = 0;
      lastLeftStickVal = 0;
      controllerEvents.rumbleXbox(.5, 500);
    }

    if (controllerEvents.swapPolarity()) {
      polarityBoolean = !polarityBoolean;
      polarity *= -1;
      controllerEvents.rumbleXbox(0.2, 500);
    }

    if (useArcadeInsteadOfBradford) {
      speed = driveComponent.slewLimit(controllerEvents.getSpeedForArcade(), lastLeftStickVal, joystickChangeLimit);
      turn = driveComponent.slewLimit(controllerEvents.getTurnForArcade(), lastRightStickVal, joystickChangeLimit);
      driveComponent.arcadeDrive(-speed * polarity, turn * polarity);
    } else {
      speed = driveComponent.slewLimit(controllerEvents.getSpeedForBradford(), lastLeftStickVal, joystickChangeLimit);
      turn = driveComponent.slewLimit(controllerEvents.getTurnForBradford(), lastRightStickVal, joystickChangeLimit);
      driveComponent.bradfordDrive(-speed * polarity, turn * polarity);
    }

    lastLeftStickVal = speed;
    lastRightStickVal = turn;
  }

  @Override
  public void interruptActions() {
    driveComponent.stopDrive();
  }
}
