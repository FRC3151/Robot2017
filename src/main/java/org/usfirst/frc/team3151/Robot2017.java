package org.usfirst.frc.team3151;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3151.auto.AutoMode;
import org.usfirst.frc.team3151.auto.mode.CrossBaselineAutoMode;
import org.usfirst.frc.team3151.auto.mode.GearAutoMode;
import org.usfirst.frc.team3151.auto.mode.IdleAutoMode;
import org.usfirst.frc.team3151.subsystem.*;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;

public final class Robot2017 extends IterativeRobot {

    private Driver driver;
    private Operator operator;

    private Gyroscope gyroscope;
    private Ultrasonic ultrasonic;
    private DriveTrain driveTrain;
    private GearFlipper gearFlipper;
    private GearTray gearTray;
    private Climber climber;

    private AutoMode auto;

    @Override
    public void robotInit() {
        RobotDrive wpiLibDrive = new RobotDrive(
            TalonUtil.createVoltageCompTalon(9),
            TalonUtil.createVoltageCompTalon(8),
            TalonUtil.createVoltageCompTalon(21),
            TalonUtil.createVoltageCompTalon(7)
        );

        wpiLibDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        wpiLibDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        wpiLibDrive.setMaxOutput(12); // see TalonUtil

        driver = new Driver(new XboxController(0));
        operator = new Operator(new XboxController(1));

        gyroscope = new Gyroscope(new ADXRS450_Gyro());
        ultrasonic = new Ultrasonic(new AnalogInput(3));
        driveTrain = new DriveTrain(wpiLibDrive);
        gearFlipper = new GearFlipper(new DoubleSolenoid(0, 1));
        gearTray = new GearTray(new DoubleSolenoid(2, 3));
        climber = new Climber(new CANTalon(20), new CANTalon(4));

        new Compressor();
        new CameraStreamer();
    }

    @Override
    public void robotPeriodic() {
        gearFlipper.tick();
        gearTray.tick();
    }

    @Override
    public void teleopPeriodic() {
        driveTrain.drive(driver.forwardMovement(), driver.strafeMovement(), driver.rotation());
        climber.climb(operator.climbPower());

        if (operator.flipGear()) {
            gearFlipper.flip();
        }

        if (operator.dumpBalls()) {
            gearTray.dump();
        }
    }

    @Override
    public void autonomousInit() {
        switch (SmartDashboard.getString("Auto Selector", "Idle")) {
            case "Cross Baseline Left": auto = new CrossBaselineAutoMode(driveTrain, ultrasonic, 1);
            case "Cross Baseline Center": auto = new CrossBaselineAutoMode(driveTrain, ultrasonic, 2);
            case "Cross Baseline Right": auto = new CrossBaselineAutoMode(driveTrain, ultrasonic, 3);
            case "Gear Left": auto = new GearAutoMode(driveTrain, gearFlipper, gyroscope, ultrasonic, 1);
            case "Gear Center": auto = new GearAutoMode(driveTrain, gearFlipper, gyroscope, ultrasonic, 2);
            case "Gear Right": auto = new GearAutoMode(driveTrain, gearFlipper, gyroscope, ultrasonic, 3);
            default: auto = new IdleAutoMode();
        }

        auto.autonomousInit();
    }

    @Override
    public void autonomousPeriodic() {
        auto.autonomousPeriodic();
    }

}