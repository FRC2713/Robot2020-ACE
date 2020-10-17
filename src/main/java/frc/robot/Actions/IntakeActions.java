package frc.robot.Actions;

import frc.robot.ACE.RobotManager;
import frc.robot.Components.IntakeComponent;
import frc.robot.ACE.Actions;
import frc.robot.Events.ControllerEvents;

public class IntakeActions extends Actions {

  private ControllerEvents controllerEvents;
  private IntakeComponent intakeComponent;
  private boolean conveyor_active = false;

  public IntakeActions() {
    setIsActiveForTeleOp();
    addRequiredComponent(IntakeComponent.class);
  }

  @Override
  public void initialize() {
    controllerEvents = (ControllerEvents) getEvents("ControllerEvents");
    intakeComponent = (IntakeComponent) getComponent("IntakeComponent");
  }

  @Override
  public void runActions() {
    if (controllerEvents.openIntake()) {
      intakeComponent.openIntakeGate();
    }

    if (controllerEvents.closeIntake()) {
      intakeComponent.closeIntakeGate();
    }

    if (controllerEvents.runIntakeConveyor() && !conveyor_active) {
      conveyor_active = true;
    } else if (controllerEvents.runIntakeConveyor() && conveyor_active) {
      conveyor_active = false;
    }

    if (conveyor_active) {
      if (RobotManager.isSimulation()) System.out.println("Running IntakeConveyor");
      intakeComponent.runConveyor();
    }

    if (!conveyor_active) {
      intakeComponent.stopConveyor();
    }

  }
  @Override
  public void resetActions() {
    conveyor_active = false;
  }

  @Override
  public void interruptActions() {
    intakeComponent.stopConveyor();
  }
}
