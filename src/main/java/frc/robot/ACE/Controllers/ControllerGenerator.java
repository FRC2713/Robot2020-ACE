package frc.robot.ACE.Controllers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerGenerator {

  private static final int the_number_of_ports = 6;
  private static int number_of_ports = the_number_of_ports;
  private static Integer controller_ids = 1;
  private static Map<String, ArrayList<Integer>> controller_map = new LinkedHashMap<>();

  private static Controller newObject(Class<? extends Controller> cls) {
    Controller object = null;
    Constructor<? extends Controller> ctor = null;
    if (cls == null)
      throw new IllegalArgumentException("Controller Class is null!");
    try {
      ctor = cls.getConstructor();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    try {
      if (ctor == null)
        throw new IllegalAccessException("Controller ctor is null!");
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

  private static String getControllerName(String baseName) {
    return "Controller#" + controller_ids.toString() + ": " +  baseName ;
  }

  public static void scan() {
    controller_ids = 1;
    number_of_ports = the_number_of_ports;
    controller_map = new LinkedHashMap<>();
    for (int i = 0; i < the_number_of_ports; i++) {
      Joystick test = new Joystick(i);
      if (!controller_map.containsKey(test.getName())) {
        ArrayList<Integer> values = new ArrayList<Integer>();
        controller_map.put(test.getName(), values);
        values.add(i);
      } else {
        ArrayList<Integer> values = controller_map.get(test.getName());
        values.add(i);
      }
    }
  }

  public static Controller generate(Class<? extends Controller> controller_class, String name) {
    Controller controller = newObject(controller_class);
    SmartDashboard.putString(getControllerName(name), "Online.");
    if (controller_map.containsKey(name)) {
      ArrayList<Integer> values = controller_map.get(name);
      if (values.size() > 0 && number_of_ports > 0) {
        controller.initialize(name, values.get(0));
        controller.setActive();
        number_of_ports--;
        values.remove(0);
      } else {
        SmartDashboard.putString(getControllerName(name), "No ports are available.");
      }
    } else {
      SmartDashboard.putString(getControllerName(name), "No controller with that name.");
    }
    controller_ids = controller_ids + 1;
    return controller;
  }

  public static Controller generate(Class<? extends Controller> controller_class, String name, int port) {
    Controller controller = newObject(controller_class);
    SmartDashboard.putString(getControllerName(name), "Online.");
    if (number_of_ports > 0) {
      Joystick test = new Joystick(port);
      if (name.equals(test.getName())) {
        controller.initialize(name, port);
        controller.setActive();
        number_of_ports--;
      } else {
        SmartDashboard.putString(getControllerName(name), "Given port does not match name.");
      }
    } else {
      SmartDashboard.putString(getControllerName(name), "No ports are available.");
    }
    controller_ids = controller_ids + 1;
    return controller;
  }
}
