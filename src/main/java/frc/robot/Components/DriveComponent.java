package frc.robot.Components;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.ACE.Component;
import frc.robot.RobotMap;
import frc.robot.Utility.Utility;

import static frc.robot.RobotMap.REGULAR_SPEED;

public class DriveComponent extends Component {

  private CANSparkMax frontLeft;
  private CANSparkMax frontRight;
  private CANSparkMax backLeft;
  private CANSparkMax backRight;

  private DifferentialDrive roboDrive;
  private ConfigureBed configureBed;

  public DriveComponent() {
    setIsActiveForTeleOp();
    setIsActiveForAutonomous();
    setComponentIsPrimaryForOutput();
    addRequiredComponent(ConfigureBed.class);
    addRequiredComponent(CompressorComponent.class);
  }

  @Override
  public void initialize() {

    configureBed = (ConfigureBed) getComponent("ConfigureBed");

    if (configureBed.getconfig() == ConfigureBed.Jumper.ONE || configureBed.getconfig() == ConfigureBed.Jumper.THREE) {
      //System.out.println("this is a test; 1");
      frontLeft = new CANSparkMax(RobotMap.frontLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      frontRight = new CANSparkMax(RobotMap.frontRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      backLeft = new CANSparkMax(RobotMap.backLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      backRight = new CANSparkMax(RobotMap.backRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

    } else if (configureBed.getconfig() == ConfigureBed.Jumper.TWO) {
      //System.out.println("this is a test; 2");
      frontLeft = new CANSparkMax(RobotMap.frontLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);
      frontRight = new CANSparkMax(RobotMap.frontRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);
      backLeft = new CANSparkMax(RobotMap.backLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);
      backRight = new CANSparkMax(RobotMap.backRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);

    } else {
      System.out.println("An error has occurred with the jumpers");
      //System.exit(-1);
      frontLeft = new CANSparkMax(RobotMap.frontLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      frontRight = new CANSparkMax(RobotMap.frontRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      backLeft = new CANSparkMax(RobotMap.backLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      backRight = new CANSparkMax(RobotMap.backRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    Utility.initializeSparkDefaults(frontLeft, frontRight);

    backLeft.follow(frontLeft);
    backRight.follow(frontRight);

    roboDrive = new DifferentialDrive(frontLeft, frontRight);

    roboDrive.setDeadband(RobotMap.DEADBAND);

    roboDrive.setMaxOutput(REGULAR_SPEED);

  }

  public CANEncoder getEncoderFromMotor(int index) {
    switch (index) {
      default:
      case 1:
        return frontLeft.getEncoder();
      case 2:
        return backRight.getEncoder();
      case 3:
        return frontRight.getEncoder();
      case 4:
        return backLeft.getEncoder();
    }
  }

  public double slewLimit(double target, double current, double increment) {
    return Utility.slewLimit(target, current, increment);
  }

  public void bradfordDrive(double speed, double turn) {
    roboDrive.arcadeDrive(speed, turn, true);
  }

  public void arcadeDrive(double speed, double turn) {
    roboDrive.arcadeDrive(speed, turn, true);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    roboDrive.tankDrive(leftSpeed, rightSpeed, true);
  }

  public void stopDrive() {
    roboDrive.stopMotor();
  }
}
