package frc.robot.Components;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.ACE.Component;
import frc.robot.AdditionalClasses.XboxImpostor;
import frc.robot.RobotMap;
import frc.robot.AdditionalClasses.SM;

import java.util.function.Supplier;

public class Controllers extends Component {

  private XboxController xBoxController;
  private Joystick arcadeController;
  private Joystick leftAttack;

  private boolean[] xBoxbuttonHeldState;
  private boolean[] xBoxbuttonLastState;
  private boolean[] xBoxbuttonToggleState;

  private boolean[] arcadebuttonHeldState;
  private boolean[] arcadebuttonLastState;
  private boolean[] arcadetoggleState;

  private boolean[] leftAttackbuttonHeldState;
  private boolean[] leftAttackbuttonLastState;
  private boolean[] leftAttacktoggleState;

  private double[] xBoxTriggerValue;
  private double[] xBoxLastTriggerValue;
  private boolean[] xBoxTriggerButtonState;
  private boolean[] xBoxLastTriggerButtonState;
  private boolean[] xBoxtriggerToggleState;
  private double[] xBoxtriggerToggleThresholdTarget;

  public Controllers() {
    setIsActiveForTeleOp();
    setComponentIsPrimaryForInput();
  }

  @Override
  public void initialize() {
    reset();
  }

  public void updateAllButtons() {
    updateButtons(xBoxController, xBoxbuttonHeldState, xBoxbuttonLastState);
    updateButtons(arcadeController, arcadebuttonHeldState, arcadebuttonLastState);
    updateButtons(leftAttack, leftAttackbuttonHeldState, leftAttackbuttonLastState);
    updateTriggers(xBoxController);
    updateButtonToggles(xBoxController,xBoxbuttonToggleState,xBoxbuttonHeldState, xBoxbuttonLastState);
    updateButtonToggles(arcadeController,arcadetoggleState,arcadebuttonHeldState, arcadebuttonLastState);
    updateButtonToggles(leftAttack,leftAttacktoggleState,leftAttackbuttonHeldState, leftAttackbuttonLastState);
    updateTriggerToggles(xBoxtriggerToggleState);
  }

  public void reset() {
    int empty_port = 0;
    for (int i = 0; i < 6; i++) {
      Joystick test = new Joystick(i);
      switch (test.getName()) {
        case RobotMap.XBOX_NAME:
        case RobotMap.XBOX2_NAME:
        case RobotMap.XBOX3_NAME:
          xBoxController = new XboxController(i);
          break;
        case RobotMap.XBOX4_NAME:
          xBoxController = new XboxImpostor(i);
          break;
        case RobotMap.ARCADE_NAME:
          arcadeController = new Joystick(i);
          break;
        case RobotMap.ATTACK_NAME:
          leftAttack = new Joystick(i);
          break;
        default:
          if (test.getName().isEmpty()) {
            empty_port = i;
          }
          break;
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

    int count = xBoxController.getButtonCount() + 1;
    xBoxbuttonHeldState = new boolean[count];
    xBoxbuttonLastState = new boolean[count];
    xBoxbuttonToggleState = new boolean[count];

    count = arcadeController.getButtonCount() + 1;
    arcadebuttonHeldState = new boolean[count];
    arcadebuttonLastState = new boolean[count];
    arcadetoggleState = new boolean[count];

    count = leftAttack.getButtonCount() + 1;
    leftAttackbuttonHeldState = new boolean[count];
    leftAttackbuttonLastState = new boolean[count];
    leftAttacktoggleState = new boolean[count];

    xBoxTriggerValue = new double[2];
    xBoxLastTriggerValue = new double[2];
    xBoxTriggerButtonState = new boolean[2];
    xBoxLastTriggerButtonState = new boolean[2];
    xBoxtriggerToggleState = new boolean[2];
    xBoxtriggerToggleThresholdTarget = new double[2];
    xBoxtriggerToggleThresholdTarget[0] = Double.POSITIVE_INFINITY;
    xBoxtriggerToggleThresholdTarget[1] = Double.POSITIVE_INFINITY;
  }

  private void updateButtons(GenericHID controller, boolean[] buttonHeldState, boolean[] buttonLastState) {
    int count = buttonHeldState.length;
    for (int i = 1; i < count; i++) {
      buttonLastState[i] = buttonHeldState[i];
      buttonHeldState[i] = controller.getRawButton(i);
    }
  }

  private void updateButtonToggles(GenericHID controller, boolean[] toggleState, boolean[] buttonHeldState, boolean[] buttonLastState) {
    int count = toggleState.length;
    for (int i = 1; i < count; i++) {
      if (getButtonPressed(i,buttonHeldState,buttonLastState) && !toggleState[i]) {
        toggleState[i] = true;
      } else if (getButtonPressed(i,buttonHeldState,buttonLastState) && toggleState[i]) {
        toggleState[i] = false;
      }
    }
  }

  private void updateTriggers(XboxController controller) {
    for (int i = 0; i < 2; i++) {
      GenericHID.Hand hand = (i == 0) ? GenericHID.Hand.kLeft : GenericHID.Hand.kRight;
      xBoxLastTriggerValue[i] = xBoxTriggerValue[i];
      xBoxTriggerValue[i] = controller.getTriggerAxis(hand);
      xBoxTriggerButtonState[i] = xBoxLastTriggerButtonState[i];
    }
  }

  private void updateTriggerToggles(boolean[] toggleState) {
    for (int i = 0; i < 2; i++) {
      GenericHID.Hand hand = (i == 0) ? GenericHID.Hand.kLeft : GenericHID.Hand.kRight;
      if (getXboxTriggerAsButton(hand,xBoxtriggerToggleThresholdTarget[i]) && !toggleState[i]) {
        toggleState[i] = true;
      } else if (getXboxTriggerAsButton(hand,xBoxtriggerToggleThresholdTarget[i]) && toggleState[i]) {
        toggleState[i] = false;
      }
    }
  }

  private boolean getButtonPressed(int button, boolean[] buttonHeldState, boolean[] buttonLastState) {
    if (button >= buttonHeldState.length) return false;
    return buttonHeldState[button] && !buttonLastState[button];
  }

  private boolean getButtonHeld(int button, boolean[] buttonHeldState) {
    if (button >= buttonHeldState.length) return false;
    return buttonHeldState[button];
  }

  public boolean getXboxButton(int button) {
    return getButtonPressed(button, xBoxbuttonHeldState, xBoxbuttonLastState);
  }

  public boolean getXboxButtonHeld(int button) {
    return getButtonHeld(button, xBoxbuttonHeldState);
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
    return xBoxTriggerValue[GenericHID.Hand.kLeft.value];
  }

  public double getXboxRightTrigger() {
    return xBoxTriggerValue[GenericHID.Hand.kRight.value];
  }


  public boolean getXboxLeftTriggerAsButton(double threshold) {
    return getXboxTriggerAsButton(GenericHID.Hand.kLeft, threshold);
  }

  public boolean getXboxRightTriggerAsButton(double threshold) {
    return getXboxTriggerAsButton(GenericHID.Hand.kRight, threshold);
  }

  private boolean getXboxTriggerAsButton(GenericHID.Hand trigger, double threshold) {
    if (xBoxTriggerValue[trigger.value] >= threshold && !xBoxTriggerButtonState[trigger.value]) {
      xBoxLastTriggerButtonState[trigger.value] = true;
      return true;
    }
    if (xBoxTriggerValue[trigger.value] != xBoxLastTriggerValue[trigger.value])
      xBoxLastTriggerButtonState[trigger.value] = false;
    return false;
  }

  public boolean getXboxLeftTriggerAsToggle(double threshold) {
    return getXboxTriggerAsToggle(GenericHID.Hand.kLeft, threshold);
  }

  public boolean getXboxRightTriggerAsToggle(double threshold) {
    return getXboxTriggerAsToggle(GenericHID.Hand.kRight, threshold);
  }

  private boolean getXboxTriggerAsToggle(GenericHID.Hand trigger, double threshold) {
    xBoxtriggerToggleThresholdTarget[trigger.value] = threshold;
    return xBoxtriggerToggleState[trigger.value];
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
    SM.rumbleController(xBoxController, intensity, ms);
  }

  public boolean getArcadeButton(int button) {
    return getButtonPressed(button, arcadebuttonHeldState, arcadebuttonLastState);
  }

  public boolean getArcadeButtonHeld(int button) {
    return getButtonHeld(button, arcadebuttonHeldState);
  }

  public double getArcadeXAxis() {
    return arcadeController.getX();
  }

  public double getArcadeYAxis() {
    return arcadeController.getY();
  }

  public void rumbleArcade(double intensity, int ms) {
    SM.rumbleController(arcadeController, intensity, ms);
  }

  public boolean getAttackButton(int button) {
    return getButtonPressed(button, leftAttackbuttonHeldState, leftAttackbuttonLastState);
  }

  public boolean getAttackButtonHeld(int button) {
    return getButtonHeld(button, leftAttackbuttonHeldState);
  }

  public double getAttackXAxis() {
    return leftAttack.getX();
  }

  public double getAttackYAxis() {
    return leftAttack.getY();
  }

  public void rumbleAttack(double intensity, int ms) {
    SM.rumbleController(leftAttack, intensity, ms);
  }

}
