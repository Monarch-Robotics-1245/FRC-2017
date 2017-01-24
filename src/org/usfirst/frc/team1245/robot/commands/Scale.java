package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.OI;
import org.usfirst.frc.team1245.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Scale extends Command{
    public Scale(){
        requires(Robot.scalar);
    }
    
    protected void initialize(){
        
    }
    
    protected void execute(){
        if (OI.driverPad.getAButton()){
            Robot.scalar.scalar.set(1.0);
        }
        else{
            Robot.scalar.scalar.set(0.0);
        }
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    protected void end(){
        
    }
    
    protected void interrupted(){
        
    }
}
