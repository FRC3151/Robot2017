package org.usfirst.frc.team3151;

import org.usfirst.frc.team3151.auto.vision.GearAuto;
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
    private GearAuto auto;

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

        CameraStreamer streamer = new CameraStreamer();
        auto = new GearAuto(streamer.getGearCamera(), driveTrain, gearFlipper, ultrasonic);
        new Compressor();
    }

    @Override
    public void robotPeriodic() {
        gearFlipper.tick();
        gearTray.tick();

        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        System.out.println("Gyro" + decimalFormat.format(gyroscope.getCorrectedAngle()) + ", Ultrasonic: " + decimalFormat.format(ultrasonic.getMeasurement()));
    }

    @Override
    public void teleopPeriodic() {
        int autoRotateAngle = driver.autoRotateAngle();

        if (autoRotateAngle > 0) {
            driveTrain.setAutoTurn(autoRotateAngle);
        } else {
            driveTrain.disableAutoTurn();
            driveTrain.drive(driver.driveForward(), driver.driveStrafe(), driver.driveRotate());
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
        auto.autoStarted();
    }

    @Override
    public void autonomousPeriodic() {
        auto.autoTick();
    }

}