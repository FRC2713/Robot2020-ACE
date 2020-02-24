package frc.robot.Components;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.EAC.Components;
import frc.robot.RobotMap;
import frc.robot.SM;

public class Controllers extends Components {

  private XboxController xBoxController;
  private Joystick arcadeController;
  private Joystick leftAttack;

  public Controllers() {
    setIsActiveForTeleOp();
  }

  @Override
  public void initialize() {
    for (int i = 0; i < 6; i++) {
      Joystick test = new Joystick(i);
      if (test.getName().equals(RobotMap.XBOX_NAME)) {
        xBoxController = new XboxController(i);
      } else if (test.getName().equals(RobotMap.ARCADE_NAME)) {
        arcadeController = new Joystick(i);
      } else if (test.getName().equals(RobotMap.ATTACK_NAME)) {
        leftAttack = new Joystick(i);
      }
    }
    if (xBoxController == null) {
      xBoxController = new XboxController(RobotMap.BACKUP_XBOX_PORT);
    }
    if (arcadeController == null) {
      arcadeController = new Joystick(RobotMap.BACKUP_ARCADE_PORT);
    }
    if (leftAttack == null) {
      leftAttack = new Joystick(RobotMap.ATTACK_LEFT_PORT);
    }
  }

  public boolean getXboxSwapDriveModeButton() {
    return xBoxController.getRawButtonPressed(RobotMap.SwapDriveModeButton);
  }

  public boolean getXboxAButton() {
   return xBoxController.getAButtonPressed();
  }

  public boolean getXboxBButton() {
    return xBoxController.getBButtonPressed();
  }

  public boolean getXboxXButton() {
    return xBoxController.getXButtonPressed();
  }

  public boolean getXboxYButton() {
    return xBoxController.getYButtonPressed();
  }

  public boolean getXboxBackButton() {
    return xBoxController.getBackButtonPressed();
  }

  public boolean getXboxStartButton() {
    return xBoxController.getStartButtonPressed();
  }

  public boolean getXboxLeftBumper() {
    return xBoxController.getBumper(GenericHID.Hand.kLeft);
  }

  public boolean getXboxRightBumper() {
    return xBoxController.getBumper(GenericHID.Hand.kRight);
  }

  public double getXboxLeftTrigger() {
    return xBoxController.getTriggerAxis(GenericHID.Hand.kLeft);
  }

  public double getXboxRightTrigger() {
    return xBoxController.getTriggerAxis(GenericHID.Hand.kRight);
  }

  public double getXboxXLeftAxis() {
    return xBoxController.getX(GenericHID.Hand.kLeft);
  }

  public double getXboxXRightAxis() {
    return xBoxController.getX(GenericHID.Hand.kRight);
  }

  public double getXboxYLeftAxis() {
    return xBoxController.getY(GenericHID.Hand.kLeft);
  }

  public double getXboxYRightAxis() {
    return xBoxController.getY(GenericHID.Hand.kRight);
  }

  public void rumbleXbox(double intensity, int ms) {
    SM.rumbleController(xBoxController,intensity,ms);
  }

}
