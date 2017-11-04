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

    public CameraStreamer() {
        configCamera("Rope Camera", "/dev/video1", 10);
        configCamera("Gear Camera", "/dev/video0", 30);
    }

    private void configCamera(String name, String path, int fps) {
        VideoCamera camera = CameraServer.getInstance().startAutomaticCapture(name, path);

        camera.setFPS(fps);
        camera.setResolution(320, 240);

        // these 3 settings don't particularly matter so long as they stay the same
        camera.setBrightness(35);
        camera.setExposureManual(35);
        camera.setWhiteBalanceManual(4_500);
    }

}