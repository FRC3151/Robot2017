package org.usfirst.frc.team3151.auto;

import org.usfirst.frc.team3151.RobotConstants;
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
    public void init() {
        resetAutoActions();

        double speed = RobotConstants.read("autoForwardSpeed", RobotConstants.AUTO_FORWARD_SPEED);
        double distance = center ? RobotConstants.read("autoCenterDistance", RobotConstants.AUTO_CENTER_DISTANCE)
                                 : RobotConstants.read("autoSideDistance", RobotConstants.AUTO_SIDE_DISTANCE);

        registerAutoAction(new DriveToDistanceAutoAction(driveTrain, ultrasonic, speed, 0, distance));
    }

}