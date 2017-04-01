package org.usfirst.frc.team3151.subsystem;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public final class Operator {

    private final XboxController controller;

    public Operator(XboxController controller) {
        this.controller = controller;
    }

    public boolean flipGear() {
        return controller.getXButton();
    }

    public boolean dumpBalls() {
        return controller.getBButton();
    }

    public double climbPower() {
        return Math.abs(controller.getY(GenericHID.Hand.kLeft));
    }

    public boolean conservePower() {
        return controller.getStartButton();
    }

}