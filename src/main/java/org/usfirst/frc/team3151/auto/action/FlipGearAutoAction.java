package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.auto.AutoAction;
import org.usfirst.frc.team3151.subsystem.GearFlipper;

public final class FlipGearAutoAction implements AutoAction {

    private final GearFlipper gearFlipper;

    public FlipGearAutoAction(GearFlipper gearFlipper) {
        this.gearFlipper = gearFlipper;
    }

    @Override
    public Result execute() {
        gearFlipper.flip();
        return Result.COMPLETED;
    }

}