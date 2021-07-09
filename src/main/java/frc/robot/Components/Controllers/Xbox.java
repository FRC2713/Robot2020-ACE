package frc.robot.Components.Controllers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.ACE.Controllers.Controller;
import frc.robot.AdditionalClasses.XboxImpostor;
import frc.robot.RobotMap;

public class Xbox extends Controller {

  private XboxController xbox;

  private boolean[] buttonHeldState;
  private boolean[] buttonLastState;
  private boolean[] buttonToggleState;

  private double[] TriggerValue;
  private double[] LastTriggerValue;
  private boolean[] TriggerButtonState;
  private boolean[] LastTriggerButtonState;
  private boolean[] triggerToggleState;
  private double[] triggerToggleThresholdTarget;

  @Override
  public void initialize(String name, int port) {
    if (name.equals(RobotMap.XBOX4_NAME)) {
      xbox = new XboxImpostor(port);
    } else {
      xbox = new XboxController(port);
    }

    int count = xbox.getButtonCount() + 1;
    buttonHeldState = new boolean[count];
    buttonLastState = new boolean[count];
    buttonToggleState = new boolean[count];

    TriggerValue = new double[2];
    LastTriggerValue = new double[2];
    TriggerButtonState = new boolean[2];
    LastTriggerButtonState = new boolean[2];
    triggerToggleState = new boolean[2];
    triggerToggleThresholdTarget = new double[2];
    triggerToggleThresholdTarget[0] = Double.POSITIVE_INFINITY;
    triggerToggleThresholdTarget[1] = Double.POSITIVE_INFINITY;
  }

  @Override
  public void update() {
    updateButtons(xbox, buttonHeldState, buttonLastState);
    updateButtonToggles(xbox, buttonToggleState, buttonHeldState, buttonLastState);
    updateTriggers(xbox);
    updateTriggerToggles(triggerToggleState);
  }

  private void updateTriggers(XboxController controller) {
    if (!getIsActive()) return;
    for (int i = 0; i < 2; i++) {
      GenericHID.Hand hand = (i == 0) ? GenericHID.Hand.kLeft : GenericHID.Hand.kRight;
      LastTriggerValue[i] = TriggerValue[i];
      TriggerValue[i] = controller.getTriggerAxis(hand);
      TriggerButtonState[i] = LastTriggerButtonState[i];
    }
  }

  private void updateTriggerToggles(boolean[] toggleState) {
    if (!getIsActive()) return;
    for (int i = 0; i < 2; i++) {
      GenericHID.Hand hand = (i == 0) ? GenericHID.Hand.kLeft : GenericHID.Hand.kRight;
      if (getTriggerAsButton(hand, triggerToggleThresholdTarget[i]) && !toggleState[i]) {
        toggleState[i] = true;
      } else if (getTriggerAsButton(hand, triggerToggleThresholdTarget[i]) && toggleState[i]) {
        toggleState[i] = false;
      }
    }
  }

  private boolean getTriggerAsButton(GenericHID.Hand trigger, double threshold) {
    if (!getIsActive()) return false;
    if (TriggerValue[trigger.value] >= threshold && !TriggerButtonState[trigger.value]) {
      LastTriggerButtonState[trigger.value] = true;
      return true;
    }
    if (TriggerValue[trigger.value] != LastTriggerValue[trigger.value])
      LastTriggerButtonState[trigger.value] = false;
    return false;
  }

  public boolean getButton(int button) {
    return getButtonPressed(button, buttonHeldState, buttonLastState);
  }

  public boolean getButtonHeld(int button) {
    return getButtonHeld(button, buttonHeldState);
  }

  public boolean getSwapDriveModeButton() {
    return getButton(RobotMap.SwapDriveModeButton);
  }

  public boolean getAButton() {
    return getButton(XboxController.Button.kA.value);
  }

  public boolean getBButton() {
    return getButton(XboxController.Button.kB.value);
  }

  public boolean getXButton() {
    return getButton(XboxController.Button.kX.value);
  }

  public boolean getYButton() {
    return getButton(XboxController.Button.kY.value);
  }

  public boolean getBackButton() {
    return getButton(XboxController.Button.kBack.value);
  }

  public boolean getStartButton() {
    return getButton(XboxController.Button.kStart.value);
  }

  public boolean getLeftBumper() {
    return getButton(XboxController.Button.kBumperLeft.value);
  }

  public boolean getRightBumper() {
    return getButton(XboxController.Button.kBumperRight.value);
  }

  public double getLeftTrigger() {
    if (!getIsActive()) return 0.0;
    return TriggerValue[GenericHID.Hand.kLeft.value];
  }

  public double getRightTrigger() {
    if (!getIsActive()) return 0.0;
    return TriggerValue[GenericHID.Hand.kRight.value];
  }


  public boolean getLeftTriggerAsButton(double threshold) {
    return getTriggerAsButton(GenericHID.Hand.kLeft, threshold);
  }

  public boolean getRightTriggerAsButton(double threshold) {
    return getTriggerAsButton(GenericHID.Hand.kRight, threshold);
  }

  public boolean getLeftTriggerAsToggle(double threshold) {
    return getTriggerAsToggle(GenericHID.Hand.kLeft, threshold);
  }

  public boolean getRightTriggerAsToggle(double threshold) {
    return getTriggerAsToggle(GenericHID.Hand.kRight, threshold);
  }

  private boolean getTriggerAsToggle(GenericHID.Hand trigger, double threshold) {
    if (!getIsActive()) return false;
    triggerToggleThresholdTarget[trigger.value] = threshold;
    return triggerToggleState[trigger.value];
  }

  public double getXLeftAxis() {
    if (!getIsActive()) return 0.0;
    return xbox.getX(GenericHID.Hand.kLeft);
  }

  public double getXRightAxis() {
    if (!getIsActive()) return 0.0;
    return xbox.getX(GenericHID.Hand.kRight);
  }

  public double getYLeftAxis() {
    if (!getIsActive()) return 0.0;
    return xbox.getY(GenericHID.Hand.kLeft);
  }

  public double getYRightAxis() {
    if (!getIsActive()) return 0.0;
    return xbox.getY(GenericHID.Hand.kRight);
  }

  public void rumble(double intensity, int ms) {
    rumbleController(xbox, intensity, ms);
  }
}
