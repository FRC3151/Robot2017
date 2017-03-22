package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

public final class Ultrasonic {

    public double getMeasurement() {
        return RobotConstants.SENSOR_ULTRASONIC.getAverageVoltage();
    }

}