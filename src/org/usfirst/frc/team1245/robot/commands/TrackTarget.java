package org.usfirst.frc.team1245.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1245.robot.OI;
import org.usfirst.frc.team1245.robot.Robot;
import org.usfirst.frc.team1245.robot.RobotMap;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TrackTarget extends Command {

    private Mat mat = new Mat();
    private Mat cvt = new Mat();
    private ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    private Mat heir = new Mat();
    
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
           
           // The while loop cannot be always 'true'. The program will never exit if it is. This
           // lets the robot stop this thread when restarting robot code or
           // deploying.
           
           int r = 10;
           int g = 120;
           int b = 0;
           int rr = 255;
           int gg = 255;
           int bb = 100;
           double[] curColor;
           
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
               Core.inRange(mat, new Scalar(r, g, b, 0), new Scalar(rr, gg, bb, 255), cvt);
               if(OI.driverPad.getBackButton()){
                   if(OI.driverPad.getBButton()){
                       ++bb;
                   }
                   if(OI.driverPad.getYButton()){
                       ++gg;
                   }
                   if(OI.driverPad.getXButton()){
                       ++rr;
                   }
                   if(OI.driverPad.getStartButton()){
                       rr = 180;
                       gg = 220;
                       bb = 100;
                   }
               }
               else{
                   if(OI.driverPad.getBButton()){
                       ++b;
                   }
                   if(OI.driverPad.getYButton()){
                       ++g;
                   }
                   if(OI.driverPad.getXButton()){
                       ++r;
                   }
                   if(OI.driverPad.getStartButton()){
                       r = 10;
                       g = 100;
                       b = 0;
                   }
               }
               SmartDashboard.putNumber("R: ", r);
               SmartDashboard.putNumber("G: ", g);
               SmartDashboard.putNumber("B: ", b);
               SmartDashboard.putNumber("RR: ", rr);
               SmartDashboard.putNumber("GG: ", gg);
               SmartDashboard.putNumber("BB: ", bb);
               //DriverStation.reportWarning("Mat: " + mat.type(), false);
               // Process Image
               int largeHCur = 0;
               int smallHCur = 0;
               int avgL = 0, iL = 0;
               int avgS = 0, iS = 0;
               
               int state = 0; //0 = top, 1 = large, 2 = middle, 3 = small,
               for(int i = 0; i < cvt.cols(); i+=3){
                   for(int j = 0; j < cvt.rows(); ++j){
                       curColor = cvt.get(j, i);
                       switch(state){
                       case 0:
                           if(curColor[0] > 127){
                               state = 1;
                           }
                           break;
                       case 1:
                           if(curColor[0] > 127){
                               ++largeHCur;
                           }else{
                               if(largeHCur > 5){
                                   state = 2;
                                   avgL+=largeHCur;
                                   ++iS;
                               }else{
                                   largeHCur = 0;
                                   state = 0;
                               }
                           }
                           break;
                       case 2:
                           if(curColor[0] > 127){
                               ++smallHCur;
                               state = 3;
                           }
                           break;
                       case 3:
                           if(curColor[0] > 127){
                               ++smallHCur;
                           }else{
                               if(smallHCur > 4){
                                   avgS += smallHCur;
                                   ++iS;
                                   smallHCur = 0;
                               }
                               state = 0;
                               i += 3;
                               j = 0;
                           }
                           break;
                       default:
                           state = 0;
                           break;
                       }
                   }
               }
               SmartDashboard.putNumber("Large H: ", avgL/iL);
               SmartDashboard.putNumber("Small H: ", avgS/iS);
               SmartDashboard.putNumber("Mat H: ", cvt.rows());
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
        /*for(int i = 0; i < contours.size(); ++i){
            Imgproc.line(cvt, new Point(contours.get(i).get(0, 0)[0], contours.get(i).get(0, 0)[1]), 
                    new Point(contours.get(i).get(0, 1)[0], contours.get(i).get(0, 1)[1]), new Scalar(255, 0, 0, 255)); 
        }*/
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
