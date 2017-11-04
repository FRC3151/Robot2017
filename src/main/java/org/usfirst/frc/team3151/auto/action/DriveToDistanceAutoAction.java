package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.auto.AutoAction;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;

public final class DriveToDistanceAutoAction implements AutoAction {

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
    public Result execute() {
        if (firstTicked == 0) {
            firstTicked = System.currentTimeMillis();
        }

        if (ultrasonic.getMeasurement() >= distance) {
            driveTrain.drive(0, 0, 0);
            // this is ugly, don't let us exit until we've driven a bit (the sensor
            // behaves weird when initially against the wall on the field)
            return System.currentTimeMillis() - firstTicked > 1_500 ? Result.COMPLETED : Result.CONTINUE_EXECUTING;
        } else {
            driveTrain.drive(forward, 0, 0);
            return Result.CONTINUE_EXECUTING;
        }
    }

}