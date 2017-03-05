package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public final class Driver {

    public double driveForward() {
        double v = RobotConstants.DRIVER_XBOX.getY(GenericHID.Hand.kLeft);
        return Math.abs(v) <= 0.15 ? 0 : -v;
    }

    public double driveStrafe() {
        double v = RobotConstants.DRIVER_XBOX.getX(GenericHID.Hand.kLeft);
        return Math.abs(v) <= 0.15 ? 0 : v;
    }

    public double driveRotate() {
        double left = RobotConstants.DRIVER_XBOX.getTriggerAxis(GenericHID.Hand.kLeft);
        double right = RobotConstants.DRIVER_XBOX.getTriggerAxis(GenericHID.Hand.kRight);

        return right - left;
    }

    public int autoRotateAngle() {
        XboxController xbox = RobotConstants.DRIVER_XBOX;

        if (xbox.getBackButton()) {
            return RobotConstants.GEAR_LEFT_ANGLE;
        } else if (xbox.getStartButton()) {
            return RobotConstants.GEAR_RIGHT_ANGLE;
        } else if (xbox.getBumper(GenericHID.Hand.kLeft)) {
            return RobotConstants.RETRIEVAL_LEFT_ANGLE;
        } else if (xbox.getBumper(GenericHID.Hand.kRight)) {
            return RobotConstants.RETRIEVAL_RIGHT_ANGLE;
        } else {
            return -1;
        }
    }

}