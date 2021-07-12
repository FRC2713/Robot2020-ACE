package frc.robot.Actions;

import frc.robot.ACE.Manager.RobotManager;
import frc.robot.Components.IntakeComponent;
import frc.robot.ACE.Actions;
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

    if (controllerEvents.runIntakeConveyor()) {
      if (RobotManager.isSimulation()) {
        System.out.println("Running IntakeConveyor");
      }
      intakeComponent.runConveyor();
    }

    if (!controllerEvents.runIntakeConveyor()) {
      intakeComponent.stopConveyor();
    }

  }

  @Override
  public void interruptActions() {
    intakeComponent.stopConveyor();
  }
}
