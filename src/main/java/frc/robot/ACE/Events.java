package frc.robot.ACE;

import frc.robot.ACE.Foundation.ACEBase;

public abstract class Events extends ACEBase {

  public void pollEvents() {
  }

  public void resetEvents() {
  }

  protected final Component getComponent(String name) {
    return getComponent(1, name);
  }
}
