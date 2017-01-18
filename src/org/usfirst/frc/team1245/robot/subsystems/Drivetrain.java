package org.usfirst.frc.team1245.robot.subsystems;
// Base Drivetrain class

import org.usfirst.frc.team1245.robot.commands.MecanumDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 *
 */
public class Drivetrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    // RobotDrive object for access to FIRST's mecanum drive method
    protected RobotDrive robotDrive;

    // Creating the CANTalons
    public CANTalon frontLeft, frontRight, rearLeft, rearRight;

    public Gyro gyro;
    private Timer timer;
    public Drivetrain(int frontLeft, int frontRight, int rearLeft, int rearRight, int gyroChannel){
        // Initializing the CANTalons
        this.frontLeft = new CANTalon(frontLeft);
        this.frontRight = new CANTalon(frontRight);
        this.rearLeft = new CANTalon(rearLeft);
        this.rearRight = new CANTalon(rearRight);
        this.rearLeft.enableBrakeMode(true);
        gyro = new AnalogGyro(gyroChannel);
        timer = new Timer();
        timer.start();
        robotDrive = new RobotDrive(this.frontLeft, this.rearLeft, this.frontRight, this.rearRight);
        robotDrive.setInvertedMotor(MotorType.kFrontRight, true);
        robotDrive.setInvertedMotor(MotorType.kRearRight, true);
    }

    // Method to be implemented in base classes
    public void initDefaultCommand(){
     // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new MecanumDrive());
    }
    
    // Return the RobotDrive object
    public RobotDrive getDrivetrain() {
        return robotDrive;
    }
    
    // Return a specific speed motor in the following four methods
    public SpeedController getFrontLeft() {
        return frontLeft;
    }
    
    public SpeedController getRearLeft() {
        return rearLeft;
    }
    
    public SpeedController getFrontRight() {
        return frontRight;
    }
    
    public SpeedController getRearRight() {
        return rearRight;
    }
}

