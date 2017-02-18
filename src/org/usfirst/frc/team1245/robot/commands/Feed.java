package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.OI;
import org.usfirst.frc.team1245.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Feed extends Command{
    boolean fly = false;
    int pressTime = 0;
    
    public Feed(){
        requires(Robot.butterfree);
    }
    
    protected void initialize(){
        
    }
    
    protected void execute(){
        pressTime -= 1;
        if(OI.driverPad.getXButton() && pressTime <= 0){
            pressTime = 5;
            fly = !fly;
        }
        Robot.butterfree.butterFree.set((fly) ? -0.95 : 0.0);
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
