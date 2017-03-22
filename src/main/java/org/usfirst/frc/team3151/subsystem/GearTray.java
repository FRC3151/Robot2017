package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public final class GearTray {

    private long lastExtended = 0;

    public void dump() {
        RobotConstants.GEAR_TRAY.set(DoubleSolenoid.Value.kForward);
        lastExtended = System.currentTimeMillis();
    }

    // automatically retract (and then return to neutral after we're retracted)
    // we don't really need to return to neutral (it's technically ok to keep applying pressure)
    // but it's both better for the components and "shrinks" our overall loop when we're not
    // firing (which means if a tube pops out we're not as likely to leak all our air)
    public void tick() {
        long timeSinceExtended = System.currentTimeMillis() - lastExtended;

        if (timeSinceExtended > 1_500) {
            RobotConstants.GEAR_TRAY.set(DoubleSolenoid.Value.kOff);
        } else if (timeSinceExtended > 750) {
            RobotConstants.GEAR_TRAY.set(DoubleSolenoid.Value.kReverse);
        }
    }

}