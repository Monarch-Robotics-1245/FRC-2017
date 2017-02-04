package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.OI;
import org.usfirst.frc.team1245.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Feed extends Command{
    boolean feeding = true;
    public Feed(){
        requires(Robot.butterfree);
    }
    
    protected void initialize(){
        
    }
    
    protected void execute(){
        if(OI.driverPad.getXButton()){
            feeding = !feeding; //switches from t -> f, and vice versa
        }
        if(feeding){
            Robot.butterfree.butterFree.set(1.0);
        }
        else{
            Robot.butterfree.butterFree.set(0.0);
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
