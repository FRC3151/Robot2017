package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public final class Gyroscope implements PIDSource {

    public void zero() {
        RobotConstants.GYRO.reset();
    }

    // the model of gyro we have doesn't reset after doing a full rotation.
    // for example, if we rotate it twice to the right it will return 720, not 0.
    // (or -720 if to the left) - this conflicts with most of our other code, which
    // want the absolute angle, so we bring it to within the range of [0, 360]
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