package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public final class GearTray {

    private long lastExtended = 0;

    public void dump() {
        RobotConstants.GEAR_TRAY.set(DoubleSolenoid.Value.kForward);
        lastExtended = System.currentTimeMillis();
    }

    public void tick() {
        long timeSinceExtended = System.currentTimeMillis() - lastExtended;

        if (timeSinceExtended > 1_500) {
            RobotConstants.GEAR_TRAY.set(DoubleSolenoid.Value.kOff);
        } else if (timeSinceExtended > 750) {
            RobotConstants.GEAR_TRAY.set(DoubleSolenoid.Value.kReverse);
        }
    }

}