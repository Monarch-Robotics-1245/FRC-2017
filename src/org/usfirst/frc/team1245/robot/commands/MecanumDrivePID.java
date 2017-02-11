// Unused PID drive command

package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MecanumDrivePID extends Command {

    public MecanumDrivePID() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        // Have the PID check for the speed of the wheel
        ((CANTalon)Robot.drivetrain.getFrontLeft()).changeControlMode(TalonControlMode.Speed);
        ((CANTalon)Robot.drivetrain.getRearLeft()).changeControlMode(TalonControlMode.Speed);
        ((CANTalon)Robot.drivetrain.getFrontRight()).changeControlMode(TalonControlMode.Speed);
        ((CANTalon)Robot.drivetrain.getRearRight()).changeControlMode(TalonControlMode.Speed);
        
        // Have the PID read the output from the Quadrature Encoders
        ((CANTalon)Robot.drivetrain.getFrontLeft()).setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        ((CANTalon)Robot.drivetrain.getRearLeft()).setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        ((CANTalon)Robot.drivetrain.getFrontRight()).setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        ((CANTalon)Robot.drivetrain.getRearRight()).setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        
        // Set the p, i, and d parameters of the PID systems (all should be the same)
        ((CANTalon)Robot.drivetrain.getFrontLeft()).setPID(1, 0, 0);
        ((CANTalon)Robot.drivetrain.getRearLeft()).setPID(1, 0, 0);
        ((CANTalon)Robot.drivetrain.getFrontRight()).setPID(1, 0, 0);
        ((CANTalon)Robot.drivetrain.getRearRight()).setPID(1, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}