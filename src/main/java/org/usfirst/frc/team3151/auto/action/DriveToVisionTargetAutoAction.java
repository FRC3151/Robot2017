package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.RobotSettings;
import org.usfirst.frc.team3151.subsystem.DriveTrain;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public final class DriveToVisionTargetAutoAction implements BooleanSupplier {

    private static final NetworkTable GEAR_TABLE = NetworkTable.getTable("GRIP/gearVision");

    private final long minTerminateTime;
    private final int targetCenterTolerance;
    private final double centeringRotateSpeed;
    private final double alignedForwardSpeed;

    private final DriveTrain driveTrain;
    private final boolean alwaysFire;
    private long lastTrackedTarget;

    public DriveToVisionTargetAutoAction(DriveTrain driveTrain, boolean alwaysFire) {
        this.minTerminateTime = (long) RobotSettings.get("visionMinTerminateTime");
        this.targetCenterTolerance = (int) RobotSettings.get("visionTargetCenterTolerance");
        this.centeringRotateSpeed = RobotSettings.get("visionCenteringRotateSpeed");
        this.alignedForwardSpeed = RobotSettings.get("visionAlignedForwardSpeed");

        this.driveTrain = driveTrain;
        this.alwaysFire = alwaysFire;
    }

    @Override
    public boolean getAsBoolean() {
        double[] targetCenters = GEAR_TABLE.getNumberArray("centerX", new double[0]);

        if (targetCenters.length != 2) {
            driveTrain.drive(0, 0, 0);

            // we flicker sometimes so instead of launching the gear the moment we lose a target we
            // give it a bit to come back
            return alwaysFire || (lastTrackedTarget != 0 && (System.currentTimeMillis() - lastTrackedTarget) > minTerminateTime);
        }

        double pegCenter = (targetCenters[0] + targetCenters[1]) / 2; // peg is right between the two targets
        double offset = pegCenter - (RobotSettings.CAMERA_FRAME_WIDTH / 2); // offset from middle of image

        if (offset > targetCenterTolerance) {
            driveTrain.drive(0, 0, centeringRotateSpeed);
        } else if (offset < -targetCenterTolerance) {
            driveTrain.drive(0, 0, -centeringRotateSpeed);
        } else {
            driveTrain.drive(alignedForwardSpeed, 0, 0);
        }

        lastTrackedTarget = System.currentTimeMillis();
        return false;
    }

}