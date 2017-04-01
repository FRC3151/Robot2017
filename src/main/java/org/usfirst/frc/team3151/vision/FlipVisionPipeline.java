package org.usfirst.frc.team3151.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.vision.VisionPipeline;

final class FlipVisionPipeline implements VisionPipeline {

    private Mat flipOutput = new Mat();

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Override
    public void process(Mat image) {
        Core.flip(image, flipOutput, -1); // -1 = both X and Y axis
    }

    Mat getFlipOutput() {
        return flipOutput;
    }

}