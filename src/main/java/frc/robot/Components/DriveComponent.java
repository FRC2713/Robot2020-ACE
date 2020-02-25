package frc.robot.Components;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.EAC.Components;
import frc.robot.RobotMap;
import frc.robot.SM;

import static frc.robot.RobotMap.REGULAR_SPEED;

public class DriveComponent extends Components {

  private CANSparkMax frontLeft;
  private CANSparkMax frontRight;
  private CANSparkMax backLeft;
  private CANSparkMax backRight;

  private CANEncoder encoder1;
  private CANEncoder encoder2;
  private CANEncoder encoder3;
  private CANEncoder encoder4;

  private DifferentialDrive roboDrive;
  private ConfigureBed configureBed;

  public DriveComponent() {
    setIsActiveForTeleOp();
    setIsActiveForAutonomous();
    addRequiredComponent(ConfigureBed.class);
  }

  @Override
  public void initialize() {

    configureBed = (ConfigureBed) getComponents("ConfigureBed");

    if (configureBed.getconfig() == ConfigureBed.Jumper.ONE || configureBed.getconfig() == ConfigureBed.Jumper.THREE) {
      //System.out.println("this is a test; 1");
      frontLeft = new CANSparkMax(RobotMap.frontLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      frontRight = new CANSparkMax(RobotMap.frontRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      backLeft = new CANSparkMax(RobotMap.backLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      backRight = new CANSparkMax(RobotMap.backRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

    }
    else if (configureBed.getconfig() == ConfigureBed.Jumper.TWO) {
      //System.out.println("this is a test; 2");
      frontLeft = new CANSparkMax(RobotMap.frontLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);
      frontRight = new CANSparkMax(RobotMap.frontRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);
      backLeft = new CANSparkMax(RobotMap.backLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);
      backRight = new CANSparkMax(RobotMap.backRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushed);

    }
    else {
      System.out.println("An error has occurred with the jumpers");
      //System.exit(-1);
      frontLeft = new CANSparkMax(RobotMap.frontLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      frontRight = new CANSparkMax(RobotMap.frontRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      backLeft = new CANSparkMax(RobotMap.backLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
      backRight = new CANSparkMax(RobotMap.backRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    SM.initializeSparkDefaults(frontLeft, frontRight);

    backLeft.follow(frontLeft);
    backRight.follow(frontRight);

    roboDrive = new DifferentialDrive(frontLeft, frontRight);

    roboDrive.setDeadband(RobotMap.DEADBAND);

    roboDrive.setMaxOutput(REGULAR_SPEED);

    encoder1 = frontLeft.getEncoder();
    encoder2 = backRight.getEncoder();
    encoder3 = frontRight.getEncoder();
    encoder4 = frontLeft.getEncoder();
  }

  private CANEncoder getEncoder(int number) {
    if (number == 1) {
      return encoder1;
    }
    else if (number == 2) {
      return encoder2;
    }
    else if (number == 3) {
      return encoder3;
    }
    else if (number == 4) {
      return encoder4;
    }
    else return null;
  }

  public double encoderDistance(int encoder, double old_E_Value[]) {
    double current_E_Value = getEncoder(encoder).getPosition();
    double traveledUnits = (current_E_Value - old_E_Value[0]);
    double traveledInches = toInches(traveledUnits);
    old_E_Value[0] = current_E_Value;
    System.out.println();
    System.out.println("Traveled " + traveledInches + "Inches");
    return traveledInches;
  }

  private double toInches(double encoderValue)  {
    return (encoderValue * RobotMap.getEncoderConstant());
  }

  public double toFeet(double inches) {
    return inches / 12;
  }

  public double slewLimit(double target, double current, double increment) {
    return SM.slewLimit(target, current, increment);
  }

  public void bradfordDrive(double speed, double turn) {
    roboDrive.arcadeDrive(speed,turn,true);
  }

  public void arcadeDrive(double speed, double turn) {
    roboDrive.arcadeDrive(speed,turn,true);
  }

  public void tankDrive(double leftspeed, double rightspeed) {
    roboDrive.tankDrive(leftspeed,rightspeed,true);
  }

  public void stopDrive() {
    roboDrive.stopMotor();
  }
}
