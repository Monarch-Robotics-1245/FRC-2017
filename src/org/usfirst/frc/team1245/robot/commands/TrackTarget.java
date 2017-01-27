package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.OI;
import org.usfirst.frc.team1245.robot.Robot;
import org.usfirst.frc.team1245.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TrackTarget extends Command {
    
    public TrackTarget() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.turret);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.visionState = 1;
    }

    // Make this return true when this Command no longer needs to run execute()
    private boolean done = false;
    protected boolean isFinished() {
        if(OI.gunnerJoystick.getRawButton(RobotMap.overrideButton)){
            done = true;
        }
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        
    }
}
