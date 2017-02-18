package org.usfirst.frc.team1245.robot.subsystems;

import org.usfirst.frc.team1245.robot.commands.ManualTurret;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Turret extends Subsystem {

    public Relay rotation; //some of these may end up being Victors, but that will be really easy to change.
    public Victor shooter, loader, pitch;
    
    public Turret(int rotationPort, int pitchPort, int shooterPort, int loaderPort){
        this.rotation = new Relay(rotationPort);
        this.pitch = new Victor(pitchPort);
        this.loader = new Victor(loaderPort);
        this.shooter = new Victor(shooterPort);
    }
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ManualTurret());
    }

}
