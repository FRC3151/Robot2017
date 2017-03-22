package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.RobotConstants;
import org.usfirst.frc.team3151.subsystem.CameraStreamer;
import org.usfirst.frc.team3151.subsystem.DriveTrain;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public final class DriveToVisionTargetAutoAction implements BooleanSupplier {

    private static final NetworkTable GEAR_TABLE = NetworkTable.getTable("GRIP/gearVision");

    private final DriveTrain driveTrain;
    private long firstTicked;

    public DriveToVisionTargetAutoAction(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
    }

    @Override
    public boolean getAsBoolean() {
        if (firstTicked == 0) {
            firstTicked = System.currentTimeMillis();
        }

        // driveToTarget returns false whenever it fails to register a target. generally, we run this action after
        // coming out of a turn, so sometimes the camera is still blurry (from the motion), so we add a min. time
        // we have to sit in this phase for (to ensure the camera gets a chance to catch up and pick up the target)
        return driveToTarget(driveTrain) && (System.currentTimeMillis() - firstTicked > RobotConstants.VISION_MIN_TERMINATE_TIME);
    }

    public static boolean driveToTarget(DriveTrain driveTrain) {
        double[] centers = GEAR_TABLE.getNumberArray("centerX", new double[0]);

        if (centers.length != 2) {
            driveTrain.stopDriving();
            return true;
        }

        double center = (centers[0] + centers[1]) / 2;
        double offset = center - (RobotConstants.CAMERA_FRAME_WIDTH / 2);

        if (offset > RobotConstants.VISION_TARGET_CENTER_TOLERANCE) {
            driveTrain.drive(0, 0, RobotConstants.VISION_CENTERING_ROTATE_SPEED);
        } else if (offset < -RobotConstants.VISION_TARGET_CENTER_TOLERANCE) {
            driveTrain.drive(0, 0, -RobotConstants.VISION_CENTERING_ROTATE_SPEED);
        } else {
            driveTrain.drive(RobotConstants.VISION_ALIGNED_FORWARD_SPEED, 0, 0);
        }

        return false;
    }

}