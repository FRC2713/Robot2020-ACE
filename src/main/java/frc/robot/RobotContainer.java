package frc.robot;


import frc.robot.Actions.Autonomous.AutonomousActions;
import frc.robot.Actions.ClimberActions;
import frc.robot.Actions.DriveActions;
import frc.robot.Actions.IntakeActions;
import frc.robot.ACE.Foundation.RobotManager;
import frc.robot.Events.*;

public class RobotContainer {

  public RobotContainer() {
    RobotManager.addEvents(GyroEvents.class);
    RobotManager.addEvents(AutonomousSelector.class);
    RobotManager.addEvents(ControllerEvents.class);
    RobotManager.addEvents(ArduinoEvents.class);
    RobotManager.addEvents(ConfigEvents.class);
    RobotManager.addEvents(PixyEvents.class);
    RobotManager.addActions(DriveActions.class);
    RobotManager.addActions(IntakeActions.class);
    RobotManager.addActions(ClimberActions.class);
    RobotManager.addActions(AutonomousActions.class);
  }

}
