package org.usfirst.frc.team1245.robot.commands;

import org.usfirst.frc.team1245.robot.OI;
import org.usfirst.frc.team1245.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Scale extends Command{
    int i;
    public Scale(){
        requires(Robot.scalar);
    }
    
    protected void initialize(){
        i = 0;
    }
    
    protected void execute(){
        OI.driverAButton.whenReleased(incrementI());
        if(i%3==0){
            Robot.scalar.scalar.set(0.0);
        }
        else if (i%3==1){
            Robot.scalar.scalar.set(0.5);
        }
        else {
            Robot.scalar.scalar.set(1.0);
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
    
    private Command incrementI(){
        ++i;
        return null;
    }
}
