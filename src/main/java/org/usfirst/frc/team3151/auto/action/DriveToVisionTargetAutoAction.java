package org.usfirst.frc.team3151.auto.action;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team3151.subsystem.CameraStreamer;
import org.usfirst.frc.team3151.subsystem.DriveTrain;

import java.util.function.BooleanSupplier;

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

        return driveToTarget(driveTrain) && (System.currentTimeMillis() - firstTicked > 4_000);
    }

    public static boolean driveToTarget(DriveTrain driveTrain) {
        double[] centers = GEAR_TABLE.getNumberArray("centerX", new double[0]);

        if (centers.length != 2) {
            driveTrain.stopDriving();
            return true;
        }

        double center = (centers[0] + centers[1]) / 2;
        double offset = center - (CameraStreamer.FRAME_WIDTH / 2);

        if (offset > 15) {
            driveTrain.drive(0, 0, 0.2);
        } else if (offset < -15) {
            driveTrain.drive(0, 0, -0.2);
        } else {
            driveTrain.drive(0.3, 0, 0);
        }

        return false;
    }

}