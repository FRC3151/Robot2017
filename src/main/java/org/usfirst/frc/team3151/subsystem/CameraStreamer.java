package org.usfirst.frc.team3151.subsystem;

import org.opencv.core.Core;
import org.opencv.core.Mat;

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

    public static final int FRAME_WIDTH = 320;
    public static final int FRAME_HEIGHT = 240;

    public CameraStreamer() {
        CameraServer server = CameraServer.getInstance();

        configCamera(server.startAutomaticCapture("Rope Camera", "/dev/video1"), 10);

        // we run a (small) VisionPipeline to just flip this image before
        // sending it over to GRIP + the driver station
        VideoCamera rawGearCam = configCamera(new UsbCamera("Raw Gear Camera", "/dev/video0"), 30);
        CvSource gearCam = server.putVideo("Gear Camera", FRAME_WIDTH, FRAME_HEIGHT);

        new VisionThread(rawGearCam, new FlipPipeline(), pipeline -> {
            gearCam.putFrame(pipeline.getFlipOutput());
        });
    }

    private VideoCamera configCamera(VideoCamera camera, int fps) {
        camera.setResolution(FRAME_WIDTH, FRAME_HEIGHT);
        camera.setFPS(fps);
        camera.setBrightness(35);
        camera.setExposureManual(35);
        camera.setWhiteBalanceManual(4_500);

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