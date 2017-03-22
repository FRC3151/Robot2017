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

        return driveToTarget(driveTrain) && (System.currentTimeMillis() - firstTicked > RobotConstants.MIN_VISION_TERMINATE_TIME);
    }

    public static boolean driveToTarget(DriveTrain driveTrain) {
        double[] centers = GEAR_TABLE.getNumberArray("centerX", new double[0]);

        if (centers.length != 2) {
            driveTrain.stopDriving();
            return true;
        }

        double center = (centers[0] + centers[1]) / 2;
        double offset = center - (CameraStreamer.FRAME_WIDTH / 2);

        if (offset > RobotConstants.TARGET_CENTER_TOLERANCE) {
            driveTrain.drive(0, 0, RobotConstants.CENTERING_ROTATE_SPEED);
        } else if (offset < -RobotConstants.TARGET_CENTER_TOLERANCE) {
            driveTrain.drive(0, 0, -RobotConstants.CENTERING_ROTATE_SPEED);
        } else {
            driveTrain.drive(RobotConstants.ALIGNED_FORWARD_SPEED, 0, 0);
        }

        return false;
    }

}