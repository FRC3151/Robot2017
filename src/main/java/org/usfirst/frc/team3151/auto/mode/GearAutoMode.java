package org.usfirst.frc.team3151.auto.mode;

import org.usfirst.frc.team3151.RobotSettings;
import org.usfirst.frc.team3151.auto.action.DriveToDistanceAutoAction;
import org.usfirst.frc.team3151.auto.action.DriveToVisionTargetAutoAction;
import org.usfirst.frc.team3151.auto.action.FlipGearAutoAction;
import org.usfirst.frc.team3151.auto.action.RotateToAngleAutoAction;
import org.usfirst.frc.team3151.auto.action.ZeroGyroscopeAutoAction;
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
        registerAction(new ZeroGyroscopeAutoAction(gyroscope));
        registerAction(new DriveToDistanceAutoAction(
                driveTrain,
                ultrasonic,
                RobotSettings.get("autoForwardSpeed"),
                RobotSettings.get(driverStation == 2 ? "autoCenterDistance" : "autoSideDistance")
        ));

        if (driverStation != 2) {
            int angle = driverStation == 1 ? RobotSettings.ANGLE_GEAR_LEFT : RobotSettings.ANGLE_GEAR_RIGHT;
            registerAction(new ZeroGyroscopeAutoAction(gyroscope));
            registerAction(new RotateToAngleAutoAction(driveTrain, angle));
        }

        registerAction(new DriveToVisionTargetAutoAction(driveTrain, driverStation == 2));
        registerAction(new FlipGearAutoAction(gearFlipper));
    }

}