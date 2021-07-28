package frc.robot.Components;

import com.revrobotics.CANEncoder;
import frc.robot.ACE.Component;
import frc.robot.RobotMap;

public class EncoderComponent extends Component {

  private CANEncoder encoder1;
  private CANEncoder encoder2;
  private CANEncoder encoder3;
  private CANEncoder encoder4;

  private DriveComponent driveComponent;

  public EncoderComponent() {
    setIsActiveForTeleOp();
    setIsActiveForAutonomous();
    setComponentIsPrimaryForInput();
    addRequiredComponent(DriveComponent.class);
  }

  @Override
  public void initialize() {
    driveComponent = (DriveComponent) getComponent("DriveComponent");
    encoder1 = driveComponent.getEncoderFromMotor(1);
    encoder2 = driveComponent.getEncoderFromMotor(2);
    encoder3 = driveComponent.getEncoderFromMotor(3);
    encoder4 = driveComponent.getEncoderFromMotor(4);
  }

  public CANEncoder getEncoder(int index) {
    if (index == 1) {
      return encoder1;
    } else if (index == 2) {
      return encoder2;
    } else if (index == 3) {
      return encoder3;
    } else if (index == 4) {
      return encoder4;
    } else return encoder1;
  }
}
