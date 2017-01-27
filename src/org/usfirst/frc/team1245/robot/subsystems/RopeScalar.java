package org.usfirst.frc.team1245.robot.subsystems;

import org.usfirst.frc.team1245.robot.commands.Scale;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RopeScalar extends Subsystem{
    public Victor scalar;
    
    public RopeScalar(int scalar){
        this.scalar = new Victor(scalar);
    }
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new Scale());
    }
}
