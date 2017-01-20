package org.usfirst.frc.team1245.robot.commands;

import org.opencv.core.Mat;
import org.usfirst.frc.team1245.robot.OI;
import org.usfirst.frc.team1245.robot.Robot;
import org.usfirst.frc.team1245.robot.RobotMap;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TrackTarget extends Command {

    private Thread visionThread;
    public TrackTarget() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
       requires(Robot.turret);
    }
    
    private void calibrate(){
        new Thread(() -> {
            Robot.turret.cameraRaw.setResolution(640, 480);
            Robot.turret.cameraRaw.setWhiteBalanceAuto();
            DriverStation.reportWarning(")))Calibration Starting!(((", false);
            try {
                Thread.sleep(15000);
            } catch (Exception e) {
                DriverStation.reportError(e.getMessage(), true);
            }
            Robot.turret.cameraRaw.setWhiteBalanceHoldCurrent();
            Robot.turret.cameraRaw.setExposureManual(100);
            DriverStation.reportWarning(")))Finished Calibration(((", false);
        }).start();
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        calibrate();
        DriverStation.reportWarning(")))Resolution Set(((", true);
        visionThread = new Thread(() -> {
            //Robot.cameraRaw.setExposureManual(10);
            //Robot.cameraRaw.setWhiteBalanceManual(1);
            //Robot.cameraRaw.setBrightness(18);
            
            // Get a CvSink. This will capture Mats from the camera
            CvSink cvSink = CameraServer.getInstance().getVideo();
            // Setup a CvSource. This will send images back to the Dashboard
            CvSource outputStream = CameraServer.getInstance().putVideo("Tracking", 640, 480);
            
            // Mats are very memory expensive. Lets reuse this Mat.
            Mat mat = new Mat();
            
            // The while loop cannot be always 'true'. The program will never exit if it is. This
            // lets the robot stop this thread when restarting robot code or
            // deploying.
            
            while (!Thread.interrupted()) {
                // Tell the CvSink to grab a frame from the camera and put it
                // in the source mat.  If there is an error notify the output.
                if (cvSink.grabFrame(mat) == 0) {
                    // Send the output the error.
                outputStream.notifyError(cvSink.getError());
                // skip the rest of the current iteration
                    continue;
                }
                // Process Image
                
                // Give the output stream a new image to display
                outputStream.putFrame(mat);
            }
        });
        visionThread.setDaemon(true);
        visionThread.start();
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        /*if(OI.gunnerJoystick.getRawButton(RobotMap.overrideButton)){
            return true;
        }*/
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        visionThread.interrupt();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        
    }
}
