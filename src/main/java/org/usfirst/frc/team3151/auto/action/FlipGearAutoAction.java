package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.subsystem.GearFlipper;

import java.util.function.BooleanSupplier;

public final class FlipGearAutoAction implements BooleanSupplier {

    private final GearFlipper gearFlipper;

    public FlipGearAutoAction(GearFlipper gearFlipper) {
        this.gearFlipper = gearFlipper;
    }

    @Override
    public boolean getAsBoolean() {
        gearFlipper.flip();
        return true;
    }

}