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

  private static void setDefaultInstance(RobotManager robotManager) {
    defaultInstance = robotManager;
  }

  public static void addEvents(Class<? extends Events> events) {
    if (!events_class_map.containsKey(events.getName())) {
      events_class_map.put(events.getName(), events);
    }
  }

  public static void addActions(Class<? extends Actions> actions) {
    if (!actions_class_map.containsKey(actions.getName())) {
      actions_class_map.put(actions.getName(), actions);
    }
  }

  public static void addComponents(Class<? extends Components> components) {
    if (!components_class_map.containsKey(components.getName())) {
      components_class_map.put(components.getName(), components);
    }
  }

  public static Actions spawnActions(Actions spawner, Class<? extends Actions> actions) {
    Actions a = (Actions) defaultInstance.newObject(actions);
    if (spawner.getIsActiveForAutonomous() && !a.getIsActiveForAutonomous()) {
      throw new IllegalArgumentException("Spawned actions are not active for : " + "Autonomous");
    }

    if (spawner.getIsActiveForTeleOp() && !a.getIsActiveForTeleOp()) {
      throw new IllegalArgumentException("Spawned actions are not active for : " + "TeleOp");
    }

    if (spawner.getIsActiveForDisabled() && !a.getIsActiveForDisabled()) {
      throw new IllegalArgumentException("Spawned actions are not active for : " + "Disabled");
    }

    if (spawner.getIsActiveForTest() && !a.getIsActiveForTest()) {
      throw new IllegalArgumentException("Spawned actions are not active for : " + "Test");
    }

    if (spawner.getIsActiveForPeriodic() && !a.getIsActiveForPeriodic()) {
      throw new IllegalArgumentException("Spawned actions are not active for : " + "Periodic");
    }
    a.doInitialization();
    return a;
  }

  public static Events getEvents(EACBase getter, String name) {
    Events events = defaultInstance.events_map.get(name);
    if (getter.getIsActiveForAutonomous() && !events.getIsActiveForAutonomous()) {
      throw new IllegalArgumentException("Events are not active for : " + "Autonomous");
    }

    if (getter.getIsActiveForTeleOp() && !events.getIsActiveForTeleOp()) {
      throw new IllegalArgumentException("Events are not active for : " + "TeleOp");
    }

    if (getter.getIsActiveForDisabled() && !events.getIsActiveForDisabled()) {
      throw new IllegalArgumentException("Events are not active for : " + "Disabled");
    }

    if (getter.getIsActiveForTest() && !events.getIsActiveForTest()) {
      throw new IllegalArgumentException("Events are not active for : " + "Test");
    }

    if (getter.getIsActiveForPeriodic() && !events.getIsActiveForPeriodic()) {
      throw new IllegalArgumentException("Events are not active for : " + "Periodic");
    }
    return events;
  }

  public static Components getComponents(EACBase getter, String name) {
    Components components = defaultInstance.components_map.get(name);
    if (getter.getIsActiveForAutonomous() && !components.getIsActiveForAutonomous()) {
      throw new IllegalArgumentException("Component is not active for : " + "Autonomous");
    }

    if (getter.getIsActiveForTeleOp() && !components.getIsActiveForTeleOp()) {
      throw new IllegalArgumentException("Component is not active for : " + "TeleOp");
    }

    if (getter.getIsActiveForDisabled() && !components.getIsActiveForDisabled()) {
      throw new IllegalArgumentException("Component is not active for : " + "Disabled");
    }

    if (getter.getIsActiveForTest() && !components.getIsActiveForTest()) {
      throw new IllegalArgumentException("Component is not active for : " + "Test");
    }

    if (getter.getIsActiveForPeriodic() && !components.getIsActiveForPeriodic()) {
      throw new IllegalArgumentException("Component is not active for : " + "Periodic");
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
      if (ctor == null) return null;
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

  @Override
  public void autonomousInit() {
    for (Actions actions : actions_map.values()) {
      if (!actions.getIsActiveForAutonomous()) actions.doInterruptActions();
    }
  }

  @Override
  public void autonomousPeriodic() {
    for (Events events : events_map.values()) {
      if (events.getIsActiveForAutonomous()) events.pollEvents();
    }
    for (Actions actions : actions_map.values()) {
      if (actions.getIsActiveForAutonomous()) actions.runActions();
    }
  }

  @Override
  public void teleopInit() {
    for (Actions actions : actions_map.values()) {
      if (!actions.getIsActiveForTeleOp()) actions.doInterruptActions();
    }
  }

  @Override
  public void teleopPeriodic() {
    for (Events events : events_map.values()) {
      if (events.getIsActiveForTeleOp()) events.pollEvents();
    }
    for (Actions actions : actions_map.values()) {
      if (actions.getIsActiveForTeleOp()) actions.runActions();
    }
  }

  @Override
  public void disabledInit() {
    for (Actions actions : actions_map.values()) {
      if (!actions.getIsActiveForDisabled()) actions.doInterruptActions();
    }
  }

  @Override
  public void disabledPeriodic() {
    for (Events events : events_map.values()) {
      if (events.getIsActiveForDisabled()) events.pollEvents();
    }
    for (Actions actions : actions_map.values()) {
      if (actions.getIsActiveForDisabled()) actions.runActions();
    }
  }

  @Override
  public void testInit() {
    for (Actions actions : actions_map.values()) {
      if (!actions.getIsActiveForTest()) actions.doInterruptActions();
    }
  }

  @Override
  public void testPeriodic() {
    for (Events events : events_map.values()) {
      if (events.getIsActiveForTest()) events.pollEvents();
    }
    for (Actions actions : actions_map.values()) {
      if (actions.getIsActiveForTest()) actions.runActions();
    }
  }

  @Override
  public void robotPeriodic() {
    for (Events events : events_map.values()) {
      if (events.getIsActiveForPeriodic()) events.pollEvents();
    }
    for (Actions actions : actions_map.values()) {
      if (actions.getIsActiveForPeriodic()) actions.runActions();
    }
  }
}
