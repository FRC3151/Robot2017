package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.auto.AutoAction;
import org.usfirst.frc.team3151.subsystem.Gyroscope;

public final class ZeroGyroscopeAutoAction implements AutoAction {

    private final Gyroscope gyroscope;

    public ZeroGyroscopeAutoAction(Gyroscope gyroscope) {
        this.gyroscope = gyroscope;
    }

    @Override
    public Result execute() {
        gyroscope.zero();
        return Result.COMPLETED;
    }

}