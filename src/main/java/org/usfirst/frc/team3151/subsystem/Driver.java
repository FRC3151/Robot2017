package org.usfirst.frc.team3151.subsystem;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public final class Driver {

    private final XboxController controller;

    public Driver(XboxController controller) {
        this.controller = controller;
    }

    public double forwardMovement() {
        return -deadzone(controller.getY(GenericHID.Hand.kLeft));
    }

    public double strafeMovement() {
        return deadzone(controller.getX(GenericHID.Hand.kLeft));
    }

    public double rotation() {
        double left = controller.getTriggerAxis(GenericHID.Hand.kLeft);
        double right = controller.getTriggerAxis(GenericHID.Hand.kRight);

        return right - left;
    }

    // we return 0 if less than X% power because xbox controllers are not the
    // best manufactured products and never report exactly 0 (and we don't want to
    // slowly move all the time)
    private double deadzone(double in) {
        return Math.abs(in) <= 0.15 ? 0 : in;
    }

}