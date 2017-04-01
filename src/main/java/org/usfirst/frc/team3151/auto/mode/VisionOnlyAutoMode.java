package org.usfirst.frc.team3151.auto.mode;

import org.usfirst.frc.team3151.auto.action.DriveToVisionTargetAutoAction;
import org.usfirst.frc.team3151.auto.action.FlipGearAutoAction;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.GearFlipper;

public final class VisionOnlyAutoMode extends ActionBasedAutoMode {

    private final DriveTrain driveTrain;
    private final GearFlipper gearFlipper;

    public VisionOnlyAutoMode(DriveTrain driveTrain, GearFlipper gearFlipper) {
        this.driveTrain = driveTrain;
        this.gearFlipper = gearFlipper;
    }

    @Override
    public void autonomousInit() {
        registerAction(new DriveToVisionTargetAutoAction(driveTrain, false));
        registerAction(new FlipGearAutoAction(gearFlipper));
    }

}