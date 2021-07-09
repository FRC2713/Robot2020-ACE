package frc.robot.Components.Controllers;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.ACE.Controllers.Controller;

public class GenericController extends Controller {

  private Joystick controller;

  private boolean[] buttonHeldState;
  private boolean[] buttonLastState;
  private boolean[] buttonToggleState;

  @Override
  public void initialize(String name, int port) {
    controller = new Joystick(port);

    int count = controller.getButtonCount() + 1;
    buttonHeldState = new boolean[count];
    buttonLastState = new boolean[count];
    buttonToggleState = new boolean[count];
  }

  @Override
  public void update() {
    updateButtons(controller, buttonHeldState, buttonLastState);
    updateButtonToggles(controller, buttonToggleState, buttonHeldState, buttonLastState);
  }

  public boolean getButton(int button) {
    return getButtonPressed(button, buttonHeldState, buttonLastState);
  }

  public boolean getButtonHeld(int button) {
    return getButtonHeld(button, buttonHeldState);
  }

  public double getXAxis() {
    if (!getIsActive()) return 0.0;
    return controller.getX();
  }

  public double getYAxis() {
    if (!getIsActive()) return 0.0;
    return controller.getY();
  }

  public void rumble(double intensity, int ms) {
    rumbleController(controller, intensity, ms);
  }
}
