package frc.robot.ACE.ACE;

import frc.robot.ACE.Base.ACEBase;
import frc.robot.ACE.Manager.RobotManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class Actions extends ACEBase {

  public abstract void runActions();

  public abstract void interruptActions();

  private ArrayList<Actions> actionGroup = new ArrayList<>();
  private Map<String, ArrayList<Actions>> actionGroupMap = new LinkedHashMap<>();
  private boolean are_actions_done = false;
  private Actions nowActions;
  private Actions nextActions;
  private int index = -1;

  public void resetActions() {
  }

  private ArrayList<Actions> get_action_group(String groupName) {
    if (groupName == null) {
      return actionGroup;
    }
    if (!actionGroupMap.containsKey(groupName)) {
      actionGroupMap.put(groupName, new ArrayList<>());
    }
    return actionGroupMap.get(groupName);
  }

  public final void doInterruptActions() {
    interruptActions();
    resetActions();
    resetActionGroup();
    are_actions_done = false;
  }

  private boolean AreActionsDone() {
    return are_actions_done;
  }

  public void setAreActionsDone(boolean done) {
    are_actions_done = done;
  }

  protected final <T> T addActions(Supplier<T> actions) {
    return addActions(actions,null);
  }

  @SuppressWarnings("unchecked")
  protected final <T> T addActions(Supplier<T> actions,String groupName) {
    return (T) add_the_actions((Supplier<Actions>) actions,groupName);
  }

  private Actions add_the_actions(Supplier<Actions> actions,String groupName) {
    ArrayList<Actions> actionGroup = get_action_group(groupName);
    Actions a = actions.get();
    a.doInitialization();
    actionGroup.add(a);
    return a;
  }
  protected final void runActionGroup() {
    runActionGroup(null);
  }

  protected final void runActionGroup(String groupName) {
    ArrayList<Actions> actionGroup = get_action_group(groupName);
    if (nowActions == null || nowActions.AreActionsDone()) {
      try {
        nextActions = actionGroup.get(++index);
      } catch (IndexOutOfBoundsException e) {
        nextActions = null;
      }
      if (nextActions != null) nowActions = nextActions;
    }
    nowActions.runActions();
  }

  private void resetActionGroup() {
    for (Actions actions : actionGroup) {
      actions.doInterruptActions();
    }
    for (ArrayList<Actions> actionGroup : actionGroupMap.values()) {
      for (Actions actions : actionGroup) {
        actions.doInterruptActions();
      }
    }
    nowActions = null;
    index = -1;
  }

  protected final Events getEvents(String name) {
    return RobotManager.getEvents(this, name);
  }

  protected final Actions manageActions(Class<? extends Actions> actions) {
    return RobotManager.manageActions(this, actions);
  }

  protected final Component getComponent(String name) {
    return getComponent(2, name);
  }
}
