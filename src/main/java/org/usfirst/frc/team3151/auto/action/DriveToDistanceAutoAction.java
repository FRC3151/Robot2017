package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;

import java.util.function.BooleanSupplier;

public final class DriveToDistanceAutoAction implements BooleanSupplier {

    private final DriveTrain driveTrain;
    private final Ultrasonic ultrasonic;
    private final double forward;
    private final double distance;
    private long firstTicked;

    public DriveToDistanceAutoAction(DriveTrain driveTrain, Ultrasonic ultrasonic, double forward, double distance) {
        this.driveTrain = driveTrain;
        this.ultrasonic = ultrasonic;
        this.forward = forward;
        this.distance = distance;
    }

    @Override
    public boolean getAsBoolean() {
        if (firstTicked == 0) {
            firstTicked = System.currentTimeMillis();
        }

        if (ultrasonic.getMeasurement() >= distance) {
            driveTrain.drive(0, 0, 0);
            // this is ugly, don't let us exit until we've driven a bit (the sensor
            // behaves weird when initially against the wall on the field)
            return System.currentTimeMillis() - firstTicked > 1_500;
        } else {
            driveTrain.drive(forward, 0, 0);
            return false;
        }
    }

}