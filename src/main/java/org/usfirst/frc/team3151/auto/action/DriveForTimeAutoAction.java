package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.subsystem.DriveTrain;

import java.util.function.BooleanSupplier;

public final class DriveForTimeAutoAction implements BooleanSupplier {

    private final DriveTrain driveTrain;
    private final double forward;
    private final long ms;

    private long firstTicked = 0;

    public DriveForTimeAutoAction(DriveTrain driveTrain, double forward, long ms) {
        this.driveTrain = driveTrain;
        this.forward = forward;
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
            driveTrain.drive(forward, 0, 0);
            return false;
        }
    }

}