package frc.robot.Components;

import edu.wpi.first.wpilibj.Compressor;
import frc.robot.ACE.Component;

public class CompressorComponent extends Component {

  public Compressor compressor;

  public CompressorComponent() {
    setIsActiveForTeleOp();
    setIsActiveForDisabled();
    setIsActiveForAutonomous();
    setComponentIsPrimaryForOutput();
  }

  @Override
  public void initialize() {
    compressor = new Compressor();
    compressor.start();
  }
}
