package org.usfirst.frc.team3151.subsystem;

import edu.wpi.cscore.VideoCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;

public final class CameraStreamer {

    public static final int FRAME_WIDTH = 320;
    public static final int FRAME_HEIGHT = 240;

    public CameraStreamer() {
        CameraServer server = CameraServer.getInstance();

        VideoCamera gearCam = server.startAutomaticCapture("Gear Camera", "/dev/video0");
        //gearCam.getProperty("rotation").set(180);
        configCamera(gearCam, 30);

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