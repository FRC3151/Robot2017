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
        driveTrain.disableAutoTurn();

        registerAutoAction(new DriveToDistanceAutoAction(driveTrain, ultrasonic, RobotConstants.BASELINE_FORWARD_SPEED, 0, center ? RobotConstants.BASELINE_CENTER_DISTANCE : RobotConstants.BASELINE_SIDE_DISTANCE));
    }

}