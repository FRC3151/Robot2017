package org.usfirst.frc.team3151.subsystem;

import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public final class Climber {

    private final CANSpeedController[] climbMotors;

    public Climber(CANSpeedController[] climbMotors) {
        this.climbMotors = climbMotors;

        for (int i = 0; i < climbMotors.length; i++) {
            LiveWindow.addActuator("Climber", "Motor " + i, climbMotors[i]);
        }
    }

    public void climb(double speed) {
        for (CANSpeedController motor : climbMotors) {
            motor.set(speed);
        }
    }

}