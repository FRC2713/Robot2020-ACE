package frc.robot.Components.Controllers;


import frc.robot.ACE.ACE.Component;
import frc.robot.ACE.Base.Controller;
import frc.robot.RobotMap;


public class Controllers extends Component {

  private Xbox driveController;
  private Controller auxiliaryController;

  public Controllers() {
    setIsActiveForTeleOp();
    setIsActiveForDisabled();
    setComponentIsPrimaryForInput();
  }

  @Override
  public void initialize() {
    driveController = new Xbox("Drive",RobotMap.ALL_XBOX_NAMES);
    auxiliaryController = new Controller(RobotMap.ATTACK_NAME);
    update();
  }

  public void update() {
    driveController.update();
    auxiliaryController.update();
  }

  public void reset() {
    driveController.resetState();
    auxiliaryController.resetState();
  }

  public Xbox getDriveController() {
    return driveController;
  }

  public Controller getAuxiliaryController() {
    return auxiliaryController;
  }
}
