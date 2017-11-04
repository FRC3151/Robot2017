package org.usfirst.frc.team3151.auto.mode;

import org.usfirst.frc.team3151.auto.action.*;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.GearFlipper;
import org.usfirst.frc.team3151.subsystem.Gyroscope;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;

public final class GearAutoMode extends ActionBasedAutoMode {

    private final DriveTrain driveTrain;
    private final GearFlipper gearFlipper;
    private final Gyroscope gyroscope;
    private final Ultrasonic ultrasonic;
    private final int driverStation;

    public GearAutoMode(DriveTrain driveTrain, GearFlipper gearFlipper, Gyroscope gyroscope, Ultrasonic ultrasonic, int driverStation) {
        this.driveTrain = driveTrain;
        this.gearFlipper = gearFlipper;
        this.gyroscope = gyroscope;
        this.ultrasonic = ultrasonic;
        this.driverStation = driverStation;
    }

    @Override
    public void autonomousInit() {
        boolean center = driverStation == 2;

        registerAction(new ZeroGyroscopeAutoAction(gyroscope));
        registerAction(new DriveToDistanceAutoAction(driveTrain, ultrasonic,
            readSetting("autoForwardSpeed", 0.5),
            center ? readSetting("autoCenterDistance", 1.55) : readSetting("autoSideDistance", 1.95)
        ));

        if (!center) {
            // angle is either 60 (0 + 60) for angling to the right for the left peg
            // or 300 (360 - 60) for angling to the left for the right peg
            int angle = driverStation == 1 ? 60 : 300;
            registerAction(new ZeroGyroscopeAutoAction(gyroscope));
            registerAction(new RotateToAngleAutoAction(driveTrain, gyroscope, angle));
        }

        registerAction(new DriveToVisionTargetAutoAction(driveTrain, center));
        registerAction(new FlipGearAutoAction(gearFlipper));
    }

}