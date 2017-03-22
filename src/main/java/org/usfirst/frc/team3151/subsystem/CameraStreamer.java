package org.usfirst.frc.team3151.subsystem;

import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.wpilibj.CameraServer;

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

        configCamera(server.startAutomaticCapture("Gear Camera", "/dev/video0"), 30);
        configCamera(server.startAutomaticCapture("Rope Camera", "/dev/video1"), 10);
    }

    private void configCamera(VideoCamera camera, int fps) {
        camera.setResolution(FRAME_WIDTH, FRAME_HEIGHT);
        camera.setFPS(fps);
        camera.setBrightness(35);
        camera.setExposureManual(35);
        camera.setWhiteBalanceManual(4_500);
    }

}