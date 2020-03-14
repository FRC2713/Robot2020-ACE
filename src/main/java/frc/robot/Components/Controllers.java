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

  private boolean[] xBoxbuttonHeldState;
  private boolean[] xBoxbuttonLastState;

  public Controllers() {
    setIsActiveForTeleOp();
  }

  @Override
  public void initialize() {
    int empty_port = 0;
    for (int i = 0; i < 6; i++) {
      Joystick test = new Joystick(i);
      if (test.getName().equals(RobotMap.XBOX_NAME)
        ||test.getName().equals(RobotMap.XBOX2_NAME)
        ||test.getName().equals(RobotMap.XBOX3_NAME)) {
        xBoxController = new XboxController(i);
      } else if (test.getName().equals(RobotMap.ARCADE_NAME)) {
        arcadeController = new Joystick(i);
      } else if (test.getName().equals(RobotMap.ATTACK_NAME)) {
        leftAttack = new Joystick(i);
      }
      if (test.getName().isEmpty()){
        empty_port = i;
      }
    }
    if (xBoxController == null) {
      xBoxController = new XboxController(empty_port);
    }
    if (arcadeController == null) {
      arcadeController = new Joystick(empty_port);
    }
    if (leftAttack == null) {
      leftAttack = new Joystick(empty_port);
    }

    int count = xBoxController.getButtonCount();
    xBoxbuttonHeldState = new boolean[count];
    xBoxbuttonLastState = new boolean[count];
  }

  public void updateAllButtons() {
    updateButtons(xBoxController,xBoxbuttonHeldState,xBoxbuttonLastState);
  }

  private void updateButtons(GenericHID controller, boolean[] buttonHeldState, boolean[] buttonLastState) {
    int count = controller.getButtonCount();
    for (int i = 0; i < count; i++) {
      buttonLastState[i] = buttonHeldState[i];
      buttonHeldState[i] = controller.getRawButton(i);
    }

  }

  private boolean getButtonPressed(int button, boolean[] buttonHeldState, boolean[] buttonLastState) {
    if (!buttonHeldState[button] && !buttonLastState[button]) return false;
    if (buttonHeldState[button] && !buttonLastState[button]) return true;
    return false;
  }

  public boolean getXboxSwapDriveModeButton() {
    return getButtonPressed(RobotMap.SwapDriveModeButton,xBoxbuttonHeldState,xBoxbuttonLastState);
  }

  public boolean getXboxAButton() {
    return getButtonPressed(1,xBoxbuttonHeldState,xBoxbuttonLastState);
  }

  public boolean getXboxBButton() {
    return getButtonPressed(2,xBoxbuttonHeldState,xBoxbuttonLastState);
  }

  public boolean getXboxXButton() {
    return getButtonPressed(3,xBoxbuttonHeldState,xBoxbuttonLastState);
  }

  public boolean getXboxYButton() {
    return getButtonPressed(4,xBoxbuttonHeldState,xBoxbuttonLastState);
  }

  public boolean getXboxBackButton() {
    return getButtonPressed(7,xBoxbuttonHeldState,xBoxbuttonLastState);
  }

  public boolean getXboxStartButton() {
    return getButtonPressed(8,xBoxbuttonHeldState,xBoxbuttonLastState);
  }

  public boolean getXboxLeftBumper() {
    return getButtonPressed(5,xBoxbuttonHeldState,xBoxbuttonLastState);
  }

  public boolean getXboxRightBumper() {
    return getButtonPressed(6,xBoxbuttonHeldState,xBoxbuttonLastState);
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
