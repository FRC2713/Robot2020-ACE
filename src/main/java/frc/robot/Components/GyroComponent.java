package frc.robot.Components;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import frc.robot.ACE.ACE.Component;

public class GyroComponent extends Component {

  private ADXRS450_Gyro gyro;

  public GyroComponent() {
    setIsActiveForAutonomous();
  }

  @Override
  public void initialize() {
    gyro = new ADXRS450_Gyro();
    calibrate();
  }

  private void calibrate() {
    new Thread(() -> {
      gyro.calibrate();
    }).start();
  }

  public double getAngle() {
    return gyro.getAngle();
  }

}
