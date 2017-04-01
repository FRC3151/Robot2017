package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.RobotSettings;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public final class DriveTrain {

    // PID loops! this is way too much to explain in a code comment,
    // see me and i'll explain why PID loops are important (basically we tell them what angle we're at,
    // the angle we want to be at, and they give us a rotation value to apply so that we don't "overshoot"
    // the target)
    private final PIDController turnLoop;
    private final RobotDrive wpiLibDrive;

    public DriveTrain(RobotDrive wpiLibDrive, Gyroscope gyroscope) {
        this.wpiLibDrive = wpiLibDrive;
        this.turnLoop = new PIDController(RobotSettings.PID_ROTATE_P, RobotSettings.PID_ROTATE_I, RobotSettings.PID_ROTATE_D, gyroscope, r -> drive(0, 0, r));

        this.turnLoop.setInputRange(0, 360); // gyros go from 0 to 360
        this.turnLoop.setOutputRange(-RobotSettings.PID_ROTATE_OUTPUT_RANGE, RobotSettings.PID_ROTATE_OUTPUT_RANGE); // our drivetrain does go from -1 to 1 but we slow this down a bit
        this.turnLoop.setAbsoluteTolerance(RobotSettings.PID_ROTATE_TOLERANCE); // this never actually happens but we do our best
        this.turnLoop.setContinuous(true); // means that if we're at 359 we can go "right" and
                                           // it'll overflow to 0 (instead of going all the day to the left down to 0)

        LiveWindow.addActuator("Drive Train", "Turn Loop", turnLoop);
    }

    // returns if the loop is completed yet
    public boolean setAutoAngle(double angle) {
        if (!turnLoop.isEnabled()) {
            turnLoop.enable();
            turnLoop.setSetpoint(angle);
        }

        // :( there's no good way to tell when a PID loop is done
        // (and really "done" is a bad term because our loop is never actually
        // 0, it's just "close enough")
        // we check both the output (the .get() call) and the error - checking just
        // output will terminate early if our P is off and we wait for I to fix
        // our alignment, and checking just error means we're technically angled
        // correctly for a second if we overshoot.
        return Math.abs(turnLoop.get()) <= 0.2 && Math.abs(turnLoop.getError()) <= 3;
    }

    public void stopAutoAngle() {
        if (turnLoop.isEnabled()) {
            turnLoop.disable();
        }
    }

    // the WPILib RobotDrive class expects forward to be -1 = full forward
    // (to make things easier for new programmers), but it's much more
    // intuitive to have +1 be forward, so we just invert.
    //
    // positive forward value is driving forward
    // positive strafe value is strafing right
    // positive rotate value is turning right (clockwise)
    public void drive(double forward, double strafe, double rotate) {
        wpiLibDrive.mecanumDrive_Cartesian(strafe, -forward, rotate, 0);
    }

}