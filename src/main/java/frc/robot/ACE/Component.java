package frc.robot.ACE;

public abstract class Component extends ACEBase {
  private int component_type = 0;

  protected final void setComponentIsPrimaryForInput() {
    component_type = 1;
  }

  protected final void setComponentIsPrimaryForOutput() {
    component_type = 2;
  }

  protected final boolean getComponentIsPrimaryForInput() {
    return component_type == 1 ? true : false;
  }

  protected final boolean getComponentIsPrimaryForOutput() {
    return component_type == 2 ? true : false;
  }

  protected final Component getComponent(String name) {
    return getComponent(0, name);
  }

}
