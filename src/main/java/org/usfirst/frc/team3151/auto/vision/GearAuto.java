package org.usfirst.frc.team3151.auto.vision;

import org.usfirst.frc.team3151.RobotConstants;
import org.usfirst.frc.team3151.auto.action.DriveForTimeAutoAction;
import org.usfirst.frc.team3151.auto.action.DriveToDistanceAutoAction;
import org.usfirst.frc.team3151.auto.action.DriveToVisionTargetAutoAction;
import org.usfirst.frc.team3151.auto.action.FlipGearAutoAction;
import org.usfirst.frc.team3151.auto.action.RotateToAngleAutoAction;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.GearFlipper;
import org.usfirst.frc.team3151.subsystem.Ultrasonic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.vision.VisionThread;

public final class GearAuto {

    private final DriveTrain driveTrain;
    private final GearFlipper gearFlipper;
    private final Ultrasonic ultrasonic;
    private final GearVisionPipelineListener pipelineListener;

    private final List<BooleanSupplier> actions = new ArrayList<>();

    public GearAuto(VideoCamera gearCamera, DriveTrain driveTrain, GearFlipper gearFlipper, Ultrasonic ultrasonic) {
        this.driveTrain = driveTrain;
        this.gearFlipper = gearFlipper;
        this.ultrasonic = ultrasonic;
        this.pipelineListener = new GearVisionPipelineListener();

        new VisionThread(gearCamera, new GearVisionPipeline(), pipelineListener).start();
    }

    public void autoStarted() {
        int driverStation = DriverStation.getInstance().getLocation();

        actions.clear();
        actions.add(new DriveToDistanceAutoAction(driveTrain, ultrasonic, 0.3, driverStation == 2 ? 0.79 : 0.90));

        if (driverStation != 2) {
            int angle = driverStation == 1 ? RobotConstants.GEAR_LEFT_ANGLE : RobotConstants.GEAR_RIGHT_ANGLE;
            actions.add(new RotateToAngleAutoAction(driveTrain, angle));
        }

        actions.add(new DriveToVisionTargetAutoAction(driveTrain, pipelineListener));
        actions.add(new DriveForTimeAutoAction(driveTrain, 0.3, 400));
        actions.add(new FlipGearAutoAction(gearFlipper));
    }

    public void autoTick() {
        // continually call the next action until it returns true
        // (which signals that it's completed)
        if (!actions.isEmpty() && actions.get(0).getAsBoolean()) {
            actions.remove(0);
        }
    }

}