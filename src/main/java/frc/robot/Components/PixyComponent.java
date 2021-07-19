package frc.robot.Components;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ACE.ACE.Component;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.links.SPILink;

import java.util.ArrayList;

public class PixyComponent extends Component {

  private Pixy2 pixy;


  public PixyComponent() {
    setIsActiveForTeleOp();
    setIsActiveForAutonomous();
    setComponentIsPrimaryForInput();
  }

  @Override
  public void initialize() {
    pixy = Pixy2.createInstance(new SPILink());
    pixy.init();
  }

  public void setLamp(byte upper, byte lower) {
    pixy.setLamp(upper, lower);
  }

  public void setLED(int r, int g, int b) {
    pixy.setLED(r, g, b);
  }

  public void processBlocks() {
    int blockCount;
    int blockWidth;
    int blockHeight;

    blockCount = pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, 1); // change 1 to 10 or something later
    SmartDashboard.putNumber("Block Count", blockCount);

    ArrayList<Pixy2CCC.Block> blocks = pixy.getCCC().getBlocks();

    if (blocks == null) {
      System.out.println("No blocks");
      return;
    }

    //System.out.println("count :" + blocks.size());

    for (Pixy2CCC.Block block : blocks) {
      blockWidth = block.getWidth();
      blockHeight = block.getHeight();

      System.out.println("Block Width : " + blockWidth); // delete after testing
      System.out.println("Block Height :" + blockHeight); // delete after testing
    }
  }


}
