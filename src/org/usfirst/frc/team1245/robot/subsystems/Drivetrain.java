package org.usfirst.frc.team1245.robot.subsystems;
// Base Drivetrain class

import org.usfirst.frc.team1245.robot.Robot;
import org.usfirst.frc.team1245.robot.commands.MecanumDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
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

    //public Gyro gyro;
    //private Timer timer;
    public Drivetrain(int frontLeft, int frontRight, int rearLeft, int rearRight, int gyroChannel){
        //initDPID(); //because we use PID on the robot.
        // Initializing the CANTalons
        this.frontLeft = new CANTalon(frontLeft);
        this.frontRight = new CANTalon(frontRight);
        this.rearLeft = new CANTalon(rearLeft);
        this.rearRight = new CANTalon(rearRight);
        this.frontLeft.enableBrakeMode(true);
        this.frontRight.enableBrakeMode(true);
        this.rearLeft.enableBrakeMode(true);
        this.rearRight.enableBrakeMode(true);

        /*this.frontLeft.setControlMode(CANTalon.TalonControlMode.Position.value);
        this.frontRight.setControlMode(CANTalon.TalonControlMode.Position.value);
        this.rearLeft.setControlMode(CANTalon.TalonControlMode.Position.value);
        this.rearRight.setControlMode(CANTalon.TalonControlMode.Position.value);
        this.frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        this.frontRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        this.rearLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        this.rearRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        
        this.frontLeft.setPID(.1, 0, 0);
        this.frontRight.setPID(.1, 0,  0);
        this.rearLeft.setPID(-.1, 0, 0);
        this.rearRight.setPID(-.1, 0, 0);*/
        
        //gyro = new AnalogGyro(gyroChannel);
        //timer = new Timer();
        //timer.start();
        robotDrive = new RobotDrive(this.frontLeft, this.rearLeft, this.frontRight, this.rearRight);
        robotDrive.setInvertedMotor(MotorType.kFrontRight, true);
        robotDrive.setInvertedMotor(MotorType.kRearRight, true);
    }

    public void initDefaultCommand(){
        // Set the default command for a subsystem here.
        setDefaultCommand(new MecanumDrive());
    }
    
    // Return the RobotDrive object
    public RobotDrive getDrivetrain() {
        return robotDrive;
    }
    
    private static double dKp;
    private static double dKi;
    private static double dKd;
    public PIDController dPID;
    public static boolean dPIDOutputEnabled = false;
    public static double average;
    
    private class AvgEncoder implements PIDSource{

        @Override
        public double pidGet(){
            //returns the average of distance turned
            average = (Robot.drivetrain.frontLeft.get() + Robot.drivetrain.frontRight.get() + 
                    Robot.drivetrain.rearLeft.get() + Robot.drivetrain.rearRight.get())/4;
            SmartDashboard.putNumber("Average", average);
            return average;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
            
        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return null;
        }
    }

    private void initDPID(){
        dPID = new PIDController(dKp, dKi, dKd, new AvgEncoder(), new PIDOutput() {
            @Override
            public void pidWrite(double output) {
                Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(output, output, 0, 0);
            }
        });
        dPID.setOutputRange(-.8, .8);
        dPID.setAbsoluteTolerance(5);
        dPID.setSetpoint(0);
    }
}

