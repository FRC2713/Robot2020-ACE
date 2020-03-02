package frc.robot.EAC;

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
  private static final Map<String, Class<? extends Components>> components_class_map = new LinkedHashMap<>();

  private final Map<String, Events> events_map = new LinkedHashMap<>();
  private final Map<String, Actions> actions_map = new LinkedHashMap<>();
  private final Map<String, Components> components_map = new LinkedHashMap<>();

  private static synchronized void setDefaultInstance(RobotManager robotManager) {
    defaultInstance = robotManager;
  }

  public static synchronized void addEvents(Class<? extends Events> events) {
    if (!events_class_map.containsKey(events.getName())) {
      events_class_map.put(events.getName(), events);
    }
  }

  public static synchronized void addActions(Class<? extends Actions> actions) {
    if (!actions_class_map.containsKey(actions.getName())) {
      actions_class_map.put(actions.getName(), actions);
    }
  }

  public static synchronized void addComponents(Class<? extends Components> components) {
    if (!components_class_map.containsKey(components.getName())) {
      components_class_map.put(components.getName(), components);
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

  public static synchronized Events getEvents(EACBase getter, String name) {
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

  public static synchronized Components getComponents(EACBase getter, String name) {
    if (!defaultInstance.components_map.containsKey(name)) {
      throw new IllegalArgumentException("No Components have the name of: " + name);
    }
    Components components = defaultInstance.components_map.get(name);
    if (getter.getIsActiveForAutonomous() && !components.getIsActiveForAutonomous()) {
      throw new IllegalArgumentException("Component is not active for: " + "Autonomous");
    }

    if (getter.getIsActiveForTeleOp() && !components.getIsActiveForTeleOp()) {
      throw new IllegalArgumentException("Component is not active for: " + "TeleOp");
    }

    if (getter.getIsActiveForDisabled() && !components.getIsActiveForDisabled()) {
      throw new IllegalArgumentException("Component is not active for: " + "Disabled");
    }

    if (getter.getIsActiveForTest() && !components.getIsActiveForTest()) {
      throw new IllegalArgumentException("Component is not active for: " + "Test");
    }

    if (getter.getIsActiveForPeriodic() && !components.getIsActiveForPeriodic()) {
      throw new IllegalArgumentException("Component is not active for: " + "Periodic");
    }
    components.doInitialization();
    return components;
  }

  private EACBase newObject(Class<? extends EACBase> cls) {
    EACBase object = null;
    Constructor<? extends EACBase> ctor = null;
    if (cls == null)
      throw new IllegalArgumentException("EAC Class is null!");
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

  @Override
  public void robotInit() {
    setDefaultInstance(this);

    new RobotContainer();

    for (Class<? extends Events> events_class : events_class_map.values()) {
      Events events = (Events) newObject(events_class);
      events_map.put(events.getClass().getName(), events);
    }

    for (Class<? extends Actions> actions_class : actions_class_map.values()) {
      Actions actions = (Actions) newObject(actions_class);
      actions_map.put(actions.getClass().getName(), actions);
    }

    for (Class<? extends Components> components_class : components_class_map.values()) {
      Components components = (Components) newObject(components_class);
      components_map.put(components.getClass().getName(), components);
    }

    for (Components components : components_map.values()) {
      components.doInitialization();
    }
    for (Events events : events_map.values()) {
      events.doInitialization();
    }
    for (Actions actions : actions_map.values()) {
      actions.doInitialization();
    }

  }

  private boolean IsActiveForMode(EACBase object, int mode) {
    switch (mode) {
      default:
      case 0: return object.getIsActiveForAutonomous();
      case 1: return object.getIsActiveForTeleOp();
      case 2: return object.getIsActiveForDisabled();
      case 3: return object.getIsActiveForTest();
      case 4: return object.getIsActiveForPeriodic();
    }
  }

  private void runInit(int mode) {
    for (Actions actions : actions_map.values()) {
      actions.setMode(mode);
      if (!IsActiveForMode(actions,mode)) actions.doInterruptActions();
    }
  }

  private void runPeriodic(int mode) {
    for (Events events : events_map.values()) {
      events.setMode(mode);
      if (IsActiveForMode(events,mode)) events.pollEvents();
    }
    for (Actions actions : actions_map.values()) {
      actions.setMode(mode);
      if (IsActiveForMode(actions,mode)) actions.runActions();
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
