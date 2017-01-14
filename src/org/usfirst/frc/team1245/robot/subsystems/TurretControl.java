package org.usfirst.frc.team1245.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TurretControl extends Subsystem {

    Relay rotation, pitch, loader;
    Talon shooter;
    
    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
        this.rotation = new Relay(0);
        this.pitch = new Relay(1);
        this.loader = new Relay(2);
        this.shooter = new Talon(0);
    }

}
