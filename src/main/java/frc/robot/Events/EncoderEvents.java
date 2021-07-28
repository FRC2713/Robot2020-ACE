package frc.robot.Events;

import frc.robot.ACE.Events;
import frc.robot.Components.EncoderComponent;
import frc.robot.RobotMap;

public class EncoderEvents extends Events {

  private EncoderComponent encoderComponent;

  public EncoderEvents() {
    setIsActiveForTeleOp();
    setIsActiveForAutonomous();
    addRequiredComponent(EncoderComponent.class);
  }

  @Override
  public void initialize() {
    encoderComponent = (EncoderComponent) getComponent("EncoderComponent");
  }

  public double getDistance(int encoder, double[] oldEncoderValue) {
    double currentEncoderValue = encoderComponent.getEncoder(encoder).getPosition();
    double traveledUnits = (currentEncoderValue - oldEncoderValue[0]);
    double traveledInches = toInches(traveledUnits);
    oldEncoderValue[0] = currentEncoderValue;
    System.out.println("Traveled: " + traveledInches + " Inches");
    return traveledInches;
  }

  public double getDistanceInFeet(int encoder, double[] oldEncoderValue) {
    return toFeet(getDistance(encoder, oldEncoderValue));
  }

  public double getAccumulatedDistance(int encoder, double[] oldEncoderValue) {
    double currentEncoderValue = encoderComponent.getEncoder(encoder).getPosition();
    double traveledUnits = (currentEncoderValue - oldEncoderValue[0]);
    double traveledInches = toInches(traveledUnits);
    System.out.println("Traveled: " + traveledInches + " Inches");
    return traveledInches;
  }

  public double getAccumulatedDistanceInFeet(int encoder, double[] old_E_Value) {
    return toFeet(getAccumulatedDistance(encoder, old_E_Value));
  }

  private double toInches(double encoderValue) {
    return (encoderValue * RobotMap.getEncoderConstant());
  }

  private double toFeet(double inches) {
    return inches / 12;
  }

  public void resetEncoder(int encoder, double[] old_E_Value) {
    old_E_Value[0] = encoderComponent.getEncoder(encoder).getPosition();
  }

}
