package org.usfirst.frc.team3151.auto.action;

import org.usfirst.frc.team3151.auto.vision.GearVisionPipelineListener;
import org.usfirst.frc.team3151.subsystem.CameraStreamer;
import org.usfirst.frc.team3151.subsystem.DriveTrain;

import java.util.function.BooleanSupplier;

public final class DriveToVisionTargetAutoAction implements BooleanSupplier {

    private final DriveTrain driveTrain;
    private final GearVisionPipelineListener pipelineListener;

    public DriveToVisionTargetAutoAction(DriveTrain driveTrain, GearVisionPipelineListener pipelineListener) {
        this.driveTrain = driveTrain;
        this.pipelineListener = pipelineListener;
    }

    @Override
    public boolean getAsBoolean() {
        int center = pipelineListener.getTargetCenter();

        if (center < 0) {
            driveTrain.stopDriving();
            return true;
        }

        int offset = center - (CameraStreamer.FRAME_WIDTH / 2);

        if (offset > 8) {
            driveTrain.drive(0, 0, 0.3);
        } else if (offset < -8) {
            driveTrain.drive(0, 0, -0.3);
        } else {
            driveTrain.drive(0.40, 0, 0);
        }

        return false;
    }

}