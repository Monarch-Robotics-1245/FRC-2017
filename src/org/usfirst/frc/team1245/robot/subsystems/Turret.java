package org.usfirst.frc.team1245.robot.subsystems;

import org.usfirst.frc.team1245.robot.commands.TrackTarget;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Turret extends Subsystem {

    //Relay rotation, pitch, loader;
    //Talon shooter;
    public UsbCamera cameraRaw = CameraServer.getInstance().startAutomaticCapture("Turret", 0);;
    
    public Turret(int rotation, int pitch, int shooter, int loader){
        // TODO Auto-generated method stub
        //this.rotation = new Relay(rotation);
        //this.pitch = new Relay(pitch);
       // this.loader = new Relay(loader);
        //this.shooter = new Talon(shooter);
    }
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TrackTarget());
    }

}
