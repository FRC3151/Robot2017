package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public final class GearFlipper {

    private long lastExtended = 0;

    public void flip() {
        RobotConstants.GEAR_FLIPPER.set(DoubleSolenoid.Value.kForward);
        lastExtended = System.currentTimeMillis();
    }

    public void tick() {
        long timeSinceExtended = System.currentTimeMillis() - lastExtended;

        if (timeSinceExtended > 750) {
            RobotConstants.GEAR_FLIPPER.set(DoubleSolenoid.Value.kOff);
        } else if (timeSinceExtended > 250) {
            RobotConstants.GEAR_FLIPPER.set(DoubleSolenoid.Value.kReverse);
        }
    }

}