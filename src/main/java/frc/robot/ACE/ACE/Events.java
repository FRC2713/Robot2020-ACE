package frc.robot.ACE.ACE;

import frc.robot.ACE.Base.ACEBase;
import frc.robot.ACE.ACE.Component;

public abstract class Events extends ACEBase {

  public void pollEvents() {
  }

  public void resetEvents() {
  }

  protected final Component getComponent(String name) {
    return getComponent(1, name);
  }
}
