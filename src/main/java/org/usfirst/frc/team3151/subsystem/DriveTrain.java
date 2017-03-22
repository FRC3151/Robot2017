package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.first.wpilibj.PIDController;

public final class DriveTrain {

    private final Gyroscope gyroscope;

    // PID loops! this is way too much to explain in a code comment,
    // see me and i'll explain why PID loops are important (basically we tell them what angle we're at,
    // the angle we want to be at, and they give us a rotation value to apply so that we don't "overshoot"
    // the target)
    private final PIDController turnLoop;

    public DriveTrain(Gyroscope gyroscope) {
        this.gyroscope = gyroscope;
        this.turnLoop = new PIDController(RobotConstants.ROTATE_P, RobotConstants.ROTATE_I, RobotConstants.ROTATE_D, gyroscope, r -> drive(0, 0, r));

        this.turnLoop.setInputRange(0, 360); // gyros go from 0 to 360
        this.turnLoop.setOutputRange(-RobotConstants.ROTATE_OUTPUT_RANGE, RobotConstants.ROTATE_OUTPUT_RANGE); // our drivetrain does go from -1 to 1 but we slow this down a bit
        this.turnLoop.setAbsoluteTolerance(RobotConstants.ROTATE_TOLERANCE); // this never actually happens but we do our best
        this.turnLoop.setContinuous(true); // means that if we're at 359 we can go "right" and
                                           // it'll overflow to 0 (instead of going all the day to the left down to 0)
        this.turnLoop.setToleranceBuffer(RobotConstants.ROTATE_BUFFER_LENGTH); // we have to have a 'tolerable' (see 2 lines up) value for X readings to
                                                                               // count as done (for .onTarget() call below)
    }

    public boolean setAutoTurn(double angle) {
        if (!turnLoop.isEnabled()) {
            turnLoop.enable();
            turnLoop.setSetpoint(angle);
        }

        // see call to .setToleranceBuffer
        return turnLoop.onTarget();
    }

    public void disableAutoTurn() {
        if (turnLoop.isEnabled()) {
            turnLoop.disable();
        }
    }

    public void stopDriving() {
        drive(0, 0, 0);
    }

    // by using the gyro we can automatically drive w/ a rotation value and compensate
    // for any mechanical defects. this is needed because in auto we need to go *exactly*
    // straight, we can't curve like the bot naturally wants to
    public void driveWithHeading(double forward, double desiredAngle) {
        // say we're at 358 degrees and want to go to 0. If we just subtracted
        // the angle, we'd go all the way around to the left. We therefore compute
        // two possible turns (one to the left and one to the right)
        // and pick whichever one is the shortest
        double rotA = gyroscope.getCorrectedAngle() - desiredAngle;
        double rotB = -(360 - rotA);
        double leastRot = Math.abs(rotA) < Math.abs(rotB) ? rotA : rotB;

        drive(forward, 0, leastRot * -RobotConstants.HEADING_LOCK_P);
    }

    // we invert forward because the method treats -1 as full forward (to be
    // consistent with joysticks), but in our code we always treat 1 as forward,
    // so we just convert
    //
    // positive forward value is driving forward
    // positive strafe value is strafing right
    // positive rotate value is turning right (clockwise)
    public void drive(double forward, double strafe, double rotate) {
        RobotConstants.ROBOT_DRIVE.mecanumDrive_Cartesian(strafe, -forward, rotate, 0);
    }

}