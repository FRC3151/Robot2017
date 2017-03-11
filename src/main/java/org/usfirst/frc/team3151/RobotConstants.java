package org.usfirst.frc.team3151;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public final class RobotConstants {

    public static final int GEAR_LEFT_ANGLE = 60;
    public static final int GEAR_RIGHT_ANGLE = 300;
    public static final int RETRIEVAL_LEFT_ANGLE = 150;
    public static final int RETRIEVAL_RIGHT_ANGLE = 210;

    public static final RobotDrive ROBOT_DRIVE = new RobotDrive(
        new CANTalon(9),
        new CANTalon(8),
        new CANTalon(21),
        new CANTalon(7)
    );

    public static final Gyro GYRO = new ADXRS450_Gyro();
    public static final AnalogInput ULTRASONIC_IN = new AnalogInput(3);

    public static final XboxController DRIVER_XBOX = new XboxController(0);
    public static final XboxController OPERATOR_XBOX = new XboxController(1);

    public static final DoubleSolenoid GEAR_FLIPPER = new DoubleSolenoid(0, 1);
    public static final DoubleSolenoid GEAR_TRAY = new DoubleSolenoid(2, 3);

    public static final SpeedController CLIMBER_A = new CANTalon(20);
    public static final SpeedController CLIMBER_B = new CANTalon(4);

    static {
        ROBOT_DRIVE.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        ROBOT_DRIVE.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
    }

}