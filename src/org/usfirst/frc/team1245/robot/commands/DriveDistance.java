package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.Robot;
import org.usfirst.frc.team1245.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistance extends Command{
    double distanceX, distanceY;
    public DriveDistance(double dX, double dY){
        requires(Robot.drivetrain);
        distanceX = dX;
        distanceY = dY;
    }
    
    protected void initialize(){
        Drivetrain.dPIDOutputEnabled = true;
        Robot.drivetrain.dPID.setSetpoint(distanceX);
        DriverStation.reportWarning("Initializing autonomous distance based", true);

    }
    protected void execute(){
        Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(Robot.drivetrain.dPID.get(), Robot.drivetrain.dPID.get(), 0, 0);
        //SmartDashboard.putNumber("PID: ", Robot.drivetrain.dPID.get());
    }
    
    protected boolean isFinished() {
        return Math.abs(Robot.drivetrain.dPID.getAvgError()) < 2; //possible issue - doesn't end?
    }
    
    protected void end(){
        Drivetrain.dPIDOutputEnabled = false;
        Robot.drivetrain.dPID.reset();
        Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(0, 0, 0, 0);
        SmartDashboard.putBoolean("is dPID ontarget", Robot.drivetrain.dPID.onTarget());
        DriverStation.reportWarning("Ending autonomous distance based", true);
    }
    
    protected void interrupted(){
        
    }
}
