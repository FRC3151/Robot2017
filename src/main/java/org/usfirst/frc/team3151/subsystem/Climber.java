package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

public final class Climber {

    public void climb(double speed) {
        RobotConstants.MOTOR_CLIMBER_A.set(speed);
        RobotConstants.MOTOR_CLIMBER_B.set(speed);
    }

}