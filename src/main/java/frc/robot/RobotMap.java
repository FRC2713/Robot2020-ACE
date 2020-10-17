/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.I2C;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class RobotMap {

  // Config
  public static final int MAX_MOTOR_FREE_AMPS = 80;
  public static final int MAX_MOTOR_STALL_AMPS = 40;
  public static final double DEADBAND = 0.04;
  public static final float REGULAR_SPEED = .8f;
  private static final I2C.Port i2cPort = I2C.Port.kOnboard;
  public static double getEncoderConstant() {
    return 1.2;
  }

  //Config Jumpers
  public static final int configOnePort = 0;
  public static final int configTwoPort = 1;
  public static final int configThreePort = 2;

  // Controllers

  //Must be at least the max amount of buttons on any controller
  public static final int default_button_count_for_controller = 25;

  public static final String XBOX_NAME = "Controller (XBOX 360 For Windows)";
  public static final String ARCADE_NAME = "Mayflash Arcade Stick";
  public static final String ATTACK_NAME = "Logitech Attack 3";
  public static final String XBOX2_NAME = "Controller (Gamepad for Xbox 360)";
  public static final String XBOX3_NAME = "Controller (Gamepad)"; //
  public static final String XBOX4_NAME = "Logitech Dual Action";

  //Motors/Solenoids
  public static final int frontLeftMotorPort = 1; //NEEDS TO BE CHANGED WHEN PORTS ARE OFFICIAL
  public static final int backLeftMotorPort = 2; //NEEDS TO BE CHANGED WHEN PORTS ARE OFFICIAL
  public static final int frontRightMotorPort = 3; //NEEDS TO BE CHANGED WHEN PORTS ARE OFFICIAL
  public static final int backRightMotorPort = 4; //NEEDS TO BE CHANGED WHEN PORTS ARE OFFICIAL
  public static final int intakeTalonPort = 6; //NEEDS TO BE CHANGED WHEN PORTS ARE OFFICIAL
  public static final int intakeArmTalonPort = 7; //NEEDS TO BE CHANGED WHEN PORTS ARE OFFICIAL
  public static final int intakeArmUpNode = 4;//CHECK WHAT IS UP/DOWN FOR ARM
  public static final int intakeArmDownNode = 6;
  public static final int IntakeGateUpNode = 5;
  public static final int IntakeGateDownNode = 7;
  public static final int ClimberUpMotorPort = 11;
  public static final int WinchOneMotorPort = 14;
  public static final int WinchTwoMotorPort = 12;


  //Sensors
  public static final int lightSensor = 4; //NEEDS TO BE CHANGED WHEN PORTS ARE OFFICIAL

  //Buttons
  public static final int SwapDriveModeButton = 7;
  public static final int climberButton = 1; //3 on attack
  public static final int winchUpButton = 3;//4 on attack
  public static final int winchDownButton = 2;//5 on attack


}

