package frc.robot.Components;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.EAC.Components;
import frc.robot.RobotMap;
import frc.robot.SM;

public class IntakeComponent extends Components {

  private DoubleSolenoid gateSolenoid;
  private boolean isIntakeGateOpen = false;

  public IntakeComponent() {
    setIsActiveForTeleOp();
    setComponentIsPrimaryForOutput();
  }

  @Override
  public void initialize() {
    gateSolenoid = SM.getDoubleSolenoid(RobotMap.IntakeGateUpNode, RobotMap.IntakeGateDownNode);
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

}
