package org.usfirst.frc.team3151.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3151.RobotConstants;
import org.usfirst.frc.team3151.auto.action.*;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.GearFlipper;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.vision.VisionThread;

public final class GearAutoMode implements AutoMode {

    private final List<BooleanSupplier> actions = new ArrayList<>();

    private final DriveTrain driveTrain;
    private final GearFlipper gearFlipper;
    private final Ultrasonic ultrasonic;

    public GearAutoMode(DriveTrain driveTrain, GearFlipper gearFlipper, Ultrasonic ultrasonic) {
        this.driveTrain = driveTrain;
        this.gearFlipper = gearFlipper;
        this.ultrasonic = ultrasonic;
    }

    @Override
    public void init() {
        int driverStation = DriverStation.getInstance().getLocation();
        actions.clear();
        driveTrain.disableAutoTurn();

        actions.add(new DriveToDistanceAutoAction(driveTrain, ultrasonic, 0.25, driverStation == 2 ? 1.45 : 1.6));

        if (driverStation != 2) {
            int angle = driverStation == 1 ? RobotConstants.GEAR_LEFT_ANGLE : RobotConstants.GEAR_RIGHT_ANGLE;
            actions.add(new RotateToAngleAutoAction(driveTrain, angle));
        }

        actions.add(new DriveToVisionTargetAutoAction(driveTrain));
        actions.add(new FlipGearAutoAction(gearFlipper));
    }

    @Override
    public void tick() {
        // continually call the next action until it returns true
        // (which signals that it's completed)
        if (!actions.isEmpty()) {
            BooleanSupplier action = actions.get(0);
            SmartDashboard.putString("Auto Phase", action.getClass().getSimpleName());

            if (action.getAsBoolean()) {
                actions.remove(0);
            }
        }
    }

}