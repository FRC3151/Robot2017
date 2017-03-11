package org.usfirst.frc.team3151.auto.action;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team3151.subsystem.CameraStreamer;
import org.usfirst.frc.team3151.subsystem.DriveTrain;

import java.util.function.BooleanSupplier;

public final class DriveToVisionTargetAutoAction implements BooleanSupplier {

    private final DriveTrain driveTrain;
    private final NetworkTable gearTable;

    public DriveToVisionTargetAutoAction(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        this.gearTable = NetworkTable.getTable("GRIP/gearVision");
    }

    @Override
    public boolean getAsBoolean() {
        double[] centers = gearTable.getNumberArray("centerX", new double[0]);

        if (centers.length != 2) {
            driveTrain.stopDriving();
            return true;
        }

        double center = (centers[0] + centers[1]) / 2;
        double offset = center - (CameraStreamer.FRAME_WIDTH / 2);

        if (offset > 8) {
            driveTrain.drive(0, 0, 0.3);
        } else if (offset < -8) {
            driveTrain.drive(0, 0, -0.3);
        } else {
            driveTrain.drive(0.40, 0, 0);
        }

        return false;
    }

}