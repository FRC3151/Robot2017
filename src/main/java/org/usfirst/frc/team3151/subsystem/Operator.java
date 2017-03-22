package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.GenericHID;

public final class Operator {

    public boolean flipGear() {
        return RobotConstants.CONTROLLER_OPERATOR.getXButton();
    }

    public boolean dumpBalls() {
        return RobotConstants.CONTROLLER_OPERATOR.getBButton();
    }

    public double climbPower() {
        return Math.abs(RobotConstants.CONTROLLER_OPERATOR.getY(GenericHID.Hand.kLeft));
    }

    public boolean zeroGyro() {
        return RobotConstants.CONTROLLER_OPERATOR.getStartButton();
    }

    public boolean debugSensors() {
        return RobotConstants.CONTROLLER_OPERATOR.getBackButton();
    }

}