package frc.robot.Actions;

import frc.robot.Components.ClimberComponent;
import frc.robot.ACE.Actions;
import frc.robot.Events.ControllerEvents;

public class ClimberActions extends Actions {

  private ControllerEvents controllerEvents;
  private ClimberComponent climberComponent;

  public ClimberActions() {
    setIsActiveForTeleOp();
    addRequiredComponent(ClimberComponent.class);
  }

  @Override
  public void initialize() {
    controllerEvents = (ControllerEvents) getEvents("ControllerEvents");
    climberComponent = (ClimberComponent) getComponents("ClimberComponent");
  }

  @Override
  public void runActions() {

    if (controllerEvents.runClimber()) {
      climberComponent.runClimber(controllerEvents.getClimberSpeed());
    }

    if (!controllerEvents.runClimber()) {
      climberComponent.runClimber(0);
    }

    if (controllerEvents.winchUp()) {
      climberComponent.winchUp();
    }

    if (controllerEvents.winchDown()) {
      climberComponent.winchDown();
    }

    if (!controllerEvents.winchUp() && !controllerEvents.winchDown()) {
      climberComponent.winchStop();
    }

  }

  @Override
  public void interruptActions() {
    climberComponent.runClimber(0);
    climberComponent.winchStop();
  }
}
