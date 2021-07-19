package frc.robot.Components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.ACE.ACE.Component;
import frc.robot.RobotMap;

public class ClimberComponent extends Component {

  private CANSparkMax ClimberUp;
  private CANSparkMax WinchOne;
  private CANSparkMax WinchTwo;

  public ClimberComponent() {
    setIsActiveForTeleOp();
    setComponentIsPrimaryForOutput();
  }

  @Override
  public void initialize() {
    ClimberUp = new CANSparkMax(RobotMap.ClimberUpMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);
    WinchOne = new CANSparkMax(RobotMap.WinchOneMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);
    WinchTwo = new CANSparkMax(RobotMap.WinchTwoMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);
    WinchTwo.follow(WinchOne);
  }

  public void runClimber(double speed) {
    ClimberUp.set(speed);
  }

  public void winchUp() {
    WinchOne.set(1);
  }

  public void winchDown() {
    WinchOne.set(-1);
  }

  public void winchStop() {
    WinchOne.set(0);
  }

}
