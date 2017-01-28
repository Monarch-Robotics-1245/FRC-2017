package org.usfirst.frc.team1245.robot.subsystems;

import org.usfirst.frc.team1245.robot.commands.TrackTarget;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Turret extends Subsystem {

    public Relay rotation, pitch; //some of these may end up being Victors, but that will be really easy to change.
    public Victor shooter, loader;
    
    public Turret(int rotation, int pitch, int shooter, int loader){
        // TODO Auto-generated method stub
        this.rotation = new Relay(rotation);
        this.pitch = new Relay(pitch);
        this.loader = new Victor(loader);
        this.shooter = new Victor(shooter);
    }
    
    @Override
    protected void initDefaultCommand() {
        //setDefaultCommand(new TrackTarget());
    }

}
