package org.usfirst.frc.team3151.auto;

import org.usfirst.frc.team3151.auto.action.*;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;

public final class CrossBaselineAutoMode extends ActionBasedAutoMode {

    private final DriveTrain driveTrain;
    private final Ultrasonic ultrasonic;
    private final boolean center;

    public CrossBaselineAutoMode(DriveTrain driveTrain, Ultrasonic ultrasonic, boolean center) {
        this.driveTrain = driveTrain;
        this.ultrasonic = ultrasonic;
        this.center = center;
    }

    @Override
    public void init() {
        resetAutoActions();
        driveTrain.disableAutoTurn();

        registerAutoAction(new DriveToDistanceAutoAction(driveTrain, ultrasonic, 0.25, 0, center ? 1.5 : 1.7));
    }

}