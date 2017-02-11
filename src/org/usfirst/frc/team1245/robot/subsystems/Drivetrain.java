package org.usfirst.frc.team1245.robot.subsystems;
// Base Drivetrain class

import org.usfirst.frc.team1245.robot.Robot;
import org.usfirst.frc.team1245.robot.commands.MecanumDrive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    //private Timer timer;
    public Drivetrain(int frontLeft, int frontRight, int rearLeft, int rearRight, int gyroChannel){
        initPID(); //because we use PID on the robot.
        // Initializing the CANTalons
        this.frontLeft = new CANTalon(frontLeft);
        this.frontRight = new CANTalon(frontRight);
        this.rearLeft = new CANTalon(rearLeft);
        this.rearRight = new CANTalon(rearRight);
        
        this.frontLeft.enableBrakeMode(true);
        this.frontRight.enableBrakeMode(true);
        this.rearLeft.enableBrakeMode(true);
        this.rearRight.enableBrakeMode(true);
        
        this.frontLeft.setPID(.1,  0,  0);
        this.frontRight.setPID(.1,  0,  0);
        this.rearLeft.setPID(.1,  0,  0);
        this.rearRight.setPID(.1,  0,  0);

        this.frontLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        this.frontRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        this.rearLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        this.rearRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);

        
        
        gyro = new AnalogGyro(gyroChannel);
        //timer = new Timer();
        //timer.start();
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
    
    private static double dKp;
    private static double dKi;
    private static double dKd;
    public PIDController dPID;
    public static boolean dPIDOutputEnabled = false;
    public static double average;
    
    private class AvgEncoder implements PIDSource{
        AvgEncoder(){
            
        }
        @Override
        public double pidGet(){
            //returns the average of distance turned
            average =  (Robot.drivetrain.frontLeft.get() + Robot.drivetrain.frontRight.get() + 
                    Robot.drivetrain.rearLeft.get() + Robot.drivetrain.rearRight.get())/4;
            SmartDashboard.putNumber("Average", average);
            return average;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public PIDSourceType getPIDSourceType() {
            // TODO Auto-generated method stub
            return null;
        }
    }
    private class AvgOutput implements PIDOutput{

        @Override
        public void pidWrite(double output) {
            // TODO Auto-generated method stub
            //don't even use this, but I have to... because Java... hrrrrrr...
        }
        
    }
    private void initPID(){
        dPID = new PIDController(dKp,dKi, dKd, new AvgEncoder(), new AvgOutput()){
            void writePID(double xMove, double yMove){
                Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(xMove, yMove, 0, 0);
            }
        };
        dPID.setAbsoluteTolerance(5);
        dPID.setOutputRange(-.8, .8);
        dPID.setSetpoint(0);
    }
    
    
    
}

