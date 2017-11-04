package org.usfirst.frc.team3151.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public final class GearTray {

    private final DoubleSolenoid gearTray;
    private long lastExtended = 0;

    public GearTray(DoubleSolenoid gearTray) {
        this.gearTray = gearTray;
    }

    public void dump() {
        gearTray.set(DoubleSolenoid.Value.kForward);
        lastExtended = System.currentTimeMillis();
    }

    // automatically retract (and then return to neutral after we're retracted)
    // we don't really need to return to neutral (it's technically ok to keep applying pressure)
    // but it's both better for the components and "shrinks" our overall loop when we're not
    // firing (which means if a tube pops out we're not as likely to leak all our air)
    public void tick() {
        long timeSinceExtended = System.currentTimeMillis() - lastExtended;

        if (timeSinceExtended > 1_500) {
            gearTray.set(DoubleSolenoid.Value.kOff);
        } else if (timeSinceExtended > 750) {
            gearTray.set(DoubleSolenoid.Value.kReverse);
        }
    }

}