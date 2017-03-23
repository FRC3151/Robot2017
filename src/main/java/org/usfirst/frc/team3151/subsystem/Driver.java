package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public final class Driver {

    // we return 0 if less than 15% because xbox controllers are not the
    // best manufactured products and never report exactly 0 (and we don't want to
    // slowly move all the time)
    public double driveForward() {
        double v = RobotConstants.CONTROLLER_DRIVER.getY(GenericHID.Hand.kLeft);
        return Math.abs(v) <= RobotConstants.CONTROLLER_MOVEMENT_DEADZONE ? 0 : -v;
    }

    public double driveStrafe() {
        double v = RobotConstants.CONTROLLER_DRIVER.getX(GenericHID.Hand.kLeft);
        return Math.abs(v) <= RobotConstants.CONTROLLER_MOVEMENT_DEADZONE ? 0 : v;
    }

    public double driveRotate() {
        double left = RobotConstants.CONTROLLER_DRIVER.getTriggerAxis(GenericHID.Hand.kLeft);
        double right = RobotConstants.CONTROLLER_DRIVER.getTriggerAxis(GenericHID.Hand.kRight);

        return right - left;
    }

    public int autoRotateAngle() {
        XboxController xbox = RobotConstants.CONTROLLER_DRIVER;

        if (xbox.getBackButton()) {
            return RobotConstants.ANGLE_GEAR_LEFT;
        } else if (xbox.getStartButton()) {
            return RobotConstants.ANGLE_GEAR_RIGHT;
        } else if (xbox.getBumper(GenericHID.Hand.kLeft)) {
            return RobotConstants.ANGLE_RETRIEVAL_LEFT;
        } else if (xbox.getBumper(GenericHID.Hand.kRight)) {
            return RobotConstants.ANGLE_RETRIEVAL_RIGHT;
        } else {
            return -1;
        }
    }

}