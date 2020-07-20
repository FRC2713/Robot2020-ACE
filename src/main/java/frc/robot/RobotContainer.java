package frc.robot;


import frc.robot.Actions.Autonomous.AutonomousActions;
import frc.robot.Actions.ClimberActions;
import frc.robot.Actions.DriveActions;
import frc.robot.Actions.IntakeActions;
import frc.robot.ACE.RobotManager;
import frc.robot.Events.ArduinoEvents;
import frc.robot.Events.ConfigEvents;
import frc.robot.Events.ControllerEvents;
import frc.robot.Events.PixyEvents;

public class RobotContainer {

  public RobotContainer() {
    RobotManager.addEvents(ControllerEvents.class);
    RobotManager.addEvents(ConfigEvents.class);
    RobotManager.addEvents(PixyEvents.class);
    RobotManager.addEvents(ArduinoEvents.class);
    RobotManager.addActions(DriveActions.class);
    RobotManager.addActions(IntakeActions.class);
    RobotManager.addActions(ClimberActions.class);
    RobotManager.addActions(AutonomousActions.class);
  }

}
