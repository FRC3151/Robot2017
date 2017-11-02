package org.usfirst.frc.team3151;

import com.ctre.CANTalon;

import org.usfirst.frc.team3151.auto.AutoMode;
import org.usfirst.frc.team3151.auto.mode.CrossBaselineAutoMode;
import org.usfirst.frc.team3151.auto.mode.GearAutoMode;
import org.usfirst.frc.team3151.auto.mode.IdleAutoMode;
import org.usfirst.frc.team3151.subsystem.Climber;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.Driver;
import org.usfirst.frc.team3151.subsystem.GearFlipper;
import org.usfirst.frc.team3151.subsystem.GearTray;
import org.usfirst.frc.team3151.subsystem.Gyroscope;
import org.usfirst.frc.team3151.subsystem.Operator;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;
import org.usfirst.frc.team3151.util.VoltageCompUtil;
import org.usfirst.frc.team3151.vision.CameraStreamer;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class Robot2017 extends IterativeRobot {

    // Controls
    private Driver driver;
    private Operator operator;

    // Sensors
    private Gyroscope gyroscope;
    private Ultrasonic ultrasonic;

    // Subsystems
    private DriveTrain driveTrain;
    private GearFlipper gearFlipper;
    private GearTray gearTray;
    private Climber climber;
    private Compressor compressor;

    // Auto
    private AutoMode auto;

    @Override
    public void robotInit() {
        RobotDrive wpiLibDrive = new RobotDrive(
            VoltageCompUtil.createVoltageTalon(9),
            VoltageCompUtil.createVoltageTalon(8),
            VoltageCompUtil.createVoltageTalon(21),
            VoltageCompUtil.createVoltageTalon(7)
        );

        wpiLibDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        wpiLibDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        wpiLibDrive.setMaxOutput(12); // see VoltageCompUtil

        driver = new Driver(new XboxController(0));
        operator = new Operator(new XboxController(1));

        gyroscope = new Gyroscope(new ADXRS450_Gyro());
        ultrasonic = new Ultrasonic(new AnalogInput(3));

        driveTrain = new DriveTrain(wpiLibDrive, gyroscope);
        gearFlipper = new GearFlipper(new DoubleSolenoid(0, 1));
        gearTray = new GearTray(new DoubleSolenoid(2, 3));
        climber = new Climber(new CANSpeedController[] {
            new CANTalon(20),
            new CANTalon(4)
        });
        compressor = new Compressor();

        // normally all these methods are static but for some reason
        // String arrays specifically are not static. :(
        new SmartDashboard().putStringArray("Auto List", new String[] {
            "Idle",
            "Cross Baseline Left/Right",
            "Cross Baseline Center",
            "Gear Left",
            "Gear Center",
            "Gear Right",
            "Vision Only"
        });

        new CameraStreamer();
    }

    @Override
    public void robotPeriodic() {
        gearFlipper.tick();
        gearTray.tick();
    }

    @Override
    public void testPeriodic() {
        // LiveWindow (a part of SmartDashboard) lets us manually
        // control solenoids, motors, etc in test mode (which is
        // seperate from teleop/auto)
        LiveWindow.run();
    }

    @Override
    public void teleopInit() {
        // if we were still trying to angle at the end of auto
        // we would keep turning into the start of teleop
        driveTrain.stopAutoAngle();
    }

    @Override
    public void teleopPeriodic() {
        driveTrain.drive(driver.driveForward(), driver.driveStrafe(), driver.driveRotate());
        climber.climb(operator.climbPower());

        if (operator.flipGear()) {
            gearFlipper.flip();
        }

        if (operator.dumpBalls()) {
            gearTray.dump();
        }

        if (operator.conservePower()) {
            compressor.stop();
        } else {
            compressor.start();
        }
    }

    @Override
    public void autonomousInit() {
        auto = createAutoMode(SmartDashboard.getString("Auto Selector", "Idle"));
        auto.autonomousInit();
    }

    @Override
    public void autonomousPeriodic() {
        auto.autonomousPeriodic();
    }

    private AutoMode createAutoMode(String selection) {
        switch (selection) {
            case "Cross Baseline Left/Right": return new CrossBaselineAutoMode(driveTrain, ultrasonic, false);
            case "Cross Baseline Center": return new CrossBaselineAutoMode(driveTrain, ultrasonic, true);
            case "Gear Left": return new GearAutoMode(driveTrain, gearFlipper, gyroscope, ultrasonic, 1);
            case "Gear Center": return new GearAutoMode(driveTrain, gearFlipper, gyroscope, ultrasonic, 2);
            case "Gear Right": return new GearAutoMode(driveTrain, gearFlipper, gyroscope, ultrasonic, 3);
            case "Vision Only": return new VisionOnlyAutoMode(driveTrain, gearFlipper);
            default: return new IdleAutoMode();
        }
    }

}