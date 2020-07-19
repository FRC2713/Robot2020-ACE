package frc.robot.Actions;

import frc.robot.Components.IntakeComponent;
import frc.robot.EAC.Actions;
import frc.robot.Events.ControllerEvents;

public class IntakeActions extends Actions {

  private ControllerEvents controllerEvents;
  private IntakeComponent intakeComponent;

  public IntakeActions() {
    setIsActiveForTeleOp();
    addRequiredComponent(IntakeComponent.class);
  }

  @Override
  public void initialize() {
    controllerEvents = (ControllerEvents) getEvents("ControllerEvents");
    intakeComponent = (IntakeComponent) getComponents("IntakeComponent");
  }

  @Override
  public void runActions() {
    if (controllerEvents.openIntake()) {
      intakeComponent.openIntakeGate();
    }

    if (controllerEvents.closeIntake()) {
      intakeComponent.closeIntakeGate();
    }

    if (controllerEvents.runIntakeConveyer()) {
      intakeComponent.runConveyer();
    }

    if (!controllerEvents.runIntakeConveyer()) {
      intakeComponent.stopConveyer();
    }

  }

  @Override
  public void interruptActions() {
    intakeComponent.stopConveyer();
  }
}
