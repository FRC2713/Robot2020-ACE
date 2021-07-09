package frc.robot.Components.Controllers;


import frc.robot.ACE.Component;
import frc.robot.ACE.Controllers.ControllerGenerator;
import frc.robot.RobotMap;


public class Controllers extends Component {

  private Xbox driveController;
  private GenericController auxiliaryController;

  public Controllers() {
    setIsActiveForTeleOp();
    setComponentIsPrimaryForInput();
  }

  @Override
  public void initialize() {
    reset();
  }

  public void updateAllButtons() {
    driveController.update();
    auxiliaryController.update();
  }

  public void reset() {
    ControllerGenerator.scan();
    driveController = (Xbox) ControllerGenerator.generate(Xbox.class, RobotMap.XBOX4_NAME);
    auxiliaryController = (GenericController) ControllerGenerator.generate(GenericController.class, RobotMap.ATTACK_NAME);
  }

  public Xbox getDriveController() {
    return driveController;
  }

  public GenericController getAuxiliaryController() {
    return auxiliaryController;
  }
}
