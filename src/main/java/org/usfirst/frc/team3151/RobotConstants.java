package org.usfirst.frc.team3151;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public final class RobotConstants {

    // Angles
    public static final int ANGLE_GEAR_LEFT = 60;
    public static final int ANGLE_GEAR_RIGHT = 300;
    public static final int ANGLE_RETRIEVAL_LEFT = 120;
    public static final int ANGLE_RETRIEVAL_RIGHT = 240;

    // Motors
    public static final RobotDrive ROBOT_DRIVE = new RobotDrive(
        wrap(new CANTalon(9)),
        wrap(new CANTalon(8)),
        wrap(new CANTalon(21)),
        wrap(new CANTalon(7))
    );

    public static final SpeedController MOTOR_CLIMBER_A = new CANTalon(20);
    public static final SpeedController MOTOR_CLIMBER_B = new CANTalon(4);

    // Solenoids
    public static final DoubleSolenoid SOLENOID_GEAR_FLIPPER = new DoubleSolenoid(0, 1);
    public static final DoubleSolenoid SOLENOID_GEAR_TRAY = new DoubleSolenoid(2, 3);

    // Sensors
    public static final Gyro SENSOR_GYRO = new ADXRS450_Gyro();
    public static final AnalogInput SENSOR_ULTRASONIC = new AnalogInput(3);

    // Controllers
    public static final double CONTROLLER_MOVEMENT_DEADZONE = 0.15;
    public static final XboxController CONTROLLER_DRIVER = new XboxController(0);
    public static final XboxController CONTROLLER_OPERATOR = new XboxController(1);

    // Auto (Drive)
    public static final double AUTO_CENTER_DISTANCE = 1.55;
    public static final double AUTO_SIDE_DISTANCE = 1.65;
    public static final double AUTO_FORWARD_SPEED = 0.25;

    // Auto (Vision)
    public static final long VISION_MIN_TERMINATE_TIME = 500;
    public static final int VISION_TARGET_CENTER_TOLERANCE = 15;
    public static final double VISION_CENTERING_ROTATE_SPEED = 0.15;
    public static final double VISION_ALIGNED_FORWARD_SPEED = 0.2;

    // PID
    public static final double PID_HEADING_LOCK_P = 0.5; // P is much higher because this is meant for <1 degree corrections, not large ones

    public static final double PID_ROTATE_P = 0.007;
    public static final double PID_ROTATE_I = 0.00035;
    public static final double PID_ROTATE_D = 0.00;
    public static final double PID_ROTATE_OUTPUT_RANGE = 0.5;
    public static final int PID_ROTATE_TOLERANCE = 3;

    // Gear Flipper
    public static final long GEAR_FLIPPER_REVERSE_MS = 250;
    public static final long GEAR_FLIPPER_NEUTRAL_MS = 750;

    // Gear Tray
    public static final long GEAR_TRAY_REVERSE_MS = 750;
    public static final long GEAR_TRAY_NEUTRAL_MS = 1_500;

    // Cameras
    public static final int CAMERA_FRAME_WIDTH = 320;
    public static final int CAMERA_FRAME_HEIGHT = 240;
    public static final int CAMERA_BRIGHTNESS = 35;
    public static final int CAMERA_EXPOSURE = 35;
    public static final int CAMERA_WHITE_BALANCE = 4_500;

    public static final int CAMERA_ROPE_FPS = 15;
    public static final String CAMERA_ROPE_PATH = "/dev/video1";

    public static final int CAMERA_GEAR_FPS = 30;
    public static final String CAMERA_GEAR_PATH = "/dev/video0";

    static {
        // I think we wired them wrong. :(
        ROBOT_DRIVE.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        ROBOT_DRIVE.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

        // see comment on wrap method
        ROBOT_DRIVE.setMaxOutput(12);
    }

    public static double read(String key, double def) {
        return Preferences.getInstance().getDouble(key, def);
    }

    public static int read(String key, int def) {
        return Preferences.getInstance().getInt(key, def);
    }

    public static long read(String key, long def) {
        return Preferences.getInstance().getLong(key, def);
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