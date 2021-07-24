package frc.robot.Events;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ACE.Events;
import frc.robot.RobotMap;

public class AutonomousSelector extends Events {

  private int path = RobotMap.defaultAutonomousPaths;
  private String[] paths;

  public AutonomousSelector() {
    setIsActiveForAutonomous();
    setIsActiveForDisabled();
  }

  @Override
  public void initialize() {
    paths = new String[RobotMap.autonomousPaths.size()];
    paths = RobotMap.autonomousPaths.toArray(paths);
    SmartDashboard.putNumber("Select Autonomous Path", path);
    SmartDashboard.putString("Autonomous Path", paths[path-1]);
  }

  @Override
  public void pollEvents() {
    int newPath = (int) SmartDashboard.getNumber("Select Autonomous Path", path);
    if (newPath <= 0 || newPath > paths.length) newPath = path;
    path = newPath;
    SmartDashboard.putString("Autonomous Path", paths[path-1]);
  }

  public String getAutonomousPath() {
    return paths[path-1];
  }
}
