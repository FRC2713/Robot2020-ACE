package frc.robot.ACE;

import frc.robot.ACE.Foundation.ACEBase;
import frc.robot.ACE.Foundation.RobotManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class Actions extends ACEBase {

  private ActionGroup actionGroup = new ActionGroup(this);
  private boolean are_actions_done = false;

  private class ActionGroup {
    private final ArrayList<Actions> actionGroup = new ArrayList<>();
    private final Map<String, ActionGroup> actionGroupMap = new LinkedHashMap<>();
    private boolean is_action_group_done = false;
    private final Actions managerActions;
    private Actions nowActions;
    private int index = -1;

    public ActionGroup(Actions manager) {
      managerActions = manager;
    }

    private Actions verifyActions(Actions actions) {
      return RobotManager.verifyActions(managerActions, actions);
    }

    public boolean isActionGroupDone() {
      return is_action_group_done;
    }

    public ActionGroup get(String groupName) {
      if (groupName == null) {
        return this;
      }
      if (!actionGroupMap.containsKey(groupName)) {
        actionGroupMap.put(groupName, new ActionGroup(managerActions));
      }
      return actionGroupMap.get(groupName);
    }

    public void resetActionGroup() {
      for (Actions actions : actionGroup) {
        actions.doInterruptActions();
      }
      for (ActionGroup actionGroup : actionGroupMap.values()) {
        actionGroup.resetActionGroup();
      }
      is_action_group_done = false;
      nowActions = null;
      index = -1;
    }

    public void runActionGroup() {
      if (nowActions == null || nowActions.AreActionsDone()) {
        Actions nextActions;
        try {
          nextActions = actionGroup.get(++index);
        } catch (IndexOutOfBoundsException e) {
          is_action_group_done = true;
          nextActions = null;
          index--;
        }
        if (nextActions != null) nowActions = nextActions;
      }
     if (nowActions != null) nowActions.runActions();
    }

    public Actions addAction(Supplier<Actions> actions) {
      Actions verifiedActions = verifyActions(actions.get());
      actionGroup.add(verifiedActions);
      return verifiedActions;
    }
  }

  public abstract void runActions();

  public void interruptActions() {
  }

  public void resetActions() {
  }

  public final void doInterruptActions() {
    interruptActions();
    resetActions();
    are_actions_done = false;
    actionGroup.resetActionGroup();
  }

  private boolean AreActionsDone() {
    return are_actions_done;
  }

  public void setAreActionsDone(boolean done) {
    are_actions_done = done;
  }

  public boolean isActionGroupDone() {
    return isActionGroupDone(null);
  }

  public boolean isActionGroupDone(String groupName) {
    return actionGroup.get(groupName).isActionGroupDone();
  }

  protected final <T> T addActions(Supplier<T> actions) {
    return addActions(actions,null);
  }

  @SuppressWarnings("unchecked")
  protected final <T> T addActions(Supplier<T> actions,String groupName) {
    return (T) actionGroup.get(groupName).addAction((Supplier<Actions>) actions) ;
  }

  protected final void runActionGroup() {
    runActionGroup(null);
  }

  protected final void runActionGroup(String groupName) {
    actionGroup.get(groupName).runActionGroup();
  }

  protected final Events getEvents(String name) {
    return RobotManager.getEvents(this, name);
  }

  protected final Component getComponent(String name) {
    return getComponent(2, name);
  }
}
