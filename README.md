# Robot2020_EAC

#### Experimental EAC(Events,Actions, and Components) programming model repo

##### Not Tested yet, do not use!

## Overview


# Events

Events deal with input devices. A pollEvents method is provided, this is called before the Actions's runActions method, that allows Events to query the state of said input device every iteration if need be.

Events should provide a well defined and well formed API that promotes code readability, based on getters, to the rest of the code, for example, ControllerEvents turns getXboxLeftBumper to openIntake:

    public boolean openIntake() {
     return controllers.getXboxLeftBumper();
    }
    
### Dependencies

Events can only be dependent on Components, not other Events, Actions or low level classes.

Events can use the getComponents method to retrieve Components.
 
     @Override
     public void initialize() {
      controllers = (Controllers) getComponents("Controllers");
     }
 All objects should be created or retrieved in the initialize method, NOT the constructor, and NOT at the top of the file as part of the object's definition.
   
# Actions

Actions deal with the locomotion and actuation of the robot. Actions check for events from an Events object, then respond accordingly. Most of the robot's behavior should be described in Actions. A runActions method is called every iteration after the Events's pollEvents method.

For example IntakeActions responds to events from ControllerEvents:

     @Override
     public void runActions() {
     if (controllerEvents.openIntake()) {
       intakeComponent.openIntakeGate();
      }

      if (controllerEvents.closeIntake()) {
       intakeComponent.closeIntakeGate();
     }
    }
    
### Dependencies

Actions can be dependent on Events, Components, and spawned Actions, not other non-spawned Actions, or low level classes. Actions should not be dependent on Components for input, only Events.

Actions can use the getComponents method to retrieve Components, the getEvents method to retrieve Events, and the spawnActions method to spawn Actions. Spawned Actions objects must be directly managed by the Actions that spawned it.

# Components

Events and Actions should not directly use low level classes(SM,RobotMap,WPILib,etc.). Only Components should use low level classes. Components should represent or manage a specific hardware or software resource.

Components should provide a well defined and well formed API, that promotes code readability and minimizes the need for code changes  from hardware or software changes.

For example IntakeComponent directly calls the DoubleSolenoid to open the intake gate, after a call from IntakeActions:

    public void openIntakeGate() {
     if (isIntakeGateOpen) return;
     gateSolenoid.set(DoubleSolenoid.Value.kReverse);
     isIntakeGateOpen = true;
    }
    
Components can also be used to simplify the rest of the code, for example the Controllers Component turns xBoxController.getBumper(GenericHID.Hand.kLeft) to getXboxLeftBumper.

    public boolean getXboxLeftBumper() {
     return xBoxController.getBumper(GenericHID.Hand.kLeft);
    }
    
### Dependencies

Components can be dependent on other Components, and low level classes. Components should not be dependent on Events or Actions. Components can use the getComponents method to retrieve other Components.

# RobotContainer

RobotContainer 'contains' the robot. All Events and Actions are added here via the RobotManager class:

    public RobotContainer() {
     RobotManager.addEvents(ControllerEvents.class);
     RobotManager.addEvents(ConfigEvents.class);
     RobotManager.addEvents(PixyEvents.class);
     RobotManager.addActions(DriveActions.class);
     RobotManager.addActions(IntakeActions.class);
     RobotManager.addActions(AutonomousActions.class);
    }

There is no longer a Robot.java file.

To specify when an Events or Actions object runs use the setIsActiveForTeleOp method for teleop, or setIsActiveForAutonomous for Autonomous. These methods along with other setIsActiveFor methods and, the addRequiredComponent method for specifying required components are called in an object's constructor, and should be the only thing in an object's constructor.

    public DriveActions() {
     setIsActiveForTeleOp();
     addRequiredComponent(DriveComponent.class);
    }
