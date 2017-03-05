package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public final class Gyroscope implements PIDSource {

    public double getCorrectedAngle() {
        double angle = RobotConstants.GYRO.getAngle();

        while (angle < 0) {
            angle += 360;
        }

        while (angle > 360) {
            angle -= 360;
        }

        return angle;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {} // ignore

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return getCorrectedAngle();
    }

}