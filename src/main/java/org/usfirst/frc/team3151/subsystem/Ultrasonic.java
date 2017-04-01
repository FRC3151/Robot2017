package org.usfirst.frc.team3151.subsystem;

import edu.wpi.first.wpilibj.AnalogInput;

public final class Ultrasonic {

    private final AnalogInput ultrasonicIn;

    public Ultrasonic(AnalogInput ultrasonicIn) {
        this.ultrasonicIn = ultrasonicIn;
        // the AnalogIn constructor already adds itself to the dashboard
    }

    public double getMeasurement() {
        return ultrasonicIn.getAverageVoltage();
    }

}