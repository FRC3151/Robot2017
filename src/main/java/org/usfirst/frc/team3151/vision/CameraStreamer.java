package org.usfirst.frc.team3151.vision;

import org.usfirst.frc.team3151.RobotSettings;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionThread;

// Unlike last year, cameras plugged into the roboRIO don't conveniently
// start streaming - we have this little class to set them up at the proper
// resolution + FPS. we're very specific about this because FRC limits our
// network bandwidth, so we manually prioritize it.
// it's also very important to lock in brightness/exposure/white balance or else
// they'll automatically change as the camera sees fit (when doing vision, consistency is important!)
public final class CameraStreamer {

    public CameraStreamer() {
        configCamera(CameraServer.getInstance().startAutomaticCapture("Rope Camera", RobotSettings.CAMERA_ROPE_PATH), RobotSettings.CAMERA_ROPE_FPS);

        // we run a (small) vision pipeline to just flip this image before
        // sending it over to GRIP + the driver station
        VideoCamera rawGearCam = configCamera(new UsbCamera("Raw Gear Camera", RobotSettings.CAMERA_GEAR_PATH), RobotSettings.CAMERA_GEAR_FPS);
        CvSource gearCam = CameraServer.getInstance().putVideo("Gear Camera", RobotSettings.CAMERA_FRAME_WIDTH, RobotSettings.CAMERA_FRAME_HEIGHT);
        new VisionThread(rawGearCam, new FlipVisionPipeline(), p -> gearCam.putFrame(p.getFlipOutput())).start();
    }

    private VideoCamera configCamera(VideoCamera camera, int fps) {
        camera.setFPS(fps);
        camera.setResolution(RobotSettings.CAMERA_FRAME_WIDTH, RobotSettings.CAMERA_FRAME_HEIGHT);
        camera.setBrightness(RobotSettings.CAMERA_BRIGHTNESS);
        camera.setExposureManual(RobotSettings.CAMERA_EXPOSURE);
        camera.setWhiteBalanceManual(RobotSettings.CAMERA_WHITE_BALANCE);

        return camera;
    }

}