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

        int angle = driverStation == 2 ? 0 : (driverStation == 1 ? RobotConstants.ANGLE_GEAR_LEFT : RobotConstants.ANGLE_GEAR_RIGHT);
        double speed = RobotConstants.read("autoForwardSpeed", RobotConstants.AUTO_FORWARD_SPEED);
        double distance = driverStation == 2 ? RobotConstants.read("autoCenterDistance", RobotConstants.AUTO_CENTER_DISTANCE)
                                             : RobotConstants.read("autoSideDistance", RobotConstants.AUTO_SIDE_DISTANCE);

        registerAutoAction(new DriveToDistanceAutoAction(driveTrain, ultrasonic, speed, 0, distance));

        if (angle != 0) {
            registerAutoAction(new RotateToAngleAutoAction(driveTrain, angle));
        }

        registerAutoAction(new DriveToVisionTargetAutoAction(driveTrain));
        registerAutoAction(new FlipGearAutoAction(gearFlipper));
    }

}