package frc.robot.Events;

import frc.robot.Components.ConfigureBed;
import frc.robot.ACE.Events;

public class ConfigEvents extends Events {

  private ConfigureBed configureBed;

  public ConfigEvents() {
    setIsActiveForTeleOp();
    setIsActiveForAutonomous();
    addRequiredComponent(ConfigureBed.class);
  }

  @Override
  public void initialize() {
    configureBed = (ConfigureBed) getComponent("ConfigureBed");
  }

  public Config getConfig() {

    if ( configureBed.getconfig() == ConfigureBed.Jumper.ONE ) {
      return Config.LYR;
    }

    if ( configureBed.getconfig() == ConfigureBed.Jumper.TWO ) {
      return Config.TR;
    }

    if ( configureBed.getconfig() == ConfigureBed.Jumper.THREE ) {
      return Config.CYR;
    }

    return Config.ERROR;
  }

  public enum Config {
    ERROR,
    CYR,
    LYR,
    TR,
  }
}
