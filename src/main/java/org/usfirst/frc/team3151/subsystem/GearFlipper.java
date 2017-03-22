package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public final class GearFlipper {

    private long lastExtended = 0;

    public void flip() {
        RobotConstants.SOLENOID_GEAR_FLIPPER.set(DoubleSolenoid.Value.kForward);
        lastExtended = System.currentTimeMillis();
    }

    // automatically retract (and then return to neutral after we're retracted)
    // we don't really need to return to neutral (it's technically ok to keep applying pressure)
    // but it's both better for the components and "shrinks" our overall loop when we're not
    // firing (which means if a tube pops out we're not as likely to leak all our air)
    public void tick() {
        long timeSinceExtended = System.currentTimeMillis() - lastExtended;

        if (timeSinceExtended > RobotConstants.GEAR_FLIPPER_NEUTRAL_MS) {
            RobotConstants.SOLENOID_GEAR_FLIPPER.set(DoubleSolenoid.Value.kOff);
        } else if (timeSinceExtended > RobotConstants.GEAR_FLIPPER_REVERSE_MS) {
            RobotConstants.SOLENOID_GEAR_FLIPPER.set(DoubleSolenoid.Value.kReverse);
        }
    }

}