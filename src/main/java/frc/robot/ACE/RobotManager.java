package frc.robot.ACE;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.RobotContainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RobotManager extends TimedRobot {

  private static RobotManager defaultInstance = null;

  private static final Map<String, Class<? extends Events>> events_class_map = new LinkedHashMap<>();
  private static final Map<String, Class<? extends Actions>> actions_class_map = new LinkedHashMap<>();
  private static final Map<String, Class<? extends Component>> component_class_map = new LinkedHashMap<>();

  private final Map<String, Events> events_map = new LinkedHashMap<>();
  private final Map<String, Actions> actions_map = new LinkedHashMap<>();
  private final Map<String, Component> component_map = new LinkedHashMap<>();

  private static synchronized void setDefaultInstance(RobotManager robotManager) {
    defaultInstance = robotManager;
  }

  public static synchronized void addEvents(Class<? extends Events> events) {
    String name = defaultInstance.getClassName(events.getName());
    if (!events_class_map.containsKey(name)) {
      events_class_map.put(name, events);
    }
  }

  public static synchronized void addActions(Class<? extends Actions> actions) {
    String name = defaultInstance.getClassName(actions.getName());
    if (!actions_class_map.containsKey(name)) {
      actions_class_map.put(name, actions);
    }
  }

  public static synchronized void addComponent(Class<? extends Component> component) {
    String name = defaultInstance.getClassName(component.getName());
    if (!component_class_map.containsKey(name)) {
      component_class_map.put(name, component);
    }
  }

  public static synchronized Actions spawnActions(Actions spawner, Class<? extends Actions> actions) {
    Actions a = (Actions) defaultInstance.newObject(actions);
    if (spawner.getIsActiveForAutonomous() && !a.getIsActiveForAutonomous()) {
      throw new IllegalArgumentException("Spawned actions are not active for: " + "Autonomous");
    }

    if (spawner.getIsActiveForTeleOp() && !a.getIsActiveForTeleOp()) {
      throw new IllegalArgumentException("Spawned actions are not active for: " + "TeleOp");
    }

    if (spawner.getIsActiveForDisabled() && !a.getIsActiveForDisabled()) {
      throw new IllegalArgumentException("Spawned actions are not active for: " + "Disabled");
    }

    if (spawner.getIsActiveForTest() && !a.getIsActiveForTest()) {
      throw new IllegalArgumentException("Spawned actions are not active for: " + "Test");
    }

    if (spawner.getIsActiveForPeriodic() && !a.getIsActiveForPeriodic()) {
      throw new IllegalArgumentException("Spawned actions are not active for: " + "Periodic");
    }
    a.doInitialization();
    return a;
  }

  public static synchronized Events getEvents(ACEBase getter, String name) {
    if (!defaultInstance.events_map.containsKey(name)) {
      throw new IllegalArgumentException("No Events have the name of: " + name);
    }
    Events events = defaultInstance.events_map.get(name);
    if (getter.getIsActiveForAutonomous() && !events.getIsActiveForAutonomous()) {
      throw new IllegalArgumentException("Events are not active for: " + "Autonomous");
    }

    if (getter.getIsActiveForTeleOp() && !events.getIsActiveForTeleOp()) {
      throw new IllegalArgumentException("Events are not active for: " + "TeleOp");
    }

    if (getter.getIsActiveForDisabled() && !events.getIsActiveForDisabled()) {
      throw new IllegalArgumentException("Events are not active for: " + "Disabled");
    }

    if (getter.getIsActiveForTest() && !events.getIsActiveForTest()) {
      throw new IllegalArgumentException("Events are not active for: " + "Test");
    }

    if (getter.getIsActiveForPeriodic() && !events.getIsActiveForPeriodic()) {
      throw new IllegalArgumentException("Events are not active for: " + "Periodic");
    }
    return events;
  }

  public static synchronized Component getComponent(ACEBase getter, String name) {
    if (!defaultInstance.component_map.containsKey(name)) {
      throw new IllegalArgumentException("No Component has the name of: " + name);
    }
    Component component = defaultInstance.component_map.get(name);
    if (getter.getIsActiveForAutonomous() && !component.getIsActiveForAutonomous()) {
      throw new IllegalArgumentException("Component is not active for: " + "Autonomous");
    }

    if (getter.getIsActiveForTeleOp() && !component.getIsActiveForTeleOp()) {
      throw new IllegalArgumentException("Component is not active for: " + "TeleOp");
    }

    if (getter.getIsActiveForDisabled() && !component.getIsActiveForDisabled()) {
      throw new IllegalArgumentException("Component is not active for: " + "Disabled");
    }

    if (getter.getIsActiveForTest() && !component.getIsActiveForTest()) {
      throw new IllegalArgumentException("Component is not active for: " + "Test");
    }

    if (getter.getIsActiveForPeriodic() && !component.getIsActiveForPeriodic()) {
      throw new IllegalArgumentException("Component is not active for: " + "Periodic");
    }
    component.doInitialization();
    return component;
  }

  private ACEBase newObject(Class<? extends ACEBase> cls) {
    ACEBase object = null;
    Constructor<? extends ACEBase> ctor = null;
    if (cls == null)
      throw new IllegalArgumentException("ACE Class is null!");
    try {
      ctor = cls.getConstructor();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    try {
      if (ctor == null)
        throw new IllegalAccessException("ctor is null!");
      object = ctor.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return object;
  }

  private String getClassName(String baseName) {
    int i = 0;
    int j = 0;
    int x = 0;
    String className = "";
    while (i < baseName.length()) {
      if (baseName.charAt(i) == '.') j++;
      i++;
    }
    x = j;
    i = 0;
    j = 0;
    while (i < baseName.length()) {
      if (baseName.charAt(i) == '.') j++;
      i++;
      if (j == x) break;
    }
    while (i < baseName.length()) {
      className += baseName.charAt(i);
      i++;
    }
    return className;
  }

  @Override
  public void robotInit() {
    setDefaultInstance(this);

    new RobotContainer();

    for (Class<? extends Events> events_class : events_class_map.values()) {
      Events events = (Events) newObject(events_class);
      events_map.put(getClassName(events.getClass().getName()), events);
    }

    for (Class<? extends Actions> actions_class : actions_class_map.values()) {
      Actions actions = (Actions) newObject(actions_class);
      actions_map.put(getClassName(actions.getClass().getName()), actions);
    }

    for (Class<? extends Component> component_class : component_class_map.values()) {
      Component component = (Component) newObject(component_class);
      component_map.put(getClassName(component.getClass().getName()), component);
    }

    for (Component component : component_map.values()) {
      component.doInitialization();
    }
    for (Events events : events_map.values()) {
      events.doInitialization();
    }
    for (Actions actions : actions_map.values()) {
      actions.doInitialization();
    }

  }

  private boolean IsActiveForMode(ACEBase object, int mode) {
    switch (mode) {
      default:
      case 0:
        return object.getIsActiveForAutonomous();
      case 1:
        return object.getIsActiveForTeleOp();
      case 2:
        return object.getIsActiveForDisabled();
      case 3:
        return object.getIsActiveForTest();
      case 4:
        return object.getIsActiveForPeriodic();
    }
  }

  private void runInit(int mode) {
    for (Actions actions : actions_map.values()) {
      actions.setMode(mode);
      if (!IsActiveForMode(actions, mode)) actions.doInterruptActions();
    }
  }

  private void runPeriodic(int mode) {
    for (Events events : events_map.values()) {
      events.setMode(mode);
      if (IsActiveForMode(events, mode)) events.pollEvents();
    }
    for (Actions actions : actions_map.values()) {
      actions.setMode(mode);
      if (IsActiveForMode(actions, mode)) actions.runActions();
    }
  }

  @Override
  public void autonomousInit() {
    runInit(0);
  }

  @Override
  public void autonomousPeriodic() {
    runPeriodic(0);
  }

  @Override
  public void teleopInit() {
    runInit(1);
  }

  @Override
  public void teleopPeriodic() {
    runPeriodic(1);
  }

  @Override
  public void disabledInit() {
    runInit(2);
  }

  @Override
  public void disabledPeriodic() {
    runPeriodic(2);
  }

  @Override
  public void testInit() {
    runInit(3);
  }

  @Override
  public void testPeriodic() {
    runPeriodic(3);
  }

  @Override
  public void robotPeriodic() {
    runPeriodic(4);
  }
}
