package org.usfirst.frc.team1245.robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.usfirst.frc.team1245.robot.commands.DriveForward;
import org.usfirst.frc.team1245.robot.subsystems.ButterflyNet;
import org.usfirst.frc.team1245.robot.subsystems.Drivetrain;
import org.usfirst.frc.team1245.robot.subsystems.RopeScalar;
import org.usfirst.frc.team1245.robot.subsystems.Turret;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static OI oi;
    public static Drivetrain drivetrain = new Drivetrain(RobotMap.frontLeft, RobotMap.rearLeft, 
                                                         RobotMap.frontRight, RobotMap.rearRight, 
                                                         RobotMap.gyroChannel);
    public static Turret turret = new Turret(RobotMap.rotation, RobotMap.pitch, RobotMap.shooter, RobotMap.loader);
    public static RopeScalar scalar = new RopeScalar(RobotMap.scalarPort);
    public static ButterflyNet butterfree = new ButterflyNet(RobotMap.butterflyNet);
    
    public static int visionState = 2;
    public static int cameraState = 0;
    
    private Thread visionThread;
    private Mat mat;
    private Mat cvt;
    
    private int r = 0; //10
    private int g = 0; //120
    private int b = 0; //0
    private int rr = 0; //255
    private int gg = 0; //255
    private int bb = 0; //100%O

    private UsbCamera turretCamera;    
    private UsbCamera driverCamera;

    private MjpegServer outputServer;
    
    private CvSink cvSink;
    
    private Command autonomousCommand;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        oi = new OI();
        mat = new Mat();
        cvt = new Mat();
        //to clean up driver interface
        /*CameraServer.getInstance().removeServer("Turret");
        CameraServer.getInstance().removeServer("Driver Cam");
        CameraServer.getInstance().removeServer("Driver");
        CameraServer.getInstance().removeServer("Tracking");
        CameraServer.getInstance().removeServer("Turret Stuff");*/
        
        //Turret Camera
        turretCamera = new UsbCamera("Turret Raw", 0);
        turretCamera.setResolution(320, 240);
        turretCamera.setExposureManual(30);
        //Driver Camera
        driverCamera = new UsbCamera("Driver Cam", 1);
        driverCamera.setResolution(640, 480);
        
        CameraServer.getInstance().addCamera(driverCamera);
        CameraServer.getInstance().addCamera(turretCamera);
        
        /*
        RobotMap.cameraOutputStream = new CvSource("Output", PixelFormat.kMJPEG, 640, 480, 30);
        outputServer = new MjpegServer("serve_output", 12450);
        outputServer.setSource(RobotMap.cameraOutputStream);
        CameraServer.getInstance().addServer(outputServer);
        */

        cvSink = CameraServer.getInstance().getVideo();
        
        //drivetrain.gyro.calibrate();
        visionThread = new Thread(() -> {
            while(!Thread.interrupted()){
                /*if(cameraState > 2){
                    cameraState = 0;
                }
                switch(cameraState){
                case 0:
                    outputServer.setSource(RobotMap.cameraOutputStream);
                    break;
                case 1:
                    outputServer.setSource(driverCamera);
                    break;
                case 2:
                    outputServer.setSource(turretCamera);
                    break;
                default:
                    cameraState = 0;
                    break;
                }*/
                switch(visionState){
                case -1:
                    calibrateTurret();
                    break;
                case 0:
                    sleepTurret();
                    break;
                case 1:
                    if(!trackTarget()){
                        visionState = 2;
                    }
                    break;
                case 2:
                    manualTurret();
                    break;
                default:
                    break;
                }
                RobotMap.cameraOutputStream.putFrame(cvt);
            }
        });
        visionThread.setDaemon(true);
        visionThread.start();
    }
    
    private void calibrateTurret(){
        //Upper Bound
        if(OI.driverPad.getBackButton()){
            if(OI.driverPad.getBButton()){
                if(!OI.bWasPressed){
                    OI.bWasPressed = true;
                    ++bb;
                }
            }else OI.yWasPressed = false;
            if(OI.driverPad.getYButton()){
                if(!OI.yWasPressed){
                    OI.yWasPressed = true;
                    ++gg;
                }
            }else OI.yWasPressed = false;
            if(OI.driverPad.getXButton()){
                if(!OI.xWasPressed){
                    OI.xWasPressed = true;
                    ++rr;
                }
            }else OI.xWasPressed = false;
            if(OI.driverPad.getStartButton()){
                rr = 255;
                gg = 255;
                bb = 255;
            }
        }
        //Lower Bound
        else{
            if(OI.driverPad.getBButton()){
                if(!OI.bWasPressed){
                    OI.bWasPressed = true;
                    ++b;
                }
            }else OI.bWasPressed = false;
            if(OI.driverPad.getYButton()){
                if(!OI.yWasPressed){
                    OI.yWasPressed = true;
                    ++g;
                }
            }else OI.yWasPressed = false;
            if(OI.driverPad.getXButton()){
                if(!OI.xWasPressed){
                    OI.xWasPressed = true;
                    ++r;
                }
            }else OI.xWasPressed = false;
            if(OI.driverPad.getStartButton()){
                r = 0;
                g = 0;
                b = 0;
            }
        }
        if(OI.driverPad.getAButton()){
            DriverStation.reportWarning("Calibration Locked", false);
            visionState = 0;
            return;
        }
        SmartDashboard.putNumber("R: ", r);
        SmartDashboard.putNumber("G: ", g);
        SmartDashboard.putNumber("B: ", b);
        SmartDashboard.putNumber("RR: ", rr);
        SmartDashboard.putNumber("GG: ", gg);
        SmartDashboard.putNumber("BB: ", bb);
        if (cvSink.grabFrame(mat) == 0) {
            // Send the output the error.
            DriverStation.reportWarning("Tracking... Failed!", false);
            RobotMap.cameraOutputStream.notifyError(cvSink.getError());
            // skip the rest of the current iteration
            return;
        }
        Core.inRange(mat, new Scalar(r, g, b, 0), new Scalar(rr, gg, bb, 255), cvt);
    }
    
    private void sleepTurret(){
        visionState = 2;
    }
    
    private boolean trackTarget(){  
        DriverStation.reportWarning("Tracking... 1", false);
        if (cvSink.grabFrame(mat) == 0) {
            // Send the output the error.
            DriverStation.reportWarning("Tracking... Failed!", false);
            RobotMap.cameraOutputStream.notifyError(cvSink.getError());
            // skip the rest of the current iteration
            return false;
        }

        //DriverStation.reportWarning("Tracking... 2", false);
        
        Core.inRange(mat, new Scalar(r, g, b, 0), new Scalar(rr, gg, bb, 255), cvt);
        // Process Image        
        /* State representation
         * 0
         *|\\\\\\1\\\\\\|
         *|\\\\\\1\\\\\\|
         *2
         *|\\\\\\3\\\\\\|
         *end
         */
        int largeHMax = 0;
        int largeHCur = 0;
        int smallHMax = 0;
        int smallHCur = 0;
        double[] curColor = new double[3];
        int colorState = 0; //0 = top, 1 = large, 2 = middle, 3 = small,
        int i = 0;
        for(int j = 0; j < cvt.rows(); ++j){
            curColor = cvt.get(j, i);
            switch(colorState){
            case 0:
                if(curColor[0] >= 175){
                    colorState = 1;
                    ++largeHCur;
                }
                break;
            case 1:
                if(curColor[0] >= 175){
                    ++largeHCur;
                }else {
                    if(largeHCur > 5){
                        colorState = 2;
                    }
                }
                break;
            case 2:
                if(largeHCur > largeHMax){
                    largeHMax = largeHCur;
                    largeHCur = 0;
                }else largeHCur = 0;
                if(curColor[0] >= 175){
                    colorState = 3;
                    ++smallHCur;
                }
                break;
            case 3:
                if(curColor[0] >= 175){
                    ++smallHCur;
                }else colorState = 4;
                break;
            case 4:
                if(smallHCur > smallHMax){
                    smallHMax = smallHCur;
                    colorState = 5;
                    smallHCur = 0;
                }else colorState = 3;
                break;
            default:
                i += 3;
                j = 0;
                colorState = 0;
                break;
            }
        }
        //DriverStation.reportWarning("Tracking... 3", false);
        SmartDashboard.putNumber("Large H: ", largeHMax);
        SmartDashboard.putNumber("Small H: ", smallHMax);
        float distance = (329.6972761f*(2.0f/largeHMax + 1.0f/smallHMax));
        SmartDashboard.putNumber("Distance: ", distance);
        String debugValues = "Mat - " + cvt.height() + " = Cam - " + CameraServer.getInstance().getVideo(turretCamera).toString();
        SmartDashboard.putString("DEBUG VALUES: ", debugValues);
        return true;
    }
    
    private void manualTurret(){
        
    }
    
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        //Robot.drivetrain.gyro.reset();
        autonomousCommand = new DriveForward(1);
        //autonomousCommand = new DriveDistance(1000,0);
        if(autonomousCommand != null) {
            autonomousCommand.start();            
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset robot.subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
