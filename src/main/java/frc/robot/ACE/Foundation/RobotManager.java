package frc.robot.ACE.Foundation;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.ACE.Actions;
import frc.robot.ACE.Component;
import frc.robot.ACE.Events;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;

public class RobotManager extends TimedRobot {

  private static RobotManager defaultInstance = null;

  private static Supplier<?> m_robotContainer;

  private static final Map<String, Class<? extends Events>> events_class_map = new LinkedHashMap<>();
  private static final Map<String, Class<? extends Actions>> actions_class_map = new LinkedHashMap<>();
  private static final SuperMap<String, Class<? extends Component>> component_class_map = new SuperMap<>();

  private final Map<String, Events> events_map = new LinkedHashMap<>();
  private final Map<String, Actions> actions_map = new LinkedHashMap<>();
  private final Map<String, Component> component_map = new LinkedHashMap<>();

  private interface ForEachFunction<V> {
    void run(V value);
  }

  private static class SuperMap<K, V> implements Map<K, V> {

    private ArrayList<Map<K, V>> mapArray = new ArrayList<>();
    private Map<K, V> foundation = new LinkedHashMap<>();
    private int index = 0;

    public SuperMap() {
      mapArray.add(new LinkedHashMap<K, V>());
    }

    @Override
    public int size() {
      return foundation.size();
    }

    @Override
    public boolean isEmpty() {
      return foundation.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
      return foundation.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
      return foundation.containsValue(value);
    }

    @Override
    public V get(Object key) {
      return mapArray.get(index).get(key);
    }

    @Override
    public V put(K key, V value) {
      V retval = mapArray.get(index).put(key, value);
      foundation.put(key, value);
      return retval;
    }

    @Override
    public V remove(Object key) {
      V retval = mapArray.get(index).remove(key);
      foundation.remove(key);
      return retval;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
      for (Map<K, V> map : mapArray) {
        map.clear();
      }
      mapArray.clear();
      foundation.clear();
      index = 0;
    }

    @Override
    public Set<K> keySet() {
      return foundation.keySet();
    }

    @Override
    public Collection<V> values() {
      Map<K, V> thisMap = mapArray.get(index);
      index++;
      if (index >= mapArray.size())
        mapArray.add(new LinkedHashMap<K, V>());
      return thisMap.values();
    }

    public void welcomeBack() {
      index--;
    }

    public void forEach(ForEachFunction<V> function) {
      for (V value : values()) {
        function.run(value);
      }
      if (!mapArray.get(index).isEmpty()) forEach(function);
      welcomeBack();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
      return foundation.entrySet();
    }
  }


  private static synchronized void setDefaultInstance(RobotManager robotManager) {
    defaultInstance = robotManager;
  }

  public static void startRobotWithContainer(Supplier<?> robotContainer) {
    m_robotContainer = robotContainer;
    RobotBase.startRobot(RobotManager::new);
  }

  public static synchronized void addPeriodicCallback(Runnable callback, double periodSeconds) {
    defaultInstance.addPeriodic(callback, periodSeconds);
  }

  public static synchronized void addPeriodicCallback(Runnable callback, double periodSeconds, double offsetSeconds) {
    defaultInstance.addPeriodic(callback, periodSeconds, offsetSeconds);
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

  public static synchronized Actions verifyActions(Actions manager, Actions actions) {
    if (manager.getIsActiveForAutonomous() && !actions.getIsActiveForAutonomous()) {
      throw new IllegalArgumentException("actionGroup actions are not active for: " + "Autonomous");
    }

    if (manager.getIsActiveForTeleOp() && !actions.getIsActiveForTeleOp()) {
      throw new IllegalArgumentException("actionGroup actions are not active for: " + "TeleOp");
    }

    if (manager.getIsActiveForDisabled() && !actions.getIsActiveForDisabled()) {
      throw new IllegalArgumentException("actionGroup actions are not active for: " + "Disabled");
    }

    if (manager.getIsActiveForTest() && !actions.getIsActiveForTest()) {
      throw new IllegalArgumentException("actionGroup actions are not active for: " + "Test");
    }

    if (manager.getIsActiveForPeriodic() && !actions.getIsActiveForPeriodic()) {
      throw new IllegalArgumentException("actionGroup actions are not active for: " + "Periodic");
    }

    if (manager.getIsActiveForSimulation() && !actions.getIsActiveForSimulation()) {
      throw new IllegalArgumentException("actionGroup actions are not active for: " + "Simulation");
    }
    actions.doInitialization();
    return actions;
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

    if (getter.getIsActiveForSimulation() && !events.getIsActiveForSimulation()) {
      throw new IllegalArgumentException("Events are not active for: " + "Simulation");
    }

    return events;
  }

  public static synchronized Component getComponent(int type, ACEBase getter, String name) {
    Component component = RobotManager.getComponent(getter, name);
    if (type == 1 && component.getComponentIsPrimaryForOutput()) {
      throw new IllegalArgumentException("Events can not use components marked as 'PrimaryForOutput'");
    }
    if (type == 2 && component.getComponentIsPrimaryForInput()) {
      throw new IllegalArgumentException("Actions can not use components marked as 'PrimaryForInput'");
    }
    return component;
  }

  private static synchronized Component getComponent(ACEBase getter, String name) {
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

    if (getter.getIsActiveForSimulation() && !component.getIsActiveForSimulation()) {
      throw new IllegalArgumentException("Component is not active for: " + "Simulation");
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

    m_robotContainer.get();

    for (Class<? extends Events> events_class : events_class_map.values()) {
      Events events = (Events) newObject(events_class);
      events_map.put(getClassName(events.getClass().getName()), events);
    }

    for (Class<? extends Actions> actions_class : actions_class_map.values()) {
      Actions actions = (Actions) newObject(actions_class);
      actions_map.put(getClassName(actions.getClass().getName()), actions);
    }

    component_class_map.forEach((Class<? extends Component> component_class) -> {
      Component component = (Component) newObject(component_class);
      component_map.put(getClassName(component.getClass().getName()), component);
    });

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
      case 5:
        return object.getIsActiveForSimulation();
    }
  }

  private void runInit(int mode) {
    //make sure components know mode
    for (Component component : component_map.values()) {
      component.setMode(mode);
    }
    for (Events events : events_map.values()) {
      events.setMode(mode);
      if (!IsActiveForMode(events, mode)) events.resetEvents();
    }
    for (Actions actions : actions_map.values()) {
      actions.setMode(mode);
      if (!IsActiveForMode(actions, mode)) actions.doInterruptActions();
    }
  }

  private void runPeriodic(int mode) {
    //make sure components know mode
    for (Component component : component_map.values()) {
      component.setMode(mode);
    }
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

  @Override
  public void simulationInit() {
    runInit(5);
  }

  @Override
  public void simulationPeriodic() {
    runPeriodic(5);
  }
}
