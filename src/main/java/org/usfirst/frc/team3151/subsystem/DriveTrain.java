package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.PIDController;

public final class DriveTrain {

    private final Gyroscope gyroscope;
    private final PIDController turnLoop;

    public DriveTrain(Gyroscope gyroscope) {
        this.gyroscope = gyroscope;
        this.turnLoop = new PIDController(0.025, 0.0, 0.0, gyroscope, r -> drive(0, 0, r));

        this.turnLoop.setInputRange(0, 360);
        this.turnLoop.setOutputRange(-0.5, 0.5);
        this.turnLoop.setAbsoluteTolerance(2);
        this.turnLoop.setContinuous(true);
    }

    public boolean setAutoTurn(double angle) {
        if (!turnLoop.isEnabled()) {
            turnLoop.enable();
            turnLoop.setSetpoint(angle);
        }

        return turnLoop.get() <= 0.1 && Math.abs(turnLoop.getError()) <= 2;
    }

    public void disableAutoTurn() {
        if (turnLoop.isEnabled()) {
            turnLoop.disable();
        }
    }

    public void stopDriving() {
        drive(0, 0, 0);
    }

    public void driveWithHeading(double forward, double desiredAngle) {
        double rotA = gyroscope.getCorrectedAngle() - desiredAngle;
        double rotB = -(360 - rotA);
        double leastRot = Math.abs(rotA) < Math.abs(rotB) ? rotA : rotB;

        drive(forward, 0, leastRot * -0.03);
    }

    public void drive(double forward, double strafe, double rotate) {
        RobotConstants.ROBOT_DRIVE.mecanumDrive_Cartesian(strafe, -forward, rotate, 0);
    }

}