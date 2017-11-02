package org.usfirst.frc.team3151.auto.mode;

import org.usfirst.frc.team3151.auto.action.DriveToDistanceAutoAction;
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
    public void autonomousInit() {
        registerAction(new DriveToDistanceAutoAction(driveTrain, ultrasonic,
            readSetting("autoForwardSpeed", 0.5),
            center ? readSetting("autoCenterDistance", 1.55) : readSetting("autoSideDistance", 1.95)
        ));
    }

}