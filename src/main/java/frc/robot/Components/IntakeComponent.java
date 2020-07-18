package frc.robot.Components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.EAC.Components;
import frc.robot.RobotMap;
import frc.robot.SM;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

public class IntakeComponent extends Components {

  private DoubleSolenoid gateSolenoid;
  private boolean isIntakeGateOpen = false;

  private CANSparkMax intakeMotor;
  private CANSparkMax intakeArmMotor;
  private DoubleSolenoid intakeArmSolenoid;


  public IntakeComponent() {
    setIsActiveForTeleOp();
    setComponentIsPrimaryForOutput();
  }

  @Override
  public void initialize() {
    gateSolenoid = SM.getDoubleSolenoid(RobotMap.IntakeGateUpNode, RobotMap.IntakeGateDownNode);
    intakeArmSolenoid = SM.getDoubleSolenoid(RobotMap.intakeArmUpNode, RobotMap.intakeArmDownNode);
    intakeMotor = new CANSparkMax(RobotMap.intakeTalonPort, CANSparkMaxLowLevel.MotorType.kBrushed);
    intakeArmMotor = new CANSparkMax(RobotMap.intakeArmTalonPort, CANSparkMaxLowLevel.MotorType.kBrushed);
  }

  public void openIntakeGate() {
    if (isIntakeGateOpen) return;
    gateSolenoid.set(DoubleSolenoid.Value.kReverse);
    isIntakeGateOpen = true;
  }

  public void closeIntakeGate() {
    if (!isIntakeGateOpen) return;
    gateSolenoid.set(DoubleSolenoid.Value.kForward);
    isIntakeGateOpen = false;
  }

  public void runConveyer() {
    intakeArmSolenoid.set(kReverse);
    intakeArmMotor.set(.5);
    intakeMotor.set(1);
  }

  public void stopConveyer() {
    intakeArmSolenoid.set(kForward);
    intakeArmMotor.stopMotor();
    intakeMotor.stopMotor();
  }

}
