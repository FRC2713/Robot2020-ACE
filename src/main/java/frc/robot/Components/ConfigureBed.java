package frc.robot.Components;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.ACE.Component;
import frc.robot.RobotMap;

public class ConfigureBed extends Component {

  //Creates jumpers
  private DigitalInput configOne;
  private DigitalInput configTwo;

  public ConfigureBed() {
    setIsActiveForTeleOp();
    setIsActiveForAutonomous();
    setComponentIsPrimaryForInput();
  }

  @Override
  public void initialize() {
     configOne = new DigitalInput(RobotMap.configOnePort);
     configTwo = new DigitalInput(RobotMap.configTwoPort);
  }

  public enum Jumper{
    ONE, TWO, THREE, FOUR // one is last years robot, two is test bot, three is this years robot, four is fail
  }

  public Jumper getconfig() {

    Jumper config = Jumper.FOUR;

    if(configOne.get() == false && configTwo.get() == true) {
      config = Jumper.ONE;

    }
    else if(configTwo.get() == false && configOne.get() == true){
      config = Jumper.TWO;

    }
    else if(configTwo.get() == false && configOne.get() == false){
      config = Jumper.THREE;

    }
    return config;

  }

}
