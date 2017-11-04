package org.usfirst.frc.team3151;

import com.ctre.CANTalon;

public final class TalonUtil {

    // this one is interesting! So, essentially, FRC batteries (and all batteries really)
    // output less voltage as they get less full. so it might start at 12.8v, then drop to
    // 11v, then all the way down to like 9v on a near dead battery. this is relevant because
    // normally we control Talons by telling them "hey output 50% of the voltage you're getting"
    // - which works well on a perfect battery, but not all batteries are perfect. 50% of 12.8v is
    // different from 50% of 11v. so what we do (at least for our drive train motors where this
    // matters) is tell the Talons the exact voltage to output. so if we ask for 6v it might have
    // to be at 50%, 48%, 59%, who cares, we know we'll get exactly 6v out. this means that things
    // like auto will behave the same if the battery is full or not - it'll always go at the same speed.
    //
    // (this is just the basics of a concept called closed-loop control. closed-loop control basically
    // means that instead of telling the robot "hey go at 60% power" we tell it "hey drive at X volts"
    // and it figures out how to do that. basically, instead of telling the robot exactly what to do,
    // we tell it what it what the end result should be (like driving at 6 volts) and it figures out
    // how to do that)
    public static CANTalon createVoltageCompTalon(int deviceNumber) {
        CANTalon talon = new CANTalon(deviceNumber);
        talon.setControlMode(CANTalon.TalonControlMode.Voltage.getValue());
        return talon;
    }

}