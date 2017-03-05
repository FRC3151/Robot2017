package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.subsystem.DriveTrain;

import java.util.function.BooleanSupplier;

public final class RotateToAngleAutoAction implements BooleanSupplier {

    private final DriveTrain driveTrain;
    private final int angle;

    public RotateToAngleAutoAction(DriveTrain driveTrain, int angle) {
        this.driveTrain = driveTrain;
        this.angle = angle;
    }

    @Override
    public boolean getAsBoolean() {
        if (driveTrain.setAutoTurn(angle)) {
            driveTrain.disableAutoTurn();
            return true;
        } else {
            return false;
        }
    }

}