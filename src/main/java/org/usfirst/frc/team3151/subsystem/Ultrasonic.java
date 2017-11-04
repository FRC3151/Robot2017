package org.usfirst.frc.team3151.subsystem;

import edu.wpi.first.wpilibj.AnalogInput;

public final class Ultrasonic {

    private final AnalogInput ultrasonicIn;

    public Ultrasonic(AnalogInput ultrasonicIn) {
        this.ultrasonicIn = ultrasonicIn;
    }

    public double getMeasurement() {
        return ultrasonicIn.getAverageVoltage();
    }

}