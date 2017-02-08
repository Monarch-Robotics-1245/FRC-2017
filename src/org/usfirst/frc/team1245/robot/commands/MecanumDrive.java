/* Command that is continuously run to take joystick input and 
 * drive the robot using a mecanum drivetrain */

package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.OI;
import org.usfirst.frc.team1245.robot.Robot;
import org.usfirst.frc.team1245.robot.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MecanumDrive extends Command {

    public MecanumDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // This command requires the drivetrain
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
    }

    double speedScale = 1.0;
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(OI.driverJoystick.getRawButton(7)){
            Robot.cameraState++;
        }
        
        if(OI.driverPad.getAButton()){
            speedScale = .80;
        }else if(OI.driverPad.getBButton()){
            speedScale = .50;
        }else{
            speedScale = 1.0;
        }
        
        // Get joystick input and filter it through the dead zone function
        double y = OI.scaleSpeed(-OI.deadZone(OI.driverPad.getX(Hand.kLeft), RobotMap.translationalDeadZone), speedScale);
        double x = OI.scaleSpeed(-OI.deadZone(OI.driverPad.getY(Hand.kLeft), RobotMap.translationalDeadZone), speedScale);
        double twist = OI.scaleSpeed(OI.deadZone(OI.driverPad.getX(Hand.kRight), RobotMap.rotationalDeadZone), speedScale);
        
        // Drive the robot based on the user input
        Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(x, y, twist, 0);
        
        // Write the drive parameters to Smartdashboard
        SmartDashboard.putNumber("Speed", speedScale);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        /* This command is run continuosuly and will 
         * never finish during teleop */
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        
    }

    // Called when another command which requires one or more of the same
    // robot.subsystems is scheduled to run
    protected void interrupted() {
        
    }
}
