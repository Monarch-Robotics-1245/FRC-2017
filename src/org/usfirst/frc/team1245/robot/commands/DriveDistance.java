package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.Robot;
import org.usfirst.frc.team1245.robot.subsystems.Drivetrain;

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
        Robot.drivetrain.dPID.setSetpoint(Math.sqrt(Math.pow(distanceY, 2) + Math.pow(distanceX, 2)));
    }
    protected void execute(){
        Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(0, 0, 0, 0);
        SmartDashboard.putNumber("Large H: ", Robot.drivetrain.frontRight.getD());
    }
    protected void end(){
        Drivetrain.dPIDOutputEnabled = false;
        Robot.drivetrain.dPID.reset();
        Robot.drivetrain.getDrivetrain().mecanumDrive_Cartesian(0, 0, 0, 0);
    }
    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.drivetrain.dPID.getError())<2;
    }
    protected void interrupted(){
        
    }
}
