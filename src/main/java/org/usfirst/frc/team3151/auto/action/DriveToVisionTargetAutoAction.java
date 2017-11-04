package org.usfirst.frc.team3151.auto.action;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team3151.auto.AutoAction;
import org.usfirst.frc.team3151.subsystem.DriveTrain;

public final class DriveToVisionTargetAutoAction implements AutoAction {

    private static final NetworkTable GEAR_TABLE = NetworkTable.getTable("GRIP/gearVision");

    private final long minTerminateTime;
    private final int targetCenterTolerance;
    private final double centeringRotateSpeed;
    private final double alignedForwardSpeed;

    private final DriveTrain driveTrain;
    private final boolean alwaysFire;
    private long lastTrackedTarget;

    public DriveToVisionTargetAutoAction(DriveTrain driveTrain, boolean alwaysFire) {
        Preferences prefs = Preferences.getInstance();

        this.minTerminateTime = prefs.getLong("visionMinTerminateTime", 500);
        this.targetCenterTolerance = prefs.getInt("visionTargetCenterTolerance", 15);
        this.centeringRotateSpeed = prefs.getDouble("visionCenteringRotateSpeed", 0.15);
        this.alignedForwardSpeed = prefs.getDouble("visionAlignedForwardSpeed", 0.2);
        this.driveTrain = driveTrain;
        this.alwaysFire = alwaysFire;
    }

    @Override
    public Result execute() {
        double[] targetCenters = GEAR_TABLE.getNumberArray("centerX", new double[0]);

        if (targetCenters.length != 2) {
            driveTrain.drive(0, 0, 0);

            // we flicker sometimes so instead of launching the gear the moment we lose a target we give it a bit to come back
            boolean flickerToleranceMet = lastTrackedTarget != 0 && (System.currentTimeMillis() - lastTrackedTarget) > minTerminateTime;
            boolean shouldFire = alwaysFire || flickerToleranceMet;

            return shouldFire ? Result.COMPLETED : Result.CONTINUE_EXECUTING;
        }

        double pegCenter = (targetCenters[0] + targetCenters[1]) / 2; // peg is right between the two targets
        double offset = pegCenter - (320 / 2); // offset from middle of image

        if (offset > targetCenterTolerance) {
            driveTrain.drive(0, 0, centeringRotateSpeed);
        } else if (offset < -targetCenterTolerance) {
            driveTrain.drive(0, 0, -centeringRotateSpeed);
        } else {
            driveTrain.drive(alignedForwardSpeed, 0, 0);
        }

        lastTrackedTarget = System.currentTimeMillis();
        return Result.CONTINUE_EXECUTING;
    }

}