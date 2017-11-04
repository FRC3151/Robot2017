package org.usfirst.frc.team3151.subsystem;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public final class Gyroscope implements PIDSource {

    private final ADXRS450_Gyro gyro;

    // we purposely specify the model of gyroscope we're referring to
    // because every gyro returns the angle (continuous vs wrapping),
    // and starting angle (some start at 0, some start at 180, etc)
    // so we can't just swap gyros like we can speed controllers
    // - we want to make sure a physical robot change gets reflected
    // in the software
    public Gyroscope(ADXRS450_Gyro gyro) {
        this.gyro = gyro;
    }

    public void zero() {
        gyro.reset();
    }

    // the model of gyro we have doesn't reset after doing a full rotation.
    // for example, if we rotate it twice to the right it will return 720, not 0.
    // (or -720 if to the left) - this conflicts with most of our other code, which
    // want the absolute angle, so we bring it to within the range of [0, 360]
    public double getCorrectedAngle() {
        double angle = gyro.getAngle();

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