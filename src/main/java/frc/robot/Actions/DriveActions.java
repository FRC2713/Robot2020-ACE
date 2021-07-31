package frc.robot.Actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ACE.Foundation.RobotManager;
import frc.robot.Components.DriveComponent;
import frc.robot.ACE.Actions;
import frc.robot.Events.ControllerEvents;

public class DriveActions extends Actions {

  private ControllerEvents controllerEvents;
  private DriveComponent driveComponent;
  private boolean polarityBoolean = false;
  private double lastLeftStickVal = 0;
  private double lastRightStickVal = 0;
  private double joystickChangeLimit = 0.3;
  private int polarity = 1;
  private int driveMode = 2;

  public DriveActions() {
    setIsActiveForTeleOp();
    addRequiredComponent(DriveComponent.class);
  }

  @Override
  public void initialize() {
    controllerEvents = (ControllerEvents) getEvents("ControllerEvents");
    driveComponent = (DriveComponent) getComponent("DriveComponent");
  }

  @Override
  public void runActions() {

    double rightValue;
    double leftValue;

    final String[] driveModes = {"Arcade", "Tank", "Bradford"};

    if (controllerEvents.swapDriveMode()) {
      driveMode = (driveMode == 2) ? 0 : driveMode + 1;
      lastRightStickVal = 0;
      lastLeftStickVal = 0;
      controllerEvents.rumbleDriveController(.5, 500);
    }

    if (controllerEvents.swapPolarity()) {
      polarityBoolean = !polarityBoolean;
      polarity *= -1;
      controllerEvents.rumbleDriveController(0.2, 500);
    }

    SmartDashboard.putString("Drive Mode is:", driveModes[driveMode]);

    switch (driveModes[driveMode]) {
      default:
      case "Bradford":
        leftValue = driveComponent.slewLimit(controllerEvents.getSpeedForBradford(), lastLeftStickVal, joystickChangeLimit);
        rightValue = driveComponent.slewLimit(controllerEvents.getTurnForBradford(), lastRightStickVal, joystickChangeLimit);
        driveComponent.bradfordDrive(-leftValue * polarity, rightValue * polarity);
        break;
      case "Arcade":
        leftValue = driveComponent.slewLimit(controllerEvents.getSpeedForArcade(), lastLeftStickVal, joystickChangeLimit);
        rightValue = driveComponent.slewLimit(controllerEvents.getTurnForArcade(), lastRightStickVal, joystickChangeLimit);
        driveComponent.arcadeDrive(-leftValue * polarity, rightValue * polarity);
        break;
      case "Tank":
        leftValue = driveComponent.slewLimit(controllerEvents.getLeftSpeedForTank(), lastLeftStickVal, joystickChangeLimit);
        rightValue = driveComponent.slewLimit(controllerEvents.getRightSpeedForTank(), lastRightStickVal, joystickChangeLimit);
        driveComponent.tankDrive(-leftValue * polarity, -rightValue * polarity);
        break;
    }

    if (RobotManager.isSimulation()) {
      System.out.print("Dive Mode: ");
      System.out.println(driveModes[driveMode]);
      System.out.println("Left Value: " + -leftValue * polarity);
      if (driveMode == 1) { //tank
        System.out.println("Right Value: " + -rightValue * polarity);
      } else {
        System.out.println("Right Value: " + rightValue * polarity);
      }
      System.out.println("Polarity: " + polarity);
    }

    lastLeftStickVal = leftValue;
    lastRightStickVal = rightValue;
  }

  @Override
  public void resetActions() {
    lastLeftStickVal = 0;
    lastRightStickVal = 0;
    //should be reset?
    //driveMode = 2;
    //polarityBoolean = false;
    //polarity = 1;
  }

  @Override
  public void interruptActions() {
    driveComponent.stopDrive();
  }
}
