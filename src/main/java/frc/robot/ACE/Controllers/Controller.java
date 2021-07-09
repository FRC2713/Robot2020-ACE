package frc.robot.ACE.Controllers;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.AdditionalClasses.SM;

public abstract class Controller {
  public abstract void initialize(String name, int port);
  public abstract void update();

  private boolean is_active = false;

  public boolean getIsActive() {
    return is_active;
  }

  public void setActive() {
    is_active = true;
  }

  protected void updateButtons(GenericHID controller, boolean[] buttonHeldState, boolean[] buttonLastState) {
    if (!getIsActive()) return;
    int count = buttonHeldState.length;
    for (int i = 1; i < count; i++) {
      buttonLastState[i] = buttonHeldState[i];
      buttonHeldState[i] = controller.getRawButton(i);
    }
  }

  protected void updateButtonToggles(GenericHID controller, boolean[] buttontoggleState, boolean[] buttonHeldState, boolean[] buttonLastState) {
    if (!getIsActive()) return;
    int count = buttontoggleState.length;
    for (int i = 1; i < count; i++) {
      if (getButtonPressed(i,buttonHeldState,buttonLastState) && !buttontoggleState[i]) {
        buttontoggleState[i] = true;
      } else if (getButtonPressed(i,buttonHeldState,buttonLastState) && buttontoggleState[i]) {
        buttontoggleState[i] = false;
      }
    }
  }

  protected boolean getButtonPressed(int button, boolean[] buttonHeldState, boolean[] buttonLastState) {
    if (!getIsActive()) return false;
    if (button >= buttonHeldState.length) return false;
    return buttonHeldState[button] && !buttonLastState[button];
  }

  protected boolean getButtonHeld(int button, boolean[] buttonHeldState) {
    if (!getIsActive()) return false;
    if (button >= buttonHeldState.length) return false;
    return buttonHeldState[button];
  }

  protected boolean getButtonToggle(int button, boolean[] buttontoggleState) {
    if (!getIsActive()) return false;
    if (button >= buttontoggleState.length) return false;
    return buttontoggleState[button];
  }

  protected void rumbleController(GenericHID controller, double intensity, int ms) {
    if (!getIsActive()) return;
    SM.rumbleController(controller, intensity, ms);
  }
}
