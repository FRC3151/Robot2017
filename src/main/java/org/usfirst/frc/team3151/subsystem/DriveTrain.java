package org.usfirst.frc.team3151.subsystem;

import edu.wpi.first.wpilibj.RobotDrive;

public final class DriveTrain {

    private final RobotDrive wpiLibDrive;

    public DriveTrain(RobotDrive wpiLibDrive) {
        this.wpiLibDrive = wpiLibDrive;
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