package org.usfirst.frc.team3151.auto.mode;

import org.usfirst.frc.team3151.auto.action.DriveToDistanceAutoAction;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;

public final class CrossBaselineAutoMode extends ActionBasedAutoMode {

    private final DriveTrain driveTrain;
    private final Ultrasonic ultrasonic;
    private final int driverStation;

    public CrossBaselineAutoMode(DriveTrain driveTrain, Ultrasonic ultrasonic, int driverStation) {
        this.driveTrain = driveTrain;
        this.ultrasonic = ultrasonic;
        this.driverStation = driverStation;
    }

    @Override
    public void autonomousInit() {
        boolean center = driverStation == 2;

        registerAction(new DriveToDistanceAutoAction(driveTrain, ultrasonic,
            readSetting("autoForwardSpeed", 0.5),
            center ? readSetting("autoCenterDistance", 1.55) : readSetting("autoSideDistance", 1.95)
        ));
    }

}