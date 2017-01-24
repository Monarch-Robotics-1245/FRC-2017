package org.usfirst.frc.team1245.robot.commands;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.usfirst.frc.team1245.robot.OI;
import org.usfirst.frc.team1245.robot.Robot;
import org.usfirst.frc.team1245.robot.RobotMap;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
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
        requires(Robot.turret);
        /* Calibration */
        Robot.turret.cameraRaw.setResolution(640, 480);
        Robot.turret.cameraRaw.setWhiteBalanceAuto();
        DriverStation.reportWarning(")))Calibration Starting!(((", false);
        try {
            Thread.sleep(15000);
        } catch (Exception e) {
            DriverStation.reportError(e.getMessage(), true);
        }
        Robot.turret.cameraRaw.setWhiteBalanceHoldCurrent();
        Robot.turret.cameraRaw.setExposureManual(30);
        DriverStation.reportWarning(")))Finished Calibration(((", false);
       Robot.visionThread = new Thread(() -> {
           
           // Get a CvSink. This will capture Mats from the camera
           CvSink cvSink = CameraServer.getInstance().getVideo();
           // Setup a CvSource. This will send images back to the Dashboard
           CvSource outputStream = CameraServer.getInstance().putVideo("Tracking", 640, 480);
           
           // Mats are very memory expensive. Lets reuse this Mat.
           Mat mat = new Mat();
           Mat cvt = new Mat();
           //Mat heir = new Mat();
           //Scalar green = new Scalar(0, 255, 0);
           
           // The while loop cannot be always 'true'. The program will never exit if it is. This
           // lets the robot stop this thread when restarting robot code or
           // deploying.
           
           int r = 0;
           int g = 0;
           int b = 0;
           int i = 0;
           
           while (!Thread.interrupted()) {
               // Tell the CvSink to grab a frame from the camera and put it
               // in the source mat.  If there is an error notify the output.
               if (cvSink.grabFrame(mat) == 0) {
                   // Send the output the error.
                   outputStream.notifyError(cvSink.getError());
                   // skip the rest of the current iteration
                   continue;
               }
               //Imgproc.cvtColor(mat, cvt, Imgproc.COLOR_BGR2HLS);
               Core.inRange(mat, new Scalar(r, g, b, 0), new Scalar(255, 255, 255, 255), cvt);
               ++i;
               if((i % 2) == 0){
                   ++b;
               }
               if ((i % 3) == 0){
                   ++r;
               }
               ++g;
               //DriverStation.reportWarning("Mat: " + mat.type(), false);
               // Process Image
               //Imgproc.cvtColor(mat, dst, Imgproc.COLOR_BGR2HSV);
               //Imgproc.findContours(dst, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
               /*for(int i = 0; i < contours.size(); ++i){
                   Imgproc.drawContours(mat,  contours, i, green, 2);   
               }*/
               
               DriverStation.reportWarning(")))Processing - Finished(((", false);                
               // Give the output stream a new image to display
               outputStream.putFrame(cvt);
           }
       });
       Robot.visionThread.setDaemon(true);
       Robot.visionThread.start();
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    private boolean done = false;
    protected boolean isFinished() {
        if(OI.gunnerJoystick.getRawButton(RobotMap.overrideButton)){
            done = true;
        }
        return done;
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
