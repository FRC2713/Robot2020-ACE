package frc.robot;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;

/**
 * System Module - Class for utility methods.
 **/

public class SM {

  public static void initializeSparkDefaults(CANSparkMax... sparks) {
    for (CANSparkMax spark : sparks) {
      spark.setSmartCurrentLimit(RobotMap.MAX_MOTOR_STALL_AMPS, RobotMap.MAX_MOTOR_FREE_AMPS);

    }
  }

  /**
   * Given a target number, current number, and increment, adjust current number by increment until we reach target
   * This is useful particularly in {@link frc.robot.Actions.DriveActions} where we need to ramp up to user input to avoid jerkiness
   *
   * @param target    The number you eventually want to get to (ie. joystick speed)
   * @param current   The current number you are at (so we know what to start at for the increment)
   * @param increment How much to increase current by until current = target
   * @return Adjusted target
   * @see <a href="https://en.wikipedia.org/wiki/Slew_rate">Wikipedia article on Slew rates</a>
   */
  public static double slewLimit(double target, double current, double increment) {
    increment = Math.abs(increment); // Professionally validating user input right here 👌
    double change = target - current;
    if (Math.abs(current) > Math.abs(target)) return target; // Always slow down immediately for safety concerns
    if (change > increment) {
      change = increment;
    } else if (change < -increment) {
      change = -increment;
    }
    return current + change;
  }


  /**
   * Rumbles a given controller for a specified time
   *
   * @param stick Controller to rumble
   * @param ms    Time in Milliseconds
   */
  public static void rumbleController(GenericHID stick, double intensity, int ms) {
    rumbleController(stick, intensity, ms, GenericHID.RumbleType.kLeftRumble);
  }

  /**
   * Rumbles a given controller for a specified time
   * Left rumble is like an earthquake, right rumble is like a vibrating toothbrush
   *
   * @param stick      Controller to rumble
   * @param ms         Time in Milliseconds
   * @param rumbleType Type of rumble to use
   */
  public static void rumbleController(GenericHID stick, double intensity, int ms, GenericHID.RumbleType rumbleType) {
    if (ms > 0) {
      new Thread(() -> {
        _setRumble(stick, intensity, rumbleType);
        try {
          Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
        _setRumble(stick, 0, rumbleType);
      }).start();
    } else {
      _setRumble(stick, intensity, rumbleType);
    }
  }

  private static void _setRumble(GenericHID stick, double intensity, GenericHID.RumbleType rumbleType) {
    stick.setRumble(rumbleType, intensity);
  }

  /**
   * Returns the speed, corrected for the deadband. This is used usually when getting
   * speed inputs from a Joystick, as joysticks usually report values slightly
   * different then what is intended
   *
   * @param speed             The current desired speed (usually from the joystick)
   * @param deadbandTolerance The amount of deadband to remove from speed
   * @return The corrected speed
   */
  public static double getDeadband(double speed, double deadbandTolerance) {
    return Math.max(0, // If deadband is greater than abs(speed), do nothing
      Math.abs(speed) - Math.max(deadbandTolerance, 0) // Subtract abs(speed) from larger of deadbandTolerance and 0
    ) * Math.signum(speed); // Restore original sign sign of speed
  }

  public static DoubleSolenoid getDoubleSolenoid(int forwardChannel, int reverseChannel) {
    if (reverseChannel > 7) {
      return new DoubleSolenoid(1, forwardChannel - 8, reverseChannel - 8);
    }
    return new DoubleSolenoid(forwardChannel, reverseChannel);
  }
}
