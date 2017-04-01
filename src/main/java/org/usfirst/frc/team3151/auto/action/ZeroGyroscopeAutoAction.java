package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.subsystem.Gyroscope;

import java.util.function.BooleanSupplier;

public final class ZeroGyroscopeAutoAction implements BooleanSupplier {

    private final Gyroscope gyroscope;

    public ZeroGyroscopeAutoAction(Gyroscope gyroscope) {
        this.gyroscope = gyroscope;
    }

    @Override
    public boolean getAsBoolean() {
        gyroscope.zero();
        return true;
    }

}