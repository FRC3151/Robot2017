package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

public final class Climber {

    public void climb(double speed) {
        RobotConstants.CLIMBER_A.set(speed);
        RobotConstants.CLIMBER_B.set(speed);
    }

}