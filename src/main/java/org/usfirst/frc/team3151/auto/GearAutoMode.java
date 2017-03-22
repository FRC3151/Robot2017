package org.usfirst.frc.team3151.auto;

import org.usfirst.frc.team3151.RobotConstants;
import org.usfirst.frc.team3151.auto.action.DriveToDistanceAutoAction;
import org.usfirst.frc.team3151.auto.action.DriveToVisionTargetAutoAction;
import org.usfirst.frc.team3151.auto.action.FlipGearAutoAction;
import org.usfirst.frc.team3151.auto.action.RotateToAngleAutoAction;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.GearFlipper;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;

public final class GearAutoMode extends ActionBasedAutoMode {

    private final DriveTrain driveTrain;
    private final GearFlipper gearFlipper;
    private final Ultrasonic ultrasonic;
    private final int driverStation;

    public GearAutoMode(DriveTrain driveTrain, GearFlipper gearFlipper, Ultrasonic ultrasonic, int driverStation) {
        this.driveTrain = driveTrain;
        this.gearFlipper = gearFlipper;
        this.ultrasonic = ultrasonic;
        this.driverStation = driverStation;
    }

    @Override
    public void init() {
        resetAutoActions();
        driveTrain.disableAutoTurn();
        int angle = driverStation == 2 ? 0 : (driverStation == 1 ? RobotConstants.ANGLE_GEAR_LEFT : RobotConstants.ANGLE_GEAR_RIGHT);

        registerAutoAction(new DriveToDistanceAutoAction(driveTrain, ultrasonic, RobotConstants.AUTO_GEAR_FORWARD_SPEED, 0, driverStation == 2 ? RobotConstants.AUTO_GEAR_CENTER_DISTANCE : RobotConstants.AUTO_GEAR_SIDE_DISTANCE));

        if (angle != 0) {
            registerAutoAction(new RotateToAngleAutoAction(driveTrain, angle));
        }

        registerAutoAction(new DriveToVisionTargetAutoAction(driveTrain));
        registerAutoAction(new FlipGearAutoAction(gearFlipper));
    }

}