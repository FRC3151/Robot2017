package org.usfirst.frc.team3151.subsystem;

import edu.wpi.first.wpilibj.SpeedController;

public final class Climber {

    private final SpeedController climbA;
    private final SpeedController climbB;

    public Climber(SpeedController climbA, SpeedController climbB) {
        this.climbA = climbA;
        this.climbB = climbB;
    }

    public void climb(double speed) {
        climbA.set(speed);
        climbB.set(speed);
    }

}