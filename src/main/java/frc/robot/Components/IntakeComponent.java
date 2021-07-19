package frc.robot.Components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.ACE.ACE.Component;
import frc.robot.RobotMap;
import frc.robot.Utility.UtilityModule;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

public class IntakeComponent extends Component {

  private DoubleSolenoid gateSolenoid;
  private boolean isIntakeGateOpen = false;

  private CANSparkMax intakeMotor;
  private CANSparkMax intakeArmMotor;
  private DoubleSolenoid intakeArmSolenoid;


  public IntakeComponent() {
    setIsActiveForTeleOp();
    setIsActiveForAutonomous();
    setComponentIsPrimaryForOutput();
  }

  @Override
  public void initialize() {
    gateSolenoid = UtilityModule.getDoubleSolenoid(RobotMap.IntakeGateUpNode, RobotMap.IntakeGateDownNode);
    intakeArmSolenoid = UtilityModule.getDoubleSolenoid(RobotMap.intakeArmUpNode, RobotMap.intakeArmDownNode);
    intakeMotor = new CANSparkMax(RobotMap.intakeTalonPort, CANSparkMaxLowLevel.MotorType.kBrushed);
    intakeArmMotor = new CANSparkMax(RobotMap.intakeArmTalonPort, CANSparkMaxLowLevel.MotorType.kBrushed);
  }

  public void openIntakeGate() {
    if (isIntakeGateOpen) return;
    gateSolenoid.set(kReverse);
    isIntakeGateOpen = true;
  }

  public void closeIntakeGate() {
    if (!isIntakeGateOpen) return;
    gateSolenoid.set(kForward);
    isIntakeGateOpen = false;
  }

  public void runConveyor() {
    intakeArmSolenoid.set(kReverse);
    intakeArmMotor.set(.5);
    intakeMotor.set(1);
  }

  public void stopConveyor() {
    intakeArmSolenoid.set(kForward);
    intakeArmMotor.stopMotor();
    intakeMotor.stopMotor();
  }

}
