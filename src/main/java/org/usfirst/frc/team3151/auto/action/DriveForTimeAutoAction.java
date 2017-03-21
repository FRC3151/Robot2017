package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.subsystem.DriveTrain;

import java.util.function.BooleanSupplier;

public final class DriveForTimeAutoAction implements BooleanSupplier {

    private final DriveTrain driveTrain;
    private final double forward;
    private final double heading;
    private final long ms;

    private long firstTicked = 0;

    public DriveForTimeAutoAction(DriveTrain driveTrain, double forward, double heading, long ms) {
        this.driveTrain = driveTrain;
        this.forward = forward;
        this.heading = heading;
        this.ms = ms;
    }

    @Override
    public boolean getAsBoolean() {
        if (firstTicked == 0) {
            firstTicked = System.currentTimeMillis();
        }

        if (System.currentTimeMillis() >= firstTicked + ms) {
            driveTrain.stopDriving();
            return true;
        } else {
            driveTrain.driveWithHeading(forward, heading);
            return false;
        }
    }

}