# Robot2020 ACE

#### Experimental ACE(Actions, Components, and Events) programming model repo

### Tested only in wpilib robot simulator.

## Overview


# Events

Events deal with input devices(including sensors). A pollEvents method is provided, this is called before the Actions's runActions method, that allows Events to query the state of said input device every iteration if need be.

Events should provide a well defined and well formed API that promotes code readability, based on getters, to the rest of the code, for example, ControllerEvents turns controllers.getDriveController().getLeftBumper() to openIntake:

    public boolean openIntake() {
     return controllers.getDriveController().getLeftBumper();
    }
    
### Dependencies

Events can only be dependent on Components, not other Events, Actions or low level classes.

Events can use the getComponent method to retrieve Components.
 
     @Override
     public void initialize() {
      controllers = (Controllers) getComponent("Controllers");
     }
 All objects should be created or retrieved in the initialize method, NOT the constructor, and NOT at the top of the file as part of a variable's definition.
   
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

Actions can be dependent on Events, Components, and ActionGroups, not other Actions, or low level classes. Actions should use Events for input, though there are cases where it may make sense for Actions to get input directly from a Component.

Actions can use the getComponent method to retrieve Components, the getEvents method to retrieve Events, and the addActions method to add Actions to ActionGroups.

#### ActionGroups

ActionGroups are a group of Actions. Actions are added to an ActionGroup via the addActions method. The runActionGroup method is used to run a specific ActionGroup. Both the addActions, and runActionGroup method take in a group name to specify the correct ActionGroup. If not given a name they will use the default ActionGroup.

    @Override
    public void initialize() {
     //add Actions to the default ActionGroup
     addActions(GoForward::new).setTargetDistInFeet(10);
     addActions(GoForward::new).setTargetDistInFeet(25);
    }

    @Override
    public void runActions() {
     //run the default ActionGroup
     runActionGroup();
    }
    
    @Override
    public void initialize() {
     //add Actions to the ActionGroup named "path_1"
     addActions(GoForward::new,"path_1").setTargetDistInFeet(10);
     addActions(GoForward::new,"path_1").setTargetDistInFeet(25);
    }

    @Override
    public void runActions() {
     //run the ActionGroup named "path_1"
     runActionGroup("path_1");
    }
    
Each Actions has its own set of ActionGroups, their own default group and their own name space for named groups.

# Components

Events and Actions should not directly use low level classes(Utility,WPILib,etc.). Only Components should use low level classes. Components should represent or manage a specific hardware or software resource.

Components should provide a well defined and well formed API, that promotes code readability and minimizes the need for code changes  from hardware or software changes.

For example IntakeComponent directly calls the DoubleSolenoid to open the intake gate, after a call from IntakeActions:

    public void openIntakeGate() {
     if (isIntakeGateOpen) return;
     gateSolenoid.set(DoubleSolenoid.Value.kReverse);
     isIntakeGateOpen = true;
    }
    
Components can also be used to simplify the rest of the code, for example the Controllers Component hides the management and initialization of Controller objects.

    @Override
    public void initialize() {
     driveController = new Xbox("Drive",RobotMap.ALL_XBOX_NAMES);
     auxiliaryController = new Controller(RobotMap.ATTACK_NAME);
     update();
    }

    public void update() {
     driveController.update();
     auxiliaryController.update();
    }

    public void reset() {
     driveController.resetState();
     auxiliaryController.resetState();
    }
    

And provides simple getters for the rest of the code.

    public Xbox getDriveController() {
     return driveController;
    }

    public Controller getAuxiliaryController() {
     return auxiliaryController;
    }
    
### Dependencies

Components can be dependent on other Components, Controllers and low level classes, NOT Events or Actions. Components can use the getComponent method to retrieve other Components.

#### Controllers

ACE provide a "Controller" class for creation and management of Controllers. Supports state and port management, will rescan ports to match names of controllers based on name given and constructor used.

# RobotContainer

RobotContainer 'contains' the robot. All Events and Actions are added here via the RobotManager class:

    public RobotContainer() {
     RobotManager.addEvents(ControllerEvents.class);
     RobotManager.addEvents(ConfigEvents.class);
     RobotManager.addEvents(PixyEvents.class);
     RobotManager.addEvents(ArduinoEvents.class);
     RobotManager.addActions(DriveActions.class);
     RobotManager.addActions(IntakeActions.class);
     RobotManager.addActions(ClimberActions.class);
     RobotManager.addActions(AutonomousActions.class);   
    }

There is no longer a Robot.java file.

To specify when an Events or Actions object runs use the setIsActiveForTeleOp method for teleop, or setIsActiveForAutonomous for Autonomous. These methods along with other setIsActiveFor methods and, the addRequiredComponent method for specifying required components are called in an object's constructor, and should be the only thing in an object's constructor.

    public DriveActions() {
     setIsActiveForTeleOp();
     addRequiredComponent(DriveComponent.class);
    }
