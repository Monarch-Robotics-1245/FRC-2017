package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {

    private Timer timer;
    private int location;
    private boolean finished = false;
    private int state = 0;
    
    public DriveForward(int loc) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.drivetrain);
        timer = new Timer();
        location = loc;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        timer.start();
        DriverStation.reportWarning("Initializing autonomous", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        switch(location){
        case 1:
            switch(state){
            case 0:
                if(timer.get() < .75){
                    Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(1.0, 0.0, 0.0, 0.0);
                }else{
                    timer.reset();
                    timer.start();
                    ++state;
                }
                break;
            case 1:
                if(timer.get() < .25){
                    Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(0.0, 0.0, 1.0, 0.0);
                    break;
                }else{
                    timer.reset();
                    timer.start();
                    ++state;
                }
            case 2:
                if(timer.get() < .1){
                    Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(1.0, 0.0, 0.0, 0.0);
                }else finished = true;
                break;
            }
            break;
        case 2:
            if(timer.get() < .75){
                Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(1.0, 0.0, 0.0, 0.0);
            }else finished = true;
            break;
        case 3:
            switch(state){
            case 0:
                if(timer.get() < .75){
                    Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(1.0, 0.0, 0.0, 0.0);
                }else{
                    timer.reset();
                    timer.start();
                    ++state;
                }
                break;
            case 1:
                if(timer.get() < .25){
                    Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(0.0, 0.0, -1.0, 0.0);
                    break;
                }else{
                    timer.reset();
                    timer.start();
                    ++state;
                }
            case 2:
                if(timer.get() < .1){
                    Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(1.0, 0.0, 0.0, 0.0);
                }else finished = true;
                break;
            }
            break;
        }
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
        timer.stop();
        timer.reset();
        Robot.drivetrain.getDrivetrain().stopMotor();
        DriverStation.reportWarning("Ending autonomous", true);
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
