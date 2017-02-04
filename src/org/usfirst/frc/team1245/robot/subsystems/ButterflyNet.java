package org.usfirst.frc.team1245.robot.subsystems;

import org.usfirst.frc.team1245.robot.commands.Feed;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ButterflyNet extends Subsystem{
    public Victor butterFree;
    public ButterflyNet(int butterflyPort){
        this.butterFree = new Victor(butterflyPort);
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
        setDefaultCommand(new Feed());
    }
}
