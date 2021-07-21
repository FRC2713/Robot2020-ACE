package frc.robot.Components.Controllers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.ACE.Foundation.Controller;
import frc.robot.Utility.XboxImpostor;
import frc.robot.RobotMap;

import java.util.List;

public class Xbox extends Controller {

  private XboxController xbox;

  private double[] TriggerValue;
  private double[] LastTriggerValue;
  private boolean[] TriggerButtonState;
  private boolean[] LastTriggerButtonState;
  private boolean[] triggerToggleState;
  private double[] triggerToggleThresholdTarget;

  public Xbox(String groupName, List<String> names) {
    super(groupName, names);
  }

  public Xbox(String name, int port) {
    super(name,port);
  }

  public Xbox(String name) {
    super(name);
  }

  @Override
  public void initialize(String name, int port) {
    if (name.equals(RobotMap.XBOX4_NAME)) {
      xbox = new XboxImpostor(port);
    } else {
      xbox = new XboxController(port);
    }
    setController(xbox);
    generateState();
    setName(name);
  }

  protected void generateCustomState() {
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
    super.update();
    updateTriggers(xbox);
    updateTriggerToggles(triggerToggleState);
  }

  private void updateTriggers(XboxController controller) {
    if (!isActive()) return;
    for (int i = 0; i < 2; i++) {
      GenericHID.Hand hand = (i == 0) ? GenericHID.Hand.kLeft : GenericHID.Hand.kRight;
      LastTriggerValue[i] = TriggerValue[i];
      TriggerValue[i] = controller.getTriggerAxis(hand);
      TriggerButtonState[i] = LastTriggerButtonState[i];
    }
  }

  private void updateTriggerToggles(boolean[] toggleState) {
    if (!isActive()) return;
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
    if (!isActive()) return false;
    if (TriggerValue[trigger.value] >= threshold && !TriggerButtonState[trigger.value]) {
      LastTriggerButtonState[trigger.value] = true;
      return true;
    }
    if (TriggerValue[trigger.value] != LastTriggerValue[trigger.value])
      LastTriggerButtonState[trigger.value] = false;
    return false;
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
    if (!isActive()) return 0.0;
    return TriggerValue[GenericHID.Hand.kLeft.value];
  }

  public double getRightTrigger() {
    if (!isActive()) return 0.0;
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
    if (!isActive()) return false;
    triggerToggleThresholdTarget[trigger.value] = threshold;
    return triggerToggleState[trigger.value];
  }

  public double getXLeftAxis() {
    if (!isActive()) return 0.0;
    return xbox.getX(GenericHID.Hand.kLeft);
  }

  public double getXRightAxis() {
    if (!isActive()) return 0.0;
    return xbox.getX(GenericHID.Hand.kRight);
  }

  public double getYLeftAxis() {
    if (!isActive()) return 0.0;
    return xbox.getY(GenericHID.Hand.kLeft);
  }

  public double getYRightAxis() {
    if (!isActive()) return 0.0;
    return xbox.getY(GenericHID.Hand.kRight);
  }
}
