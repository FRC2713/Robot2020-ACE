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

  private boolean[] arcadebuttonHeldState;
  private boolean[] arcadebuttonLastState;

  private boolean[] leftAttackbuttonHeldState;
  private boolean[] leftAttackbuttonLastState;

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

    count = arcadeController.getButtonCount();
    arcadebuttonHeldState = new boolean[count];
    arcadebuttonLastState = new boolean[count];

    count = leftAttack.getButtonCount();
    leftAttackbuttonHeldState = new boolean[count];
    leftAttackbuttonLastState = new boolean[count];
  }

  public void updateAllButtons() {
    updateButtons(xBoxController,xBoxbuttonHeldState,xBoxbuttonLastState);
    updateButtons(arcadeController,arcadebuttonHeldState,arcadebuttonLastState);
    updateButtons(leftAttack,leftAttackbuttonHeldState,leftAttackbuttonLastState);
  }

  private void updateButtons(GenericHID controller, boolean[] buttonHeldState, boolean[] buttonLastState) {
    int count = controller.getButtonCount();
    for (int i = 0; i < count; i++) {
      buttonLastState[i] = buttonHeldState[i];
      buttonHeldState[i] = controller.getRawButton(i);
    }
  }

  private boolean getButtonPressed(int button, boolean[] buttonHeldState, boolean[] buttonLastState) {
    return buttonHeldState[button] && !buttonLastState[button];
  }

  public boolean getXboxButton(int button) {
    return getButtonPressed(button,xBoxbuttonHeldState,xBoxbuttonLastState);
  }

  public boolean getXboxSwapDriveModeButton() {
    return getXboxButton(RobotMap.SwapDriveModeButton);
  }

  public boolean getXboxAButton() {
    return getXboxButton(XboxController.Button.kA.value);
  }

  public boolean getXboxBButton() {
    return getXboxButton(XboxController.Button.kB.value);
  }

  public boolean getXboxXButton() {
    return getXboxButton(XboxController.Button.kX.value);
  }

  public boolean getXboxYButton() {
    return getXboxButton(XboxController.Button.kY.value);
  }

  public boolean getXboxBackButton() {
    return getXboxButton(XboxController.Button.kBack.value);
  }

  public boolean getXboxStartButton() {
    return getXboxButton(XboxController.Button.kStart.value);
  }

  public boolean getXboxLeftBumper() {
    return getXboxButton(XboxController.Button.kBumperLeft.value);
  }

  public boolean getXboxRightBumper() {
    return getXboxButton(XboxController.Button.kBumperRight.value);
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

  public boolean getArcadeButton(int button) {
    return getButtonPressed(button,arcadebuttonHeldState,arcadebuttonLastState);
  }

  public double getArcadeXAxis() {
    return arcadeController.getX();
  }

  public double getArcadeYAxis() {
    return arcadeController.getY();
  }

  public void rumbleArcade(double intensity, int ms) {
    SM.rumbleController(arcadeController,intensity,ms);
  }

  public boolean getAttackButton(int button) {
    return getButtonPressed(button,leftAttackbuttonHeldState,leftAttackbuttonLastState);
  }

  public double getAttackXAxis() {
    return leftAttack.getX();
  }

  public double getAttackYAxis() {
    return leftAttack.getY();
  }

  public void rumbleAttack(double intensity, int ms) {
    SM.rumbleController(leftAttack,intensity,ms);
  }

}
