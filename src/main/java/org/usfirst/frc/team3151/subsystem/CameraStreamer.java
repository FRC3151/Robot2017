package org.usfirst.frc.team3151.subsystem;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.usfirst.frc.team3151.RobotConstants;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionThread;

// Unlike last year, cameras plugged into the roboRIO don't conveniently
// start streaming - we have this little class to set them up at the proper
// resolution + FPS. we're very specific about this because FRC limits our
// network bandwidth, so we manually prioritize it.
// it's also very important to lock in brightness/exposure/white balance or else
// they'll automatically change as the camera sees fit (when doing vision, consistency is important!)
public final class CameraStreamer {

    public CameraStreamer() {
        CameraServer server = CameraServer.getInstance();

        configCamera(server.startAutomaticCapture("Rope Camera", RobotConstants.CAMERA_ROPE_PATH), RobotConstants.CAMERA_ROPE_FPS);

        // we run a (small) VisionPipeline to just flip this image before
        // sending it over to GRIP + the driver station
        VideoCamera rawGearCam = configCamera(new UsbCamera("Raw Gear Camera", RobotConstants.CAMERA_GEAR_PATH), RobotConstants.CAMERA_GEAR_FPS);
        CvSource gearCam = server.putVideo("Gear Camera", RobotConstants.CAMERA_FRAME_WIDTH, RobotConstants.CAMERA_FRAME_HEIGHT);

        new VisionThread(rawGearCam, new FlipPipeline(), pipeline -> {
            gearCam.putFrame(pipeline.getFlipOutput());
        });
    }

    private VideoCamera configCamera(VideoCamera camera, int fps) {
        camera.setResolution(RobotConstants.CAMERA_FRAME_WIDTH, RobotConstants.CAMERA_FRAME_HEIGHT);
        camera.setFPS(fps);
        camera.setBrightness(RobotConstants.CAMERA_BRIGHTNESS);
        camera.setExposureManual(RobotConstants.CAMERA_EXPOSURE);
        camera.setWhiteBalanceManual(RobotConstants.CAMERA_WHITE_BALANCE);

        return camera;
    }

    private static final class FlipPipeline implements VisionPipeline {

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

}