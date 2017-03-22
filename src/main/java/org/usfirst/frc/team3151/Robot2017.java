package org.usfirst.frc.team3151;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3151.auto.AutoMode;
import org.usfirst.frc.team3151.auto.CrossBaselineAutoMode;
import org.usfirst.frc.team3151.auto.GearAutoMode;
import org.usfirst.frc.team3151.auto.IdleAutoMode;
import org.usfirst.frc.team3151.auto.action.DriveToVisionTargetAutoAction;
import org.usfirst.frc.team3151.subsystem.CameraStreamer;
import org.usfirst.frc.team3151.subsystem.Climber;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.Driver;
import org.usfirst.frc.team3151.subsystem.GearFlipper;
import org.usfirst.frc.team3151.subsystem.GearTray;
import org.usfirst.frc.team3151.subsystem.Gyroscope;
import org.usfirst.frc.team3151.subsystem.Operator;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;

import java.text.DecimalFormat;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;

public final class Robot2017 extends IterativeRobot {

    // Controls
    private Driver driver;
    private Operator operator;

    // Components
    private Gyroscope gyroscope;
    private Ultrasonic ultrasonic;
    private DriveTrain driveTrain;
    private GearFlipper gearFlipper;
    private GearTray gearTray;
    private Climber climber;

    // Auto
    private AutoMode autoMode;

    @Override
    public void robotInit() {
        driver = new Driver();
        operator = new Operator();

        gyroscope = new Gyroscope();
        ultrasonic = new Ultrasonic();
        driveTrain = new DriveTrain(gyroscope);
        gearFlipper = new GearFlipper();
        gearTray = new GearTray();
        climber = new Climber();

        autoMode = new IdleAutoMode();

        // normally all these methods are static but for some reason
        // String arrays specifically are not static. :(
        (new SmartDashboard()).putStringArray("Auto List", new String[] {
            "Idle",
            "Cross Baseline Left/Right",
            "Cross Baseline Center",
            "Gear Left",
            "Gear Center",
            "Gear Right"
        });

        new CameraStreamer();
        new Compressor();
    }

    @Override
    public void robotPeriodic() {
        gearFlipper.tick();
        gearTray.tick();

        if (operator.debugSensors()) {
            DecimalFormat threePlaces = new DecimalFormat("#.###");
            System.out.println("Gyro @ " + threePlaces.format(gyroscope.getCorrectedAngle()) + ", Ultrasonic @ " + threePlaces.format(ultrasonic.getMeasurement()));
        }

        if (operator.zeroGyro()) {
            gyroscope.zero();
        }
    }

    @Override
    public void teleopPeriodic() {
        int autoRotateAngle = driver.autoRotateAngle();

        if (autoRotateAngle > 0) {
            driveTrain.setAutoTurn(autoRotateAngle);
        } else {
            driveTrain.disableAutoTurn();

            if (driver.autoAngle()) {
                DriveToVisionTargetAutoAction.driveToTarget(driveTrain);
            } else {
                driveTrain.drive(driver.driveForward(), driver.driveStrafe(), driver.driveRotate());
            }
        }

        if (operator.flipGear()) {
            gearFlipper.flip();
        }

        if (operator.dumpBalls()) {
            gearTray.dump();
        }

        climber.climb(operator.climbPower());
    }

    @Override
    public void autonomousInit() {
        gyroscope.zero();

        switch (SmartDashboard.getString("Auto Selector", "Idle")) {
            case "Cross Baseline Left/Right":
                autoMode = new CrossBaselineAutoMode(driveTrain, ultrasonic, false);
                break;
            case "Cross Baseline Center":
                autoMode = new CrossBaselineAutoMode(driveTrain, ultrasonic, true);
                break;
            case "Gear Left":
                autoMode = new GearAutoMode(driveTrain, gearFlipper, ultrasonic, 1);
                break;
            case "Gear Center":
                autoMode = new GearAutoMode(driveTrain, gearFlipper, ultrasonic, 2);
                break;
            case "Gear Right":
                autoMode = new GearAutoMode(driveTrain, gearFlipper, ultrasonic, 3);
                break;
            default:
                autoMode = new IdleAutoMode();
                break;
        }

        autoMode.init();
    }

    @Override
    public void autonomousPeriodic() {
        autoMode.tick();
    }

}