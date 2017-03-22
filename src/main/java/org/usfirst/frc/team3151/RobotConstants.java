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
    public static final int RETRIEVAL_LEFT_ANGLE = 120;
    public static final int RETRIEVAL_RIGHT_ANGLE = 240;

    public static final RobotDrive ROBOT_DRIVE = new RobotDrive(
        wrap(new CANTalon(9)),
        wrap(new CANTalon(8)),
        wrap(new CANTalon(21)),
        wrap(new CANTalon(7))
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

        // see comment on wrap method
        ROBOT_DRIVE.setMaxOutput(12);
    }

    // this one is interesting! So, essentially, FRC batteries (and all batteries really)
    // output less voltage as they get less full. so it might start at 12.8v, then drop to
    // 11v, then all the way down to like 9v on a near dead battery. this is relevant because
    // normally we control Talons by telling them "hey output 50% of the voltage you're getting"
    // - which works well on a perfect battery, but not all batteries are perfect. 50% of 12.8v is
    // different from 50% of 11v. so what we do (at least for our drive train motors where this
    // matters) is tell the Talons the exact voltage to output. so if we ask for 6v it might have
    // to be at 50%, 48%, 59%, who cares, we know we'll get exactly 6v out. this means that things
    // like auto will behave the same if the battery is full or not - it'll always go at the same speed.
    //
    // (this is just the basics of a concept called closed-loop control. closed-loop control basically
    // means that instead of telling the robot "hey go at 60% power" we tell it "hey drive at X volts"
    // and it figures out how to do that. basically, instead of telling the robot exactly what to do,
    // we tell it what it what the end result should be (like driving at 6 volts) and it figures out
    // how to do that)
    private static CANTalon wrap(CANTalon talon) {
        talon.setControlMode(CANTalon.TalonControlMode.Voltage.getValue());
        return talon;
    }

}