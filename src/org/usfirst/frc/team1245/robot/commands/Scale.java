package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.OI;
import org.usfirst.frc.team1245.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class Scale extends Command{
    public Scale(){
        requires(Robot.scalar);
    }
    
    protected void initialize(){
        
    }
    
    protected void execute(){
        if(OI.driverPad.getRawAxis(2) > .75 && OI.driverPad.getRawAxis(3) > .75){
            if(OI.driverPad.getYButton()){
                Robot.scalar.scalarMotor.set(1.0);
            }
            else{
                Robot.scalar.scalarMotor.set(-1.0);
            }
        }
        else if(OI.driverPad.getBumper(Hand.kLeft) && OI.driverPad.getBumper(Hand.kRight)){
            if(OI.driverPad.getYButton()){
                Robot.scalar.scalarMotor.set(.6);
            }
            else{
                Robot.scalar.scalarMotor.set(-.6);
            }
        }
        else
            Robot.scalar.scalarMotor.set(0.0);
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
