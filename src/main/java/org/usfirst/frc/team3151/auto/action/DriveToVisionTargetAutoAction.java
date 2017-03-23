package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.RobotConstants;
import org.usfirst.frc.team3151.subsystem.DriveTrain;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DriveToVisionTargetAutoAction implements BooleanSupplier {

    private static final NetworkTable GEAR_TABLE = NetworkTable.getTable("GRIP/gearVision");

    private static long minTerminateTime;
    private static int targetCenterTolerance;
    private static double centeringRotateSpeed;
    private static double alignedForwardSpeed;

    private final DriveTrain driveTrain;
    private long lastTrackedTarget;

    public DriveToVisionTargetAutoAction(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        this.lastTrackedTarget = 0;

        minTerminateTime = RobotConstants.read("visionMinTerminateTime", RobotConstants.VISION_MIN_TERMINATE_TIME);
        targetCenterTolerance = RobotConstants.read("visionTargetCenterTolerance", RobotConstants.VISION_TARGET_CENTER_TOLERANCE);
        centeringRotateSpeed = RobotConstants.read("visionCenteringRotateSpeed", RobotConstants.VISION_CENTERING_ROTATE_SPEED);
        alignedForwardSpeed = RobotConstants.read("visionAlignedForwardSpeed", RobotConstants.VISION_ALIGNED_FORWARD_SPEED);
    }

    @Override
    public boolean getAsBoolean() {
        double[] result = processVisionData();

        if (result.length == 0) {
            driveTrain.stopDriving();
            return lastTrackedTarget != 0 && (System.currentTimeMillis() - lastTrackedTarget) > minTerminateTime;
        } else {
            driveTrain.drive(result[0], 0, result[1]);
            lastTrackedTarget = System.currentTimeMillis();
            return false;
        }
    }

    // for testing purposes we make this method (interpreting the data) a seperate block of code
    // so that we test vision without actually having the robot move. we have one implementation
    // (in this file) which does actually move the robot, and another (for testing) which just
    // prints to the console.
    //
    // returns either an empty array if no targets were found or 2 numbers in the form of [ forward drive, rotate ]
    public static double[] processVisionData() {
        double[] targetCenter = GEAR_TABLE.getNumberArray("centerX", new double[0]);
        double[] targetAreas = GEAR_TABLE.getNumberArray("area", new double[0]);

        SmartDashboard.putBoolean("Vision Found", targetCenter.length == 2);

        if (targetCenter.length != 2) {
            return new double[0];
        }

        double center = (targetCenter[0] + targetCenter[1]) / 2; // center of 2 detected targets
        double offset = center - (RobotConstants.CAMERA_FRAME_WIDTH / 2); // offset from middle of image

        SmartDashboard.putNumber("Vision Offset", offset);
        SmartDashboard.putNumber("Vision Area", targetAreas[0] + targetAreas[1]);

        if (offset > targetCenterTolerance) {
            return new double[] { 0, centeringRotateSpeed };
        } else if (offset < -targetCenterTolerance) {
            return new double[] { 0, -centeringRotateSpeed };
        } else {
            return new double[] { alignedForwardSpeed, 0 };
        }
    }

}