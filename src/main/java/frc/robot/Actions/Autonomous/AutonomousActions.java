package frc.robot.Actions.Autonomous;

import frc.robot.ACE.Actions;
import frc.robot.Actions.Autonomous.Paths.BarrelPath;
import frc.robot.Actions.Autonomous.Paths.BouncePath;
import frc.robot.Actions.Autonomous.Paths.SlalomPath;
import frc.robot.Components.DriveComponent;
import frc.robot.Components.GyroComponent;
import frc.robot.Events.AutonomousSelector;

public class AutonomousActions extends Actions {

  private AutonomousSelector selector;

  public AutonomousActions() {
    setIsActiveForAutonomous();
    //Required components for action group actions need to be added here
    addRequiredComponent(DriveComponent.class);
    addRequiredComponent(GyroComponent.class);
  }

  @Override
  public void initialize() {
    selector = (AutonomousSelector) getEvents("AutonomousSelector");
    addActions(BarrelPath::new, "BarrelPath");
    addActions(BouncePath::new, "BouncePath");
    addActions(SlalomPath::new, "SlalomPath");
  }

  @Override
  public void runActions() {
    runActionGroup(selector.getAutonomousPath());
  }
}
