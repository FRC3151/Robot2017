package org.usfirst.frc.team3151.auto.action;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import org.usfirst.frc.team3151.auto.AutoAction;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.Gyroscope;

import java.util.concurrent.atomic.AtomicReference;

public final class RotateToAngleAutoAction implements AutoAction {

    private final int angle;
    private AtomicReference<PIDController> turnLoopRef;

    public RotateToAngleAutoAction(DriveTrain driveTrain, Gyroscope gyroscope, int angle) {
        this.angle = angle;
        PIDController turnLoop = new PIDController(0.007, 0.00035, 0, gyroscope, rotate -> {
            if (!DriverStation.getInstance().isAutonomous()) {
                turnLoopRef.get().disable();
                return;
            }

            driveTrain.drive(0, 0, rotate);
        });

        turnLoop.setInputRange(0, 360); // gyros go from 0 to 360 degrees
        turnLoop.setOutputRange(-0.5, 0.5); // we limit output from -50% to +50%
        turnLoop.setAbsoluteTolerance(3); // try to get within +/- 3 degrees
        turnLoop.setContinuous(true); // the output can "wrap around" from 360 to 0
        turnLoopRef = new AtomicReference<>(turnLoop);
    }

    @Override
    public Result execute() {
        PIDController turnLoop = turnLoopRef.get();

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
        boolean done = Math.abs(turnLoop.get()) <= 0.2 && Math.abs(turnLoop.getError()) <= 3;

        if (done) {
            turnLoop.disable();
        }

        return done ? Result.COMPLETED : Result.CONTINUE_EXECUTING;
    }

}