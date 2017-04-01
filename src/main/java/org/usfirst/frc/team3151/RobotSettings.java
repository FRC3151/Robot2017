package org.usfirst.frc.team3151;

import edu.wpi.first.wpilibj.Preferences;

public final class RobotSettings {

    public static final int ANGLE_GEAR_LEFT = 60;
    public static final int ANGLE_GEAR_RIGHT = 300;

    public static final double CONTROLLER_MOVEMENT_DEADZONE = 0.15;
    public static final double PID_ROTATE_P = 0.007;
    public static final double PID_ROTATE_I = 0.00035;
    public static final double PID_ROTATE_D = 0.00;
    public static final double PID_ROTATE_OUTPUT_RANGE = 0.5;
    public static final int PID_ROTATE_TOLERANCE = 3;

    public static final long GEAR_FLIPPER_REVERSE_MS = 250;
    public static final long GEAR_FLIPPER_NEUTRAL_MS = 750;
    public static final long GEAR_TRAY_REVERSE_MS = 750;
    public static final long GEAR_TRAY_NEUTRAL_MS = 1_500;

    public static final int CAMERA_FRAME_WIDTH = 320;
    public static final int CAMERA_FRAME_HEIGHT = 240;
    public static final int CAMERA_BRIGHTNESS = 35;
    public static final int CAMERA_EXPOSURE = 35;
    public static final int CAMERA_WHITE_BALANCE = 4_500;
    public static final int CAMERA_ROPE_FPS = 10;
    public static final String CAMERA_ROPE_PATH = "/dev/video1";
    public static final int CAMERA_GEAR_FPS = 30;
    public static final String CAMERA_GEAR_PATH = "/dev/video0";

    static {
        Preferences prefs = Preferences.getInstance();

        prefs.putDouble("visionCenteringRotateSpeed", 0.15);
        prefs.putDouble("visionAlignedForwardSpeed", 0.2);
        prefs.putDouble("autoForwardSpeed", 0.5);
        prefs.putDouble("autoCenterDistance", 1.55);
        prefs.putDouble("autoSideDistance", 1.95);
        prefs.putDouble("visionTargetCenterTolerance", 15);
        prefs.putDouble("visionMinTerminateTime", 500);
    }

    public static double get(String key) {
        return Preferences.getInstance().getDouble(key, 0);
    }

}