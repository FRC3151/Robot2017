package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotSettings;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public final class GearFlipper {

    private final DoubleSolenoid gearFlipper;
    private long lastExtended = 0;

    public GearFlipper(DoubleSolenoid gearFlipper) {
        this.gearFlipper = gearFlipper;
        LiveWindow.addActuator("Gear Flipper", "Piston", gearFlipper);
    }

    public void flip() {
        gearFlipper.set(DoubleSolenoid.Value.kForward);
        lastExtended = System.currentTimeMillis();
    }

    // automatically retract (and then return to neutral after we're retracted)
    // we don't really need to return to neutral (it's technically ok to keep applying pressure)
    // but it's both better for the components and "shrinks" our overall loop when we're not
    // firing (which means if a tube pops out we're not as likely to leak all our air)
    public void tick() {
        long timeSinceExtended = System.currentTimeMillis() - lastExtended;

        if (timeSinceExtended > RobotSettings.GEAR_FLIPPER_NEUTRAL_MS) {
            gearFlipper.set(DoubleSolenoid.Value.kOff);
        } else if (timeSinceExtended > RobotSettings.GEAR_FLIPPER_REVERSE_MS) {
            gearFlipper.set(DoubleSolenoid.Value.kReverse);
        }
    }

}