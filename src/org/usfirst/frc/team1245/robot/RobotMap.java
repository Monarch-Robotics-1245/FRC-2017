package org.usfirst.frc.team1245.robot;

/**
 * The robot.RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
    
    // Talon SRX channels
    public static final int frontLeft = 6;
    public static final int rearLeft = 3;
    public static final int frontRight = 5;
    public static final int rearRight = 4;
    
    //Turret Channels
    public static final int rotation = 0;
    public static final int pitch = 0;
    public static final int shooter = 0;
    public static final int loader = 0;
    
    // Sensors
    public static final int gyroChannel = 0;
    
    // Control buttons
    public static final int speedButton = 5;
    public static final int fireButton = 1;
    public static final int overrideButton = 2;
    
    // Deadzones for rotation and translation for the drivetrain
    public static final double translationalDeadZone = 0.1;
    public static final double rotationalDeadZone = 0.1;
}
