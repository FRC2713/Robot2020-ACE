# Robot2020_EAC

#### Experimental EAC(Events,Actions, and Components) programming model repo

##### Not Tested yet, do not use!




## Overview


# Events

Events deal with input devices. A pollEvents method is provided, this is called before an Actions's runActions method, that allows Events to query the state of said input device every iteration if need be.

Events should provide a well defined and well formed API that promotes code readability, based on getters, to the rest of the code, for example, ControllerEvents turns getXboxLeftBumper to openIntake:

    public boolean openIntake() {
     return controllers.getXboxLeftBumper();
    }
    
# Actions

Actions deal with the locomotion and actuation of the robot. Actions check for events from an Events object, then respond accordingly. Most of the robot's behavior should be described in Actions. A runActions method is called every iteration after an Events's pollEvents method.

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
    

# Components

Events and Actions should not directly use low level classes(SM,RobotMap,WPILib,etc.). Only Components should use low level classes. Components should represent or manage a specific hardware or software resource.

Components should provide a well defined and well formed API, that promotes code readability and minimizes the need for code changes  from hardware or software changes.

For example IntakeComponent directly calls the DoubleSolenoid to open the intake gate, after a call from IntakeActions:

    public void openIntakeGate() {
     if (isIntakeGateOpen) return;
     gateSolenoid.set(DoubleSolenoid.Value.kReverse);
     isIntakeGateOpen = true;
    }
    

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
