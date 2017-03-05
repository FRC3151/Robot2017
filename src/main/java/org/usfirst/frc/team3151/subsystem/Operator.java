package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.GenericHID;

public final class Operator {

    public boolean flipGear() {
        return RobotConstants.OPERATOR_XBOX.getXButton();
    }

    public boolean dumpBalls() {
        return RobotConstants.OPERATOR_XBOX.getBButton();
    }

    public double climbPower() {
        return RobotConstants.OPERATOR_XBOX.getY(GenericHID.Hand.kLeft);
    }

}